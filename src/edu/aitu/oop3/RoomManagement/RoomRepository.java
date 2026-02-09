package edu.aitu.oop3.RoomManagement;

import edu.aitu.oop3.Common.IDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean create(Room entity) { return createRoom(entity); }
    @Override
    public Room getById(int id) { return getRoomById(id); }
    @Override
    public List<Room> getAll() { return getAllRooms(); }

    @Override
    public boolean createRoom(Room room) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "INSERT INTO rooms(number, type, price, is_available) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, room.getNumber());
            st.setString(2, room.getType());
            st.setDouble(3, room.getPrice());
            st.setBoolean(4, room.isAvailable());
            st.execute();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally { try { if (con != null) con.close(); } catch (SQLException e) {} }
    }

    @Override
    public Room getRoomById(int id) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id, number, type, price, is_available FROM rooms WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Room(rs.getInt("id"), rs.getInt("number"), rs.getString("type"), rs.getDouble("price"), rs.getBoolean("is_available"));
            }
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); }
        finally { try { if (con != null) con.close(); } catch (SQLException e) {} }
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT id, number, type, price, is_available FROM rooms";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            List<Room> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("id"), rs.getInt("number"), rs.getString("type"), rs.getDouble("price"), rs.getBoolean("is_available")));
            }
            return rooms;
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); return null; }
        finally { try { if (con != null) con.close(); } catch (SQLException e) {} }
    }

    @Override
    public List<Room> getAvailableRooms(Date checkIn, Date checkOut) {
        Connection con = null;
        try {
            con = db.getConnection();
            String sql = "SELECT * FROM rooms WHERE id NOT IN (" +
                    "SELECT room_id FROM reservations " +
                    "WHERE (check_in < ? AND check_out > ?) " +
                    "AND status <> 'CANCELLED')";
            PreparedStatement st = con.prepareStatement(sql);
            st.setDate(1, checkOut);
            st.setDate(2, checkIn);
            ResultSet rs = st.executeQuery();
            List<Room> rooms = new ArrayList<>();
            while (rs.next()) {
                rooms.add(new Room(rs.getInt("id"), rs.getInt("number"), rs.getString("type"), rs.getDouble("price"), rs.getBoolean("is_available")));
            }
            return rooms;
        } catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); return new ArrayList<>(); }
        finally { try { if (con != null) con.close(); } catch (SQLException e) {} }
    }
}