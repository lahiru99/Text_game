import java.util.HashMap;
import java.util.Map;

public class Inventory {

	Map<String, Item> _inventory = new HashMap<>();

	public Inventory() {

	}

	public void addItem(String key, int amount) {	
		if(key == "")return;
		
		try {
			_inventory.get(key).amount += amount;
		} catch (Exception e) {
			Item f = new Item(key, amount);
			_inventory.put(key, f);
		}

	}

	public Boolean getItem(String key, int amount) {
		try {
			if (_inventory.get(key).amount >= amount) {
				_inventory.get(key).amount -= amount;
			}else {
				return false;
			}
				return true;
		} catch (Exception e) {
			return false;
		}

	}
	
	public Boolean isItemInStock(String key, int amount) {
		try {
			if (_inventory.get(key).amount >= amount) {

			}else {
				return false;
			}
				return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void listItems() {
		for (Item i : _inventory.values()) {
			GameManager.print("\n" + i.key + " " + i.amount);
		}
	}

}
