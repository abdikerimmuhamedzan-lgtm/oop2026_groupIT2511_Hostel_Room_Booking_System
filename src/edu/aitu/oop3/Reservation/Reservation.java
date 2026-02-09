package edu.aitu.oop3.Reservation;

import java.sql.Date;

public class Reservation {
    private int id;
    private int guestId;
    private int roomId;
    private Date checkIn;
    private Date checkOut;
    private double totalPrice;
    private String status;

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

    private Reservation(Builder builder) {
        this.id = 0;
        this.guestId = builder.guestId;
        this.roomId = builder.roomId;
        this.checkIn = builder.checkIn;
        this.checkOut = builder.checkOut;
        this.totalPrice = builder.totalPrice;
        this.status = builder.status;
    }

    public static class Builder {
        private int guestId;
        private int roomId;
        private Date checkIn;
        private Date checkOut;
        private double totalPrice;
        private String status = "PENDING";

        public Builder setGuestId(int guestId) { this.guestId = guestId; return this; }
        public Builder setRoomId(int roomId) { this.roomId = roomId; return this; }
        public Builder setDates(Date checkIn, Date checkOut) { this.checkIn = checkIn; this.checkOut = checkOut; return this; }
        public Builder setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; return this; }
        public Builder setStatus(String status) { this.status = status; return this; }
        public Reservation build() { return new Reservation(this); }
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getGuestId() { return guestId; }
    public int getRoomId() { return roomId; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", status='" + status + '\'' + ", total=" + totalPrice + '}';
    }
}