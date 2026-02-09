package edu.aitu.oop3.Accounting;

public class Payment {
    private int id;
    private int reservationId;
    private double amount;
    private String status;

    public Payment() {}
    public Payment(int id, int reservationId, double amount, String status) {
        this.id = id;
        this.reservationId = reservationId;
        this.amount = amount;
        this.status = status;
    }
    public int getId() { return id; }
    public int getReservationId() { return reservationId; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
}