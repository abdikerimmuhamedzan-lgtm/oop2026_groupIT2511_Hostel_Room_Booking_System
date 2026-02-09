package edu.aitu.oop3.Reservation;

import edu.aitu.oop3.RoomManagement.Room;
import edu.aitu.oop3.RoomManagement.IRoomRepository;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookingService {
    private final IRoomRepository roomRepo;
    private final IReservationRepository reservationRepo;

    public BookingService(IRoomRepository roomRepo, IReservationRepository reservationRepo) {
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

    public List<Room> getAllRooms() { return roomRepo.getAllRooms(); }

    public List<Room> getAvailableRooms(String checkInStr, String checkOutStr) {
        try {
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);
            return roomRepo.getAvailableRooms(checkIn, checkOut);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid date format. Use YYYY-MM-DD.");
            return null;
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); return null; }
    }

    public String cancelReservation(int reservationId) {
        boolean updated = reservationRepo.updateStatus(reservationId, "CANCELLED");
        return updated ? "Reservation " + reservationId + " cancelled." : "Error: Could not cancel.";
    }

    public String makeReservation(int guestId, int roomId, String checkInStr, String checkOutStr) {
        try {
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);
            Room room = roomRepo.getRoomById(roomId);
            if (room == null) return "Error: Room not found with ID " + roomId;

            long diff = checkOut.getTime() - checkIn.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days <= 0) return "Error: Check-out must be after check-in.";

            List<Room> availableRooms = roomRepo.getAvailableRooms(checkIn, checkOut);
            boolean isFree = availableRooms.stream().anyMatch(r -> r.getId() == roomId);
            if (!isFree) return "Error: Room " + roomId + " is booked!";

            double totalPrice = days * room.getPrice();
            Reservation reservation = new Reservation(0, guestId, roomId, checkIn, checkOut, totalPrice, "CONFIRMED");
            boolean created = reservationRepo.createReservation(reservation);
            return created ? "Reservation created! Total: " + totalPrice : "Error: DB error.";

        } catch (IllegalArgumentException e) { return "Error: Invalid date."; }
        catch (Exception e) { return "Error: " + e.getMessage(); }
    }
}