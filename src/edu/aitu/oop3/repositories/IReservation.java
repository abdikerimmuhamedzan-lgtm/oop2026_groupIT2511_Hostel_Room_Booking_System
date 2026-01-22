package edu.aitu.oop3.repositories;

import edu.aitu.oop3.entities.Reservation;
import java.util.List;

public interface IReservation {
    boolean createReservation(Reservation reservation);
    List<Reservation> getReservationsByGuestId(int guestId);
}