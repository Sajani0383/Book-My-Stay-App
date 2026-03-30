import java.util.*;
import java.io.*;

/*
================================================================
USE CASE 12: DATA PERSISTENCE & SYSTEM RECOVERY
================================================================

DESCRIPTION:
This program saves and loads room inventory from a file to
ensure system state is preserved across restarts.

================================================================
*/

class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRoom(String type, int count) {
        rooms.put(type, count);
    }
}

class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    String type = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    inventory.setRoom(type, count);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading inventory. Starting fresh.");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        service.loadInventory(inventory, filePath);

        System.out.println();
        System.out.println("Current Inventory:");
        System.out.println("Single: " + inventory.getRooms().get("Single"));
        System.out.println("Double: " + inventory.getRooms().get("Double"));
        System.out.println("Suite: " + inventory.getRooms().get("Suite"));

        service.saveInventory(inventory, filePath);

        System.out.println("Inventory saved successfully.");
    }
}