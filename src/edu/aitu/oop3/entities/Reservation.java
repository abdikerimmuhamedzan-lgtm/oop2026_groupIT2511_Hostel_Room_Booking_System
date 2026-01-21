package edu.aitu.oop3.entities;

import java.sql.Date;

public class Reservation {
    private int id;
    private int guestId;
    private int roomId;
    private Date checkIn;
    private Date checkOut;
    private double totalPrice;
    private String status; // "CONFIRMED", "CANCELLED"

    public Reservation() {}

    public Reservation(int id, int guestId, int roomId, Date checkIn, Date checkOut, double totalPrice, String status) {
        this.id = id;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }

    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", status='" + status + '\'' + ", total=" + totalPrice + '}';
    }
}