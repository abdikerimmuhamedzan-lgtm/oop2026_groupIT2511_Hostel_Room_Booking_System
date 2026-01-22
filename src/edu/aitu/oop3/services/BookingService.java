package edu.aitu.oop3.services;

import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.entities.Room;
import edu.aitu.oop3.repositories.Interfaces.IReservation;
import edu.aitu.oop3.repositories.Interfaces.Iroomrepository;

import java.sql.Date;
import java.util.List;

public class BookingService {
    private final Iroomrepository roomRepo;
    private final IReservation reservationRepo;

    public BookingService(Iroomrepository roomRepo, IReservation reservationRepo) {
        this.roomRepo = roomRepo;
        this.reservationRepo = reservationRepo;
    }

    public List<Room> getAllRooms() {
        return roomRepo.getAllRooms();
    }

    public String makeReservation(int guestId, int roomId, String checkInStr, String checkOutStr) {
        try {
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);
            Room room = roomRepo.getRoomById(roomId);
            if (room == null) {
                return "Error: Room not found with ID " + roomId;
            }

            // 2. Считаем дни и цену
            long differenceInMillis = checkOut.getTime() - checkIn.getTime();
            long days = differenceInMillis / (1000 * 60 * 60 * 24);

            if (days <= 0) {
                return "Error: Check-out date must be after check-in date.";
            }

            double totalPrice = days * room.getPrice();


            Reservation reservation = new Reservation(0, guestId, roomId, checkIn, checkOut, totalPrice, "CONFIRMED");
            boolean created = reservationRepo.createReservation(reservation);

            if (created) {
                return "Reservation created successfully! Total Price: " + totalPrice;
            } else {
                return "Error: Could not save reservation to database.";
            }

        } catch (IllegalArgumentException e) {
            return "Error: Invalid date format. Please use YYYY-MM-DD.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}