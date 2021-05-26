import java.util.*;

public class Warehouse {

    private static ArrayList<BetterItem> deliver = new ArrayList<>();
    private static ArrayList<BetterItem> request = new ArrayList<>();
    private static ArrayList<BetterItem> needToDeliver = new ArrayList<>();

    private static ArrayList<Order> initial = new ArrayList<>();

    public static void main(String[] args) {
        // Day 0
        SupplyAndDemand.generateOrders();
        initial.add(SupplyAndDemand.initialStock());
        convertInitial(initial);
        for (int i = 0; i <= 30; i++) {
            deliver = sanitizeWarehouse(deliver);
            request = sanitizeWarehouse(request);
            needToDeliver = sanitizeWarehouse(needToDeliver);
            checkAndRemoveStock(deliver, request, needToDeliver);
            needToDeliver = sanitizeWarehouse(needToDeliver);
            System.out.println();
            System.out.println("Day " +  i + " Status: ");
            System.out.println("===== Currently in Stock: =====");
            printStatus(deliver);
            System.out.println("===== Delivered today: =====");
            printStatus(request);
            System.out.println("===== Need to deliver: =====");
            printStatus(needToDeliver);
            removeNull();
            getOrder(i);
        }
//        System.out.println(deliver);
//        System.out.println("===========================SEPERATION LINE===========================");
//        System.out.println(request);

    }

    public static void printStatus(ArrayList<BetterItem> list) {
        for (BetterItem betterItem : list) {
            System.out.println(betterItem.getItemCount() + " " + betterItem.getItemName());
        }
    }

    public static void checkAndRemoveStock(ArrayList<BetterItem> deliver, ArrayList<BetterItem> request, ArrayList<BetterItem> needToDeliver) {

        for (BetterItem item : request) {
            int count = 0;
            for (BetterItem betterItem : deliver) {
                // requesting the same item
                if (betterItem.getItemName().equals(item.getItemName())) {
                    int stockCount = betterItem.getItemCount();
                    int requestCount = item.getItemCount();
                    // if not enough
                    if (stockCount < requestCount) {
                        needToDeliver.add(item);
                    }
                    // if enough
                    else {
                        count++;
                        betterItem.setItemCount(stockCount - requestCount);
                    }
                }
            }
            if (count == deliver.size()) {
                needToDeliver.add(item);
            }
        }
    }

    public static ArrayList<BetterItem> sanitizeWarehouse(ArrayList<BetterItem> type) {
        // System.out.println("TYPE IS: " + type);
        ArrayList<BetterItem> toReturn = new ArrayList<>();
        ArrayList<String> stringOfItems = new ArrayList<>();
        for (BetterItem item : type) {
            if (stringOfItems.size() == 0) {
                stringOfItems.add(item.getItemName());

            }
            int count = 0;
            for (String betterItem : stringOfItems) {
                if (!betterItem.equals(item.getItemName())) {
                    count++;
                }
            }
            if (count == stringOfItems.size()) {
                stringOfItems.add(item.getItemName());
            }
        }
        int count[] = new int [stringOfItems.size()];

        for(int j = 0; j < stringOfItems.size(); j++) {
            for (BetterItem betterItem : type) {
                if (stringOfItems.get(j).equals(betterItem.getItemName())) {
                    count[j] = count[j] + betterItem.getItemCount();
                }
            }
        }
        for(int i = 0; i < stringOfItems.size(); i++) {
            toReturn.add(new BetterItem(count[i], stringOfItems.get(i)));
        }
        // System.out.println("TO_RETURN IS" + toReturn);
        return toReturn;
    }

    public static void convertInitial(ArrayList<Order> initial) {
        for (Order order : initial) {
            changeFormAndAdd(order, deliver);
        }
    }

    public static void getOrder(int day) {
        while (SupplyAndDemand.nextOrder(day) != null) {
            Order current = SupplyAndDemand.nextOrder(day);
            if (current != null && current.getType().equals("Deliver")) {
                changeFormAndAdd(current, deliver);
            } else if (current != null && current.getType().equals("Request")) {
                changeFormAndAdd(current, request);
            }
        }
    }

    private static void changeFormAndAdd(Order current, ArrayList<BetterItem> requestOrDeliver) {
        String changeForm = current.toString();
        String[] changeFormArray = changeForm.split("\n");
        changeFormArray = Arrays.copyOfRange(changeFormArray, 1, changeFormArray.length);
        int itemCount;
        String itemCountInString;
        String item;
        for (String s : changeFormArray) {
            itemCountInString = "";
            for (int j = 0; j < s.length(); j++) {
                if (Character.isDigit(s.charAt(j))) {
                    itemCountInString += Character.toString(s.charAt(j));
                }
            }
            item = "";
            for (int j = 0; j < s.length(); j++) {
                if (!Character.isDigit(s.charAt(j))) {
                    item += Character.toString(s.charAt(j));
                }
            }
            itemCount = Integer.parseInt(itemCountInString);
            BetterItem addTo = new BetterItem(itemCount, item);
            requestOrDeliver.add(addTo);
        }
    }

    public static void removeNull() {
        for (int i = 0; i < deliver.size(); i++) {
            if (deliver.get(i) == null) {
                deliver.remove(i);
                i--;
            }
        }
        for (int i = 0; i < request.size(); i++) {
            if (request.get(i) == null) {
                request.remove(i);
                i--;
            }
        }
    }

}

class BetterItem {

    private int itemCount;
    private String itemName;

    public BetterItem(int itemCount, String itemName) {
        this.itemCount = itemCount;
        this.itemName = itemName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public String toString() {
        return "BetterItem{" +
                "itemCount=" + itemCount +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
