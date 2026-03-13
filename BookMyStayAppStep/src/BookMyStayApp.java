/**
 * * MAIN CLASS

 Use Case 6: Reservation Confirmation & Room Allocation
 Description:
 This class is responsible for confirming
 booking requests and assigning rooms.
 It ensures:Each room ID is unique
 Inventory is updated immediately
 No room is double-booked
 version 6.0
 @author Sajani G
 */
import java.util.HashMap;
import java.util.Map;
import java.util.*;
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
}
class RoomInventory {
    private HashMap<String, Integer> inventory;
    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
    public void decrementRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }
    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + " -> " + inventory.get(key));
        }
    }
}
class Reservation {
    private String guestName;
    private String roomType;
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
    public String getGuestName() {
        return guestName;
    }
    public String getRoomType() {
        return roomType;
    }
}
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }
    public Reservation getNextRequest() {
        return queue.poll();
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
class BookingService {
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();
    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }
    public void processBooking(Reservation reservation) {
        String roomType = reservation.getRoomType();
        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }
        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() +
                    (100 + new Random().nextInt(900));
        }
        while (allocatedRoomIds.contains(roomId));
        allocatedRoomIds.add(roomId);
        roomAllocations.putIfAbsent(roomType, new HashSet<>());
        roomAllocations.get(roomType).add(roomId);
        inventory.decrementRoom(roomType);
        System.out.println("\nReservation Confirmed");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Room ID: " + roomId);
    }
    public void displayAllocations() {
        System.out.println("\nAllocated Rooms:");
        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " -> " + roomAllocations.get(type));
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Hotel Booking System");
        System.out.println("Hotel Booking System v6.0");
        System.out.println("Application Started Successfully");
        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue requestQueue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);
        requestQueue.addRequest(new Reservation("Shirley Rubesh", "Single Room"));
        requestQueue.addRequest(new Reservation("Kavynaya Shankar", "Suite Room"));
        requestQueue.addRequest(new Reservation("Ash", "Double Room"));
        while (!requestQueue.isEmpty()) {
            Reservation reservation = requestQueue.getNextRequest();
            bookingService.processBooking(reservation);
        }
        bookingService.displayAllocations();
        inventory.displayInventory();
    }
}