package edu.aitu.oop3.entities;

public class Room {
    private int id;
    private int number;
    private String type; // 'Standard', 'Suite', 'Dorm'
    private double price;
    private boolean isAvailable;

    public Room() {}

    public Room(int id, int number, String type, double price, boolean isAvailable) {
        this.id = id;
        this.number = number;
        this.type = type;
        this
                .price = price;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", number=" + number + ", type='" + type + '\'' + ", price=" + price + '}';
    }
}