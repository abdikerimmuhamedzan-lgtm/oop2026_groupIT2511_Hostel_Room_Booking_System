package edu.aitu.oop3.RoomManagement;

import edu.aitu.oop3.Common.IGenericRepository;
import java.sql.Date;
import java.util.List;

public interface IRoomRepository extends IGenericRepository<Room> {
    boolean createRoom(Room room);
    Room getRoomById(int id);
    List<Room> getAllRooms();
    List<Room> getAvailableRooms(Date checkIn, Date checkOut);
}