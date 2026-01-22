package edu.aitu.oop3;

import edu.aitu.oop3.db.IDB;
import edu.aitu.oop3.db.PostgresDB;
import edu.aitu.oop3.entities.Room;
import edu.aitu.oop3.repositories.ReservationRepository;
import edu.aitu.oop3.repositories.RoomRepository;
import edu.aitu.oop3.repositories.IReservation;
import edu.aitu.oop3.repositories.Iroomrepository;
import edu.aitu.oop3.services.BookingService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB();
        Iroomrepository roomRepo = new RoomRepository(db);
        IReservation resRepo = new ReservationRepository(db);
        BookingService service = new BookingService(roomRepo, resRepo);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Connecting to database...");

        while (true) {
            System.out.println("\n--- HOSTEL BOOKING SYSTEM ---");
            System.out.println("1. List All Rooms");
            System.out.println("2. Make Reservation");
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

            } else if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}