package edu.aitu.oop3.RoomManagement;

public class RoomFactory {
    public static Room createRoom(String type, int number) {
        Room room = new Room();
        room.setNumber(number);
        room.setAvailable(true);
        room.setType(type);

        switch (type.toLowerCase()) {
            case "suite": room.setPrice(200.0); break;
            case "dorm": room.setPrice(30.0); break;
            case "standard": default:
                room.setPrice(100.0);
                room.setType("Standard");
                break;
        }
        return room;
    }
}