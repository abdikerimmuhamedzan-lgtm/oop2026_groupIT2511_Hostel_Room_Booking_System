package edu.aitu.oop3.services;

import edu.aitu.oop3.entities.Payment;
import edu.aitu.oop3.repositories.IPaymentRepository;

public class PaymentService {
    private final IPaymentRepository paymentRepo;

    public PaymentService(IPaymentRepository paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public boolean processPayment(double amount) {
        Payment payment = new Payment(0, 0, amount, "COMPLETED");
        return paymentRepo.createPayment(payment);
    }
}