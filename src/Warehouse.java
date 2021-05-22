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
        convertIntial(initial);
        for (int i = 0; i <= 30; i++) {
            deliver = sanitizeWarehouse(deliver);
            request = sanitizeWarehouse(request);
            needToDeliver = sanitizeWarehouse(needToDeliver);
            removeNull();
            getOrder(i);
        }

//        System.out.println(deliver);
//        System.out.println("===========================SEPERATION LINE===========================");
//        System.out.println(request);

    }

    public static ArrayList<BetterItem> sanitizeWarehouse(ArrayList<BetterItem> type) {
        // System.out.println(type);
        ArrayList<BetterItem> toReturn = new ArrayList<>();
        int i = 0;
        for (BetterItem item : type) {
            if (toReturn.size() == 0) {
                toReturn.add(item);
                toReturn.get(0).setItemCount(0);
            }
            int count = 0;
            for (BetterItem betterItem : toReturn) {
                if (!betterItem.getItemName().equals(item.getItemName())) {
                    count++;
                }
            }
            if (count == toReturn.size()) {
                toReturn.add(item);
                toReturn.get(i).setItemCount(0);
            }
        }

        // ArrayList<BetterItem> toReturnCount = new ArrayList<>();
        for (BetterItem item : toReturn) {
            for (BetterItem betterItem : type) {
                if (item.getItemName().equals(betterItem.getItemName())) {
                    System.out.println("toReturn is " + item.getItemCount() + " type is " + betterItem.getItemCount());
                    item.setItemCount(item.getItemCount() + betterItem.getItemCount());
                }
            }
        }
        return toReturn;
    }

    public static void convertIntial(ArrayList<Order> initial) {
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

    public static boolean cheackInStock() {
        return true;
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
