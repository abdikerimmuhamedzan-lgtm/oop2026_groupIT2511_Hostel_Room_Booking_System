package edu.aitu.oop3.repositories.Interfaces;

import edu.aitu.oop3.entities.Room;
import java.util.List;

public interface Iroomrepository {
    boolean createRoom(Room room);
    Room getRoomById(int id);
    List<Room> getAllRooms();
}