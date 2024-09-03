package com.event.admin.retrievetickets;

import com.event.admin.domain.Ticket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RetrieveTicketsController { 
    private final RetrieveTicketsHandler retrieveTicketsHandler;

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        var tickets = retrieveTicketsHandler.handle();
        return ResponseEntity.ok(tickets);
    }

}
