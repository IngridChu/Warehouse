//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Random;

public class SupplyAndDemand {
    static Random rng = new Random(5L);
    static ArrayList[] orders = new ArrayList[30];
    static String[] types = new String[]{"boxes of FINAL FANTASY VII", "packages of cereal", "packages of toilet paper", "crates of hand sanitizer", "boxes of beef", "barrels of wine", "gallons of milk", "pounds of chicken", "crates of lettuce", "boxes of masks"};

    public SupplyAndDemand() {
    }

    public static Order initialStock() {
        Order firstOrder = new Order("Deliver");
        firstOrder.addItem("50 " + types[0]);
        firstOrder.addItem("25 " + types[1]);
        firstOrder.addItem("30 " + types[2]);
        firstOrder.addItem("7 " + types[4]);
        firstOrder.addItem("5 " + types[8]);
        firstOrder.addItem("60 " + types[9]);
        return firstOrder;
    }

    public static void generateOrders() {
        for(int i = 0; i < 30; ++i) {
            orders[i] = new ArrayList();
            int quantity = rng.nextInt(4) + 2;

            for(int j = 0; j < quantity; ++j) {
                Order newOrder;
                if (rng.nextInt(2) == 0) {
                    newOrder = new Order("Deliver");
                } else {
                    newOrder = new Order("Request");
                }

                int numberOfItems = rng.nextInt(4) + 2;

                for(int k = 0; k < numberOfItems; ++k) {
                    int amount = rng.nextInt(25) + 5;
                    int type = rng.nextInt(types.length);
                    newOrder.addItem(amount + " " + types[type]);
                }

                orders[i].add(newOrder);
            }
        }

    }

    public static Order nextOrder(int day) {
        if (day >= 0 && day < 30) {
            return orders[day].size() > 0 ? (Order)orders[day].remove(0) : null;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        generateOrders();
        System.out.println(initialStock());
        System.out.println(nextOrder(0));
        System.out.println(nextOrder(0));
        System.out.println(nextOrder(0));
        System.out.println(nextOrder(0));
    }
}
