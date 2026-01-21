package edu.aitu.oop3;

import edu.aitu.oop3.db.IDB;
import edu.aitu.oop3.db.PostgresDB;
import edu.aitu.oop3.repositories.IReservationRepository;
import edu.aitu.oop3.repositories.IRoomRepository;
import edu.aitu.oop3.repositories.ReservationRepository;
import edu.aitu.oop3.repositories.RoomRepository;
import edu.aitu.oop3.services.BookingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB();
        IRoomRepository roomRepo = new RoomRepository(db);
        IReservationRepository resRepo = new ReservationRepository(db);
        BookingService service = new BookingService(roomRepo, resRepo);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- HOSTEL BOOKING SYSTEM ---");
            System.out.println("1. List All Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("0. Exit");
            System.out.print("Select option: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                service.getAllRooms().forEach(System.out::println);
            } else if (choice == 2) {
                System.out.print("Enter Room ID: ");
                int roomId = scanner.nextInt();
                System.out.print("Check-In (YYYY-MM-DD): ");
                String checkIn = scanner.next();
                System.out.print("Check-Out (YYYY-MM-DD): ");
                String checkOut = scanner.next();

                String result = service.makeReservation(1, roomId, checkIn, checkOut);
                System.out.println(result);
            } else if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }
        }
    }
}