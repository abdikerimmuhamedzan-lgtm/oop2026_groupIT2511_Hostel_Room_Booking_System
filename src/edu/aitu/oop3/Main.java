package edu.aitu.oop3;

import edu.aitu.oop3.db.IDB;
import edu.aitu.oop3.db.PostgresDB;
import edu.aitu.oop3.entities.Room;
import edu.aitu.oop3.repositories.*;
import edu.aitu.oop3.services.BookingService;
import edu.aitu.oop3.services.PaymentService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB();
        Iroomrepository roomRepo = new RoomRepository(db);
        IReservation resRepo = new ReservationRepository(db);
        IPaymentRepository payRepo = new PaymentRepository(db);

        BookingService service = new BookingService(roomRepo, resRepo);
        PaymentService payService = new PaymentService(payRepo);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Connecting to database...");

        while (true) {
            System.out.println("\n HOSTEL BOOKING SYSTEM ");
            System.out.println("1. List All Rooms");
            System.out.println("2. Search Available Rooms (by Date)");
            System.out.println("3. Make Reservation");
            System.out.println("4. Cancel Reservation");
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

            } else if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}