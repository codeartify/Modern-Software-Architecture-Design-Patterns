package com.event.admin.processpayment.shared;

import com.event.admin.domain.Discount;
import com.event.admin.domain.Ticket;
import com.event.admin.processpayment.dataaccess.DiscountRepository;
import com.event.admin.processpayment.domain.DiscountCode;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TotalAmountFactory {
    public static final double VAT_RATE = 1.20;
    public static final double FEE_RATE = 1.03;

    private final DiscountRepository discountRepository;

    public TotalAmountFactory(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public double calculateTotalAmountFor(List<@Valid Ticket> tickets, DiscountCode discountCode) {
        if (tickets.size() > 20) {
            throw new IllegalArgumentException("Cannot purchase more than 20 overall tickets at a time.");
        }

        return calculateTotalAmountWithDiscount(tickets, discountCode) + feeForQuantity(tickets);
    }

    public double getTotalAmountWithFee(List<@Valid Ticket> tickets, DiscountCode discountCode1) {
        return calculateTotalAmountFor(tickets, discountCode1) * VAT_RATE * FEE_RATE;
    }

    private static double feeForQuantity(List<Ticket> tickets) {
        var totalAmountBeforeDiscount = calculateTotalAmountFrom(tickets);

        if (totalAmountBeforeDiscount > 40.00) {
            return totalAmountBeforeDiscount * 0.025;
        } else {
            return 0.99;
        }
    }

    private double calculateTotalAmountWithDiscount(List<@Valid Ticket> tickets, DiscountCode discountCode) {
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

    private double calculateSumOfDiscountsPerTicket(List<@Valid Ticket> tickets, DiscountCode discountCode) {
        var discount = discountRepository.fetchDiscountCode(discountCode);

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

    private static boolean discountApplies(Ticket ticket, Discount discount) {
        return discount != null && (discount.getApplicableTicketType() == null || discount.getApplicableTicketType().equals(ticket.getType()));
    }
}
