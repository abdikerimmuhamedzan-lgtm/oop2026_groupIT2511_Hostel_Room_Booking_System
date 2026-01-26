package edu.aitu.oop3.repositories;

import edu.aitu.oop3.entities.Payment;

public interface IPaymentRepository {
    boolean createPayment(Payment payment);
}