
public class Item {

	public String key;
	int amount;
	//player inventory
	public Item(String name, int amount) {
		this.key = name;
		this.amount = amount;
	}
	
	public int price;
	//shop Item
	public Item(int price,String name) {
		this.key = name;
		this.price = price;
	}
	
	
	
	
}
