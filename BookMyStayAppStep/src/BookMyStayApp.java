/**
 * * MAIN CLASS
 Use Case 3: Centralized Room Inventory Management
 Description:
 This class acts as the single source of truth
 for room avaitabitity in the hotel.
 Room pricing and characteristics are Obtained
 from Room objects, not duplicated nere.
 This avoids multiple sources of truth and
 keeps responsibilities clearly separated.
 @author Sajani G
 @version 3.0
 */
import java.util.HashMap;
import java.util.Map;
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}
class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}
class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 600, 400);
    }
}
class RoomInventory {
    private HashMap<String, Integer> inventory;
    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 6);
        inventory.put("Double Room", 9);
        inventory.put("Suite Room", 1);
    }
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
    public void updateAvailability(String roomType, int newCount) {
        inventory.put(roomType, newCount);
    }
    public void displayInventory() {
        System.out.println("\n===== CURRENT ROOM INVENTORY =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
        System.out.println("----------------------------------");
    }
}
abstract class Room {
    private String roomType;
    private int beds;
    private int size;
    private double price;
    public Room(String roomType, int beds, int size, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.price = price;
    }
    public String getRoomType() {
        return roomType;
    }
    public int getBeds() {
        return beds;
    }
    public int getSize() {
        return size;
    }
    public double getPrice() {
        return price;
    }
    public void displayRoomDetails() {
        System.out.println("Room Type : " + roomType);
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq ft");
        System.out.println("Price     : $" + price);
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Hotel Booking System");
        System.out.println("Hotel Booking System v3.0");
        System.out.println("Application Started Successfully");
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        RoomInventory inventory = new RoomInventory();
        System.out.println("===== ROOM DETAILS =====\n");
        single.displayRoomDetails();
        System.out.println("Available : " + inventory.getAvailability(single.getRoomType()));
        System.out.println("----------------------------------");
        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + inventory.getAvailability(doubleRoom.getRoomType()));
        System.out.println("----------------------------------");
        suite.displayRoomDetails();
        System.out.println("Available : " + inventory.getAvailability(suite.getRoomType()));
        System.out.println("----------------------------------");
        inventory.displayInventory();
        System.out.println("\nApplication Terminated.");
    }
}