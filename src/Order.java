import java.util.*;

public class Order {
	
	ArrayList<String> items;
	String type;

	public Order(String t) {
		items = new ArrayList<String>();
		type = t;
	}
	
	public void addItem(String item) {
		items.add(item);
	}
	
	public ArrayList<String> getItems() {
		return items;
	}
	
	public String getType() {
		return type;
	}
	
	public String toString() {
		String toReturn = type + ":\n";
		for(int i = 0; i < items.size(); i++) {
			toReturn += items.get(i) + "\n";
		}
		return toReturn;
	}
}
