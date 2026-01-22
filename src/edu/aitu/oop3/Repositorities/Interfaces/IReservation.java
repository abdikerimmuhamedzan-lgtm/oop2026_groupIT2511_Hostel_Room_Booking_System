package edu.aitu.oop3.Repositorities.Interfaces;

import edu.aitu.oop3.entities.Reservation;
import java.util.List;

public interface IReservationRepository {
    boolean createReservation(Reservation reservation);
    List<Reservation> getReservationsByGuestId(int guestId);
}