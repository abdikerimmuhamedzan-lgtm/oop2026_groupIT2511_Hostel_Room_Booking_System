package edu.aitu.oop3;

import edu.aitu.oop3.Accounting.IPaymentRepository;
import edu.aitu.oop3.Accounting.PaymentRepository;
import edu.aitu.oop3.Accounting.PaymentService;
import edu.aitu.oop3.Accounting.PricingStrategy;
import edu.aitu.oop3.Common.IDB;
import edu.aitu.oop3.Common.PostgresDB;
import edu.aitu.oop3.Reservation.BookingService;
import edu.aitu.oop3.Reservation.IReservationRepository;
import edu.aitu.oop3.Reservation.Reservation;
import edu.aitu.oop3.Reservation.ReservationRepository;
import edu.aitu.oop3.RoomManagement.IRoomRepository;
import edu.aitu.oop3.RoomManagement.Room;
import edu.aitu.oop3.RoomManagement.RoomFactory;
import edu.aitu.oop3.RoomManagement.RoomRepository;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB();

        IRoomRepository roomRepo = new RoomRepository(db);

        IReservationRepository resRepo = new ReservationRepository(db);
        BookingService service = new BookingService(roomRepo, resRepo);

        IPaymentRepository payRepo = new PaymentRepository(db);
        PaymentService payService = new PaymentService(payRepo);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Connecting to database...");

        while (true) {
            System.out.println("\n HOSTEL BOOKING SYSTEM (Endterm Component Ver.)");
            System.out.println("1. List All Rooms");
            System.out.println("2. Search Available Rooms");
            System.out.println("3. Make Reservation");
            System.out.println("4. Cancel Reservation");
            System.out.println("5. Add New Room ");
            System.out.println("6. Filter Rooms by Price ");
            System.out.println("7. Custom Reservation (Builder + Pricing)");
            System.out.println("0. Exit");
            System.out.print("Select option: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next();
                continue;
            }

            if (choice == 1) {
                List<Room> rooms = service.getAllRooms();
                if (rooms != null && !rooms.isEmpty()) {
                    System.out.println("\nID | Number | Type     | Price  | Available");
                    for (Room r : rooms) {
                        System.out.printf("%-2d | %-6d | %-8s | %-6.2f | %-5s%n",
                                r.getId(), r.getNumber(), r.getType(), r.getPrice(), r.isAvailable());
                    }
                } else {
                    System.out.println("No rooms found.");
                }

            } else if (choice == 2) {
                System.out.print("Enter Check-In (YYYY-MM-DD): ");
                String in = scanner.next();
                System.out.print("Enter Check-Out (YYYY-MM-DD): ");
                String out = scanner.next();

                List<Room> rooms = service.getAvailableRooms(in, out);
                if (rooms != null && !rooms.isEmpty()) {
                    System.out.println("\n--- Available Rooms ---");
                    for (Room r : rooms) {
                        System.out.printf("ID: %d, Type: %s, Price: %.2f%n", r.getId(), r.getType(), r.getPrice());
                    }
                } else {
                    System.out.println("No rooms available for these dates.");
                }

            } else if (choice == 3) {
                System.out.print("Enter Guest ID: ");
                int guestId = scanner.nextInt();
                System.out.print("Enter Room ID: ");
                int roomId = scanner.nextInt();
                System.out.print("Check-In (YYYY-MM-DD): ");
                String checkIn = scanner.next();
                System.out.print("Check-Out (YYYY-MM-DD): ");
                String checkOut = scanner.next();

                String result = service.makeReservation(guestId, roomId, checkIn, checkOut);
                System.out.println("\n>>> " + result);

                if (result.startsWith("Reservation created")) {
                    payService.processPayment(100.0);
                }

            } else if (choice == 4) {
                System.out.print("Enter Reservation ID to cancel: ");
                int resId = scanner.nextInt();
                String result = service.cancelReservation(resId);
                System.out.println("\n>>> " + result);

            } else if (choice == 5) {
                System.out.print("Enter Room Type (Standard/Suite/Dorm): ");
                String type = scanner.next();
                System.out.print("Enter Room Number: ");
                int number = scanner.nextInt();

                Room newRoom = RoomFactory.createRoom(type, number);
                boolean success = roomRepo.createRoom(newRoom);
                if (success) {
                    System.out.println("Room created: " + newRoom);
                } else {
                    System.out.println("Failed to create room.");
                }

            } else if (choice == 6) {
                System.out.print("Enter max price: ");
                double max = scanner.nextDouble();
                List<Room> cheapRooms = service.getRoomsFilteredByPrice(max);
                if (cheapRooms != null) {
                    cheapRooms.forEach(System.out::println);
                }

            } else if (choice == 7) {
                System.out.println("--- Special Booking ---");
                System.out.print("Guest ID: ");
                int gId = scanner.nextInt();
                System.out.print("Room ID: ");
                int rId = scanner.nextInt();
                System.out.print("Date (YYYY-MM-DD): ");
                String dStr = scanner.next();
                Date date = Date.valueOf(dStr);

                Room room = roomRepo.getRoomById(rId);
                if (room != null) {
                    PricingStrategy pricing = PricingStrategy.getInstance();
                    double finalPrice = pricing.calculatePrice(room.getPrice(), date.toLocalDate());
                    System.out.println("Calculated Price (with seasonal/weekend adjustment): " + finalPrice);

                    Reservation res = new Reservation.Builder()
                            .setGuestId(gId)
                            .setRoomId(rId)
                            .setDates(date, date)
                            .setTotalPrice(finalPrice)
                            .setStatus("CONFIRMED")
                            .build();

                    boolean created = resRepo.createReservation(res);
                    System.out.println("Reservation created via Builder: " + created);
                } else {
                    System.out.println("Room not found.");
                }
            } else if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}