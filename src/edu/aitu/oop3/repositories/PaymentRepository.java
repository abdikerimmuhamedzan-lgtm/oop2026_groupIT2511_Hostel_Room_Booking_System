package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.IDB;
import edu.aitu.oop3.entities.Payment;
import java.sql.*;

public class PaymentRepository implements IPaymentRepository {
    private final IDB db;

    public PaymentRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createPayment(Payment payment) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO payments(reservation_id, amount, status) VALUES (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

            if (payment.getReservationId() == 0) {
                st.setNull(1, Types.INTEGER);
            } else {
                st.setInt(1, payment.getReservationId());
            }

            st.setDouble(2, payment.getAmount());
            st.setString(3, payment.getStatus());

            st.execute();
            System.out.println("Payment saved to DB successfully!");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}