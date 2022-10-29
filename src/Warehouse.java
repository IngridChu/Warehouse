import java.util.*;

public class Warehouse {

    // deliver arraylist
    static ArrayList<String> deliverRaw = new ArrayList<String>();
    static ArrayList<Item> deliver = new ArrayList<Item>();
    // request arraylist
    static ArrayList<String> requestRaw = new ArrayList<String>();
    static ArrayList<Item> request = new ArrayList<Item>();

    public static void main(String[] args) {
        Order firstOrder = SupplyAndDemand.initialStock();
        SupplyAndDemand.generateOrders();
        deliverRaw.addAll(firstOrder.getItems());

        for (int i = 0; i < 30; i++) {
            Order nextOrder = SupplyAndDemand.nextOrder(i);
            if (nextOrder != null) {
                System.out.println("\nDay " + i + ": ");
                // if contains "Deliver" then add to delivery arraylist
                if (nextOrder.getType().contains("Deliver")) {
                    deliverRaw.addAll(nextOrder.getItems());
                    // System.out.println(deliverRaw);
                    parse(deliverRaw, deliver);
                    System.out.println("Deliver: " + deliver);

                }
                // if contains "Request" then add to request arraylist
                if (nextOrder.getType().contains("Request")) {
                    requestRaw.addAll(nextOrder.getItems());
                    // System.out.println(requestRaw);
                    parse(requestRaw, request);
                    // request will check if deliver has enough items
                    // if not, then request will add to queue
                    // if yes, then request will remove from deliver
                    check(deliver, request);
                    System.out.println("Request: " + request);

                }

            }
        }
        System.out.println("\nEnd of Month Results: ");
        System.out.println("Deliver: " + deliver);
        System.out.println("Request: " + request);
    }

    // check if deliver has enough items to fulfill request
    // if not then leave it in request
    // if yes then remove from deliver
    public static void check(ArrayList<Item> deliver, ArrayList<Item> request) {
        // System.out.println(request.size());
        // System.out.println(deliver.size());
        for (int i = 0; i < request.size(); i++) {
            // System.out.println("Checking " + request.get(i) + " in request");
            for (Item item : deliver) {
                // System.out.println("Checking " + deliver.get(j) + " in deliver");
                if (request.get(i).getName().equals(item.getName())) {
                    if (request.get(i).getQuantity() <= item.getQuantity()) {
                        System.out.println("Delivered " + request.get(i).getQuantity() + " " + request.get(i).getName());
                        item.setQuantity(item.getQuantity() - request.get(i).getQuantity());
                        request.remove(i);
                        i--;
                    }
                }
            }
        }
    }


    // parse the arraylist into item then add it to the new item arraylist
    public static void parse(ArrayList<String> raw, ArrayList<Item> items) {
        for (String s : raw) {
            String[] split = s.split(" ");
            int amount = Integer.parseInt(split[0]);
            String type = split[3];
            // loop through the items arraylist to see if the item already exists
            boolean exists = false;
            for (Item item : items) {
                if (item.getName().equals(type)) {
                    item.addAmount(amount);
                    exists = true;
                }
            }
            // if the item doesn't exist, create a new item and add it to the arraylist
            if (!exists) {
                Item newItem = new Item(type, amount);
                items.add(newItem);
            }
        }

    }


}

class Item {
    String name;
    int quantity;

    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return quantity + " " + name;
    }

    public void addAmount(int amount) {
        quantity += amount;
    }

    public void removeAmount(int amount) {
        quantity -= amount;
    }
}


