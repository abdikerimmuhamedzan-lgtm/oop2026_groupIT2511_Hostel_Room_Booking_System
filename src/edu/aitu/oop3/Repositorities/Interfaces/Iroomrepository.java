package edu.aitu.oop3.Repositorities.Interfaces;

import edu.aitu.oop3.entities.Room;
import java.util.List;

public interface IRoomRepository {
    boolean createRoom(Room room);
    Room getRoomById(int id);
    List<Room> getAllRooms();
}