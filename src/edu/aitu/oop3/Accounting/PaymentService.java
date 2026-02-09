package edu.aitu.oop3.Accounting;

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