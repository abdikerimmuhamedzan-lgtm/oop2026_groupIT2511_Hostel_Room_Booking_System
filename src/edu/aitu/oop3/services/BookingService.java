package edu.aitu.oop3.services;

import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.entities.Room;
import edu.aitu.oop3.repositories.IReservation;
import edu.aitu.oop3.repositories.Iroomrepository;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private final Iroomrepository roomRepo;
    private final IReservation reservationRepo;

    public BookingService(Iroomrepository roomRepo, IReservation reservationRepo) {
        this.roomRepo = roomRepo;
        this.reservationRepo = reservationRepo;
    }

    public List<Room> getRoomsFilteredByPrice(double maxPrice) {
        List<Room> allRooms = roomRepo.getAllRooms();
        if (allRooms == null) return null;

        return allRooms.stream()
                .filter(room -> room.getPrice() <= maxPrice)
                .sorted(Comparator.comparingDouble(Room::getPrice))
                .collect(Collectors.toList());
    }

    public List<Room> getAllRooms() {
        return roomRepo.getAllRooms();
    }

    public List<Room> getAvailableRooms(String checkInStr, String checkOutStr) {
        try {
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);
            return roomRepo.getAvailableRooms(checkIn, checkOut);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public String cancelReservation(int reservationId) {
        boolean updated = reservationRepo.updateStatus(reservationId, "CANCELLED");
        if (updated) {
            return "Reservation " + reservationId + " cancelled successfully.";
        } else {
            return "Error: Could not cancel reservation (ID might be wrong).";
        }
    }

    public String makeReservation(int guestId, int roomId, String checkInStr, String checkOutStr) {
        try {
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);

            Room room = roomRepo.getRoomById(roomId);
            if (room == null) {
                return "Error: Room not found with ID " + roomId;
            }

            long differenceInMillis = checkOut.getTime() - checkIn.getTime();
            long days = differenceInMillis / (1000 * 60 * 60 * 24);

            if (days <= 0) {
                return "Error: Check-out date must be after check-in date.";
            }

            List<Room> availableRooms = roomRepo.getAvailableRooms(checkIn, checkOut);
            boolean isFree = false;
            for (Room r : availableRooms) {
                if (r.getId() == roomId) {
                    isFree = true;
                    break;
                }
            }

            if (!isFree) {
                return "Error: Room " + roomId + " is already booked for these dates!";
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