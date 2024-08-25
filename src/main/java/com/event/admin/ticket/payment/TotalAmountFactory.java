package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.DiscountCode;
import com.event.admin.ticket.model.Ticket;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotalAmountFactory {
    public static final double VAT_RATE = 1.20;
    public static final double FEE_RATE = 1.03;

    private final DiscountCodeRepository discountCodeRepository;

    public TotalAmountFactory(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    public double calculateTotalAmountFor(List<@Valid Ticket> tickets, String discountCode) {
        if (tickets.size() > 20) {
            throw new IllegalArgumentException("Cannot purchase more than 20 overall tickets at a time.");
        }

        return calculateTotalAmountWithDiscount(tickets, discountCode) + feeForQuantity(tickets);
    }

    private static double feeForQuantity(List<Ticket> tickets) {
        var totalAmountBeforeDiscount = calculateTotalAmountFrom(tickets);

        if (totalAmountBeforeDiscount > 40.00) {
            return totalAmountBeforeDiscount * 0.025;
        } else {
            return 0.99;
        }
    }

    private double calculateTotalAmountWithDiscount(List<@Valid Ticket> tickets, String discountCode) {
        var totalAmount = calculateTotalAmountFrom(tickets);
        var sumOfDiscountsPerTicket = calculateSumOfDiscountsPerTicket(tickets, discountCode);
        var groupDiscount = calculateGroupDiscountFor(tickets);

        return totalAmount - sumOfDiscountsPerTicket + (totalAmount * groupDiscount);
    }

    private static double calculateGroupDiscountFor(List<Ticket> tickets) {
        if (tickets.size() >= 11) {
            return 0.30;
        } else if (tickets.size() >= 6) {
            return 0.25;
        } else if (tickets.size() >= 2) {
            return 0.20;
        } else {
            return 0;
        }
    }

    private double calculateSumOfDiscountsPerTicket(List<@Valid Ticket> tickets, String discountCode) {
        var discount = discountCodeRepository.fetchDiscountCode(discountCode);

        return tickets.stream()
                .filter(ticket -> discountApplies(ticket, discount))
                .mapToDouble(ticket -> calculateTicketPriceWithVAT(ticket) * discount.getDiscountPercentage() / 100)
                .sum();
    }

    private static double calculateTotalAmountFrom(List<@Valid Ticket> tickets) {
        return tickets.stream()
                .mapToDouble(TotalAmountFactory::calculateTicketPriceWithVAT)
                .sum();
    }

    private static double calculateTicketPriceWithVAT(Ticket ticket) {
        return ticket.getPrice() * VAT_RATE;
    }

    private static boolean discountApplies(Ticket ticket, DiscountCode discountCode) {
        return discountCode != null && (discountCode.getApplicableTicketType() == null || discountCode.getApplicableTicketType().equals(ticket.getType()));
    }

    public double getTotalAmountWithFee(List<@Valid Ticket> tickets, String discountCode) {
        return calculateTotalAmountFor(tickets, discountCode) * VAT_RATE * FEE_RATE;
    }
}
