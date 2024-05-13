import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class Shop {
	
	Map<String, Item> _shopI = new HashMap<>();
	
	public static Shop _instance;
	
	Shop(){
		_instance = this;
		InitShop();
	}
	
	public Item buy(String key) {
		try {
			Item i = _shopI.get(key);
			if(GameManager
					._instance
					.player
					._inventory
					.isItemInStock("coin", i.price)) {
				
				GameManager
				._instance
				.player
				._inventory
				.getItem("coin",  i.price);
				
				return i;
				
			}
		} catch (Exception e) {
			System.out.print("Item not available");
		}
		
		return new Item("", 0);
	}
	
	
	public void InitShop() {
		_shopI.put("wood", new Item(10,"wood"));
		_shopI.put("shoes", new Item(10,"shoes"));
		_shopI.put("paddle", new Item(10,"paddle"));
		_shopI.put("paddle", new Item(10,"password"));
	}
	
	public void enterShop() {
		Scanner userInput = new Scanner(System.in);
		String line = "";
		listShop();
		while (!GameManager.is(line, "exit") ) {
			System.out.print("\nSHOP>");
			line = userInput.nextLine();
			String leftAlignFormat = "| %-15s | %-4d |%n";
			try {
			if(line.contains("bag")) {GameManager._instance.player._inventory.listItems();}
			else if(line.contains("buy")) {
				String[] s = line.split(" ");
				int amount=0;
				if(s[0].contains("buy")) {

					
						amount = Integer.parseInt(s[1]);
						

					if(_shopI.containsKey(s[2])) {
						Item item = _shopI.get(s[2]);
						int totalCost = item.price*amount;
						if(GameManager._instance.player._inventory.isItemInStock("coin", totalCost)) {
							GameManager._instance.player._inventory.addItem(s[2], amount);
							GameManager._instance.player._inventory.getItem("coin", totalCost);
							System.out.print("Purchased "+s[1]+"-"+s[2]);
						}else {
							System.out.print("Out of cash");
						}

					}
				}
			}
			} catch (Exception e) {
				// TODO: handle exception
			}
	
		}
	}
	
	public void listShop() {
		String leftAlignFormat = "| %-15s | %-4d |%n";
		System.out.format("+-----------------+------+%n");
		System.out.format("| NAME            | PRICE|%n");
		System.out.format("+-----------------+------+%n");
		for (Item i : _shopI.values()) {
	
			System.out.format(leftAlignFormat, i.key, i.price);
		}
		System.out.format("+-----------------+------+%n");
	}
}
