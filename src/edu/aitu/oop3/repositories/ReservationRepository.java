package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.IDB;
import edu.aitu.oop3.entities.Reservation;
import edu.aitu.oop3.repositories.Interfaces.IReservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository implements IReservation {
    private final IDB db;

    public ReservationRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createReservation(Reservation reservation) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO reservations(guest_id, room_id, check_in, check_out, total_price, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            st.setInt(1, reservation.getGuestId());
            st.setInt(2, reservation.getRoomId());
            st.setDate(3, reservation.getCheckIn());
            st.setDate(4, reservation.getCheckOut());
            st.setDouble(5, reservation.getTotalPrice());
            st.setString(6, reservation.getStatus());

            st.execute();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public List<Reservation> getReservationsByGuestId(int guestId) {
        // Базовая реализация (можно расширить позже)
        return new ArrayList<>();
    }
}