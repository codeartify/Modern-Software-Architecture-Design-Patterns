package com.event.admin.ticket;

import com.event.admin.ticket.model.Event;
import com.event.admin.ticket.model.PaymentRequest;
import com.event.admin.ticket.model.ReserveTicketsRequest;
import com.event.admin.ticket.model.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void reserveTicketsForNonexistentBookerShouldReturn404() throws Exception {
        var nonexistantBooker = "missingNo";
        ReserveTicketsRequest request = new ReserveTicketsRequest(2, "Standard", nonexistantBooker);

        // Act & Assert
        mockMvc.perform(post("/api/events/{id}/tickets", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void reserveTicketsForNonexistentEventShouldReturn404() throws Exception {
        // Arrange
        long nonExistentEventId = 999L; // Assuming this ID does not exist
        ReserveTicketsRequest request = new ReserveTicketsRequest(2, "Standard", "johndoe");

        // Act & Assert
        mockMvc.perform(post("/api/events/{id}/tickets", nonExistentEventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }


    @Test
    void reserveTicketsForEventWithNoAvailableTicketsShouldReturn204() throws Exception {
        // Arrange
        var createdEvent = createNewEvent(new Event(null, "Test Event", 5));
        addTicketsToEvent(10, "Standard", createdEvent, 9999999999L);
        Long eventId = createdEvent.getId();

        ReserveTicketsRequest request = new ReserveTicketsRequest(2, "Standard", "johndoe");

        // Act & Assert
        mockMvc.perform(post("/api/events/{id}/tickets", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void reserveTicketsForEventWithNoAvailableTicketsForTypeShouldReturn204() throws Exception {
        // Arrange
        var createdEvent = createNewEvent(new Event(null, "Test Event", 5));
        addTicketsToEvent(10, "Standard", createdEvent, null);
        Long eventId = createdEvent.getId();

        // request for VIP tickets
        ReserveTicketsRequest request = new ReserveTicketsRequest(2, "VIP", "johndoe");

        // Act & Assert
        mockMvc.perform(post("/api/events/{id}/tickets", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    void reserveTicketsExceedingLimitShouldReturn400() throws Exception {
        var createdEvent = createNewEvent(new Event(null, "Test Event", 2));
        addTicketsToEvent(10, "Standard", createdEvent, null);

        ReserveTicketsRequest request = new ReserveTicketsRequest(3, "Standard", "johndoe");

        mockMvc.perform(post("/api/events/{id}/tickets", createdEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void reserveTicketsSuccessfullyShouldReturn200() throws Exception {
        var createdEvent = createNewEvent(new Event(null, "Test Event", 5));
        addTicketsToEvent(10, "Standard", createdEvent, null);

        Long eventId = createdEvent.getId();

        ReserveTicketsRequest request = new ReserveTicketsRequest(2, "Standard", "johndoe");

        // Act & Assert
        mockMvc.perform(post("/api/events/{id}/tickets", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(eventId))
                .andExpect(jsonPath("$.numberOfReservedTickets").value(2))
                .andExpect(jsonPath("$.bookerUsername").value("johndoe"));
    }

    private Event createNewEvent(Event testEvent) throws Exception {
        String eventResponse = mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEvent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(eventResponse, Event.class);
    }

    private void addTicketsToEvent(int numberOfTickets, String ticketType, Event createdEvent, Long bookerId) throws Exception {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < numberOfTickets; i++) {
            Ticket ticket = new Ticket(null, 50.0, ticketType, null, bookerId, false, createdEvent);
            tickets.add(ticket);
        }
        mockMvc.perform(post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tickets)))
                .andExpect(status().isOk());
    }

    // Test 1: Valid Payment with Credit Card
    @Test
    void testProcessPaymentWithValidCreditCard() throws Exception {
        PaymentRequest paymentRequest = createValidPaymentRequest("credit_card");
        mockMvc.perform(post("/api/tickets/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    // Test 2: Valid Payment with Bill
    @Test
    void testProcessPaymentWithValidBill() throws Exception {
        PaymentRequest paymentRequest = createValidPaymentRequest("bill");
        mockMvc.perform(post("/api/tickets/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    // Test 3: Invalid Payment Type
    @Test
    void testProcessPaymentWithInvalidPaymentType() throws Exception {
        PaymentRequest paymentRequest = createValidPaymentRequest("invalid_type");
        mockMvc.perform(post("/api/tickets/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest());
    }

    // Test 4: More than 10 Tickets
    @Test
    void testProcessPaymentWithMoreThan20Tickets() throws Exception {
        PaymentRequest paymentRequest = createInvalidPaymentRequestWithMoreThan20Tickets();
        mockMvc.perform(post("/api/tickets/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest());
    }

    // Test 5: Missing Required Fields for Bill Payment
    @Test
    void testProcessPaymentWithMissingFieldsForBill() throws Exception {
        PaymentRequest paymentRequest = createValidPaymentRequest("bill");
        paymentRequest.setBuyerCompanyName(null); // Missing field
        mockMvc.perform(post("/api/tickets/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isBadRequest());
    }

    // Test 6: Applying Discounts
    @Test
    void testProcessPaymentWithDiscount() throws Exception {
        PaymentRequest paymentRequest = createValidPaymentRequest("credit_card");
        paymentRequest.setDiscountCode("DISCOUNT10");
        mockMvc.perform(post("/api/tickets/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    // Test 7: No Discount Code Provided
    @Test
    void testProcessPaymentWithoutDiscountCode() throws Exception {
        PaymentRequest paymentRequest = createValidPaymentRequest("credit_card");
        paymentRequest.setDiscountCode(null); // No discount code
        mockMvc.perform(post("/api/tickets/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    // Test 8: Group Discount Applied
    @Test
    void testProcessPaymentWithGroupDiscount() throws Exception {
        PaymentRequest paymentRequest = createValidPaymentRequest("credit_card");
        paymentRequest.setTickets(createMultipleTickets(6)); // 6 tickets should trigger group discount
        mockMvc.perform(post("/api/tickets/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk());
    }

    // Helper method to create a valid PaymentRequest
    private PaymentRequest createValidPaymentRequest(String paymentType) {
        Ticket ticket1 = new Ticket(1L, 100.00, "VIP", null, null, false, new Event(1L, "Spring Boot Workshop", 5));
        Ticket ticket2 = new Ticket(2L, 50.00, "Standard", null, null, false, new Event(1L, "Spring Boot Workshop", 5));
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPaymentType(paymentType);
        paymentRequest.setTickets(tickets);
        paymentRequest.setPaymentMethod("CreditCard");
        paymentRequest.setDiscountCode("SUMMER20");
        paymentRequest.setBuyerCompanyName("SuperSoftware AG");
        paymentRequest.setOrganizerCompanyName("Codeartify GmbH");
        paymentRequest.setBuyerName("John Doe");
        paymentRequest.setIban("DE89370400440532013000");
        paymentRequest.setBillDescription("Payment for Spring Boot Workshop tickets");

        return paymentRequest;
    }

    // Helper method to create an invalid PaymentRequest with more than 20 tickets
    private PaymentRequest createInvalidPaymentRequestWithMoreThan20Tickets() {
        List<Ticket> tickets = createMultipleTickets(21); // More than 20 tickets
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setPaymentType("credit_card");
        paymentRequest.setTickets(tickets);
        paymentRequest.setPaymentMethod("CreditCard");
        paymentRequest.setDiscountCode("SUMMER20");
        paymentRequest.setBuyerCompanyName("SuperSoftware GmbH");
        paymentRequest.setOrganizerCompanyName("Codeartify GmbH");
        paymentRequest.setBuyerName("John Doe");
        paymentRequest.setIban("DE89370400440532013000");
        paymentRequest.setBillDescription("Payment for Spring Boot Workshop tickets");

        return paymentRequest;
    }

    private List<Ticket> createMultipleTickets(int count) {
        Event event = new Event(1L, "Spring Boot Workshop", 5);
        Ticket[] tickets = new Ticket[count];
        for (int i = 0; i < count; i++) {
            tickets[i] = new Ticket((long) i + 1, 100.00, "VIP", null, null, false, event);
        }
        return List.of(tickets);
    }
}
