package edu.aitu.oop3.Reservation;

import java.util.List;

public interface IReservationRepository {
    boolean createReservation(Reservation reservation);
    List<Reservation> getReservationsByGuestId(int guestId);
    boolean updateStatus(int id, String status);
}