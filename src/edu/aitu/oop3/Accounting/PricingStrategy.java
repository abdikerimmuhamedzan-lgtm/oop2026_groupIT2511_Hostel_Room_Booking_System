package edu.aitu.oop3.Accounting;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class PricingStrategy {
    private static PricingStrategy instance;
    private double weekendMultiplier = 1.2;

    private PricingStrategy() {}

    public static synchronized PricingStrategy getInstance() {
        if (instance == null) instance = new PricingStrategy();
        return instance;
    }

    public double calculatePrice(double basePrice, LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            return basePrice * weekendMultiplier;
        }
        return basePrice;
    }
}