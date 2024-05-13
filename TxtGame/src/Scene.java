
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import jdk.jfr.Percentage;



public class Scene {
	// scene name
	public String scene;
	// ASCII art
	public String artString;
	// item that you get
	public String item;
	// hint
	public String hint;
	// reachable directions in string form
	public String link;
	// Item needed to build something
	public String exchangeItem;
	// amount of Items needed to build
	public int exchangeAmount;
	// if lock u can't go to the this scene,until conditions are met
	public boolean lock;
	// Percentage that ull find an item
	public int item_percent = 60;
	//
	public int Enemy_percentage = 20;
	//
	public String EnemyPic;
	boolean shopAvailable;
	// empty scene
	public Scene() {
		this("", "", "", "", 0, "", "","");
	}
	
	//shop scene
	public Scene(String scene, String art,String dialogue, String link,String enemy) {
		this(scene, art, "",false,"",0, dialogue, link, enemy,30);
		shopAvailable = true;
	}
	// scene without lock
	public Scene(String scene, String art, String item, String Eitem, int Amount, String dialogue, String link,String enemy) {
		this(scene, art, item,false,Eitem,Amount, dialogue, link, enemy,30);
	}
	
	// full scene  ("shark infested",Stuff.art[3],"",true,"boat",1,"I Hope sharks dont eat me", "w e","🦈");
	public Scene(String scene, String art, String item,Boolean l,String Eitem, int Amount, String dialogue, String link,String Enemy,int Percentage) {
		this.scene = scene;
		this.item = item;
		this.hint = dialogue;
		this.link = link;
		this.exchangeAmount = Amount;
		this.exchangeItem = Eitem;
		this.artString = art;
		this.EnemyPic = Enemy;
		this.lock = l;
		this.Enemy_percentage = Percentage;
	}

	// interacts
	public void look() {
		GameManager.print("{•̃_•̃} " + hint+"\n");
		if (exchangeItem != "") {
			GameManager.print("\ni can get a " + item + " for " + exchangeAmount + " " + exchangeItem);
		} else {
			check();
		}

	}

	public Item use() {
		if (exchangeItem != "") {
			if (GameManager._instance.player._inventory.getItem(exchangeItem, exchangeAmount)) {
				GameManager.print("✅ You got a " + item);
				return new Item(item, 1);
			} else {
				GameManager.print("⚠️looks i need " + exchangeAmount + " of " + exchangeItem + " to get a " + item);
				return new Item("", 0);

			}

		} else {
			GameManager.print("⚠️ Nothin here to exchange");
			return new Item("", 0);
		}
	}

	// checks the lock
	public boolean canMove() {
		if (lock) {
			if (GameManager._instance.player._inventory.isItemInStock(exchangeItem, exchangeAmount)) {
				GameManager._instance.player._inventory.getItem(exchangeItem, exchangeAmount);
				return true;
			} else {
				GameManager.print("❌Ops, i need a " + exchangeItem + "❌");
				return false;
			}

		} else {
			return true;
		}
	}

	public void check() {

		boolean touble = R(Enemy_percentage);
		Enemy enemy = new Enemy(100, 20);
		if (touble) {
			GameManager.print("⚠️⚠️⚠️⚠️⚠️⚠️ATTACK️⚠️⚠️⚠️⚠️⚠️⚠️⚠️\n️");
			GameManager.print("Press a and enter as fast as you can,or you die\n");
			while (GameManager._instance.player.isAlive() && enemy.isAlive()) {
				float x = 10; // wait 2 seconds at most
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				long startTime = System.currentTimeMillis();
				try {
					while ((System.currentTimeMillis() - startTime) < x * 1000 && !in.ready()
							&& GameManager._instance.player.isAlive() && enemy.isAlive()) {
						GameManager._instance.player.takeDamage(20);
						System.out.println("👊"+EnemyPic+"   🤕❤️:" + GameManager._instance.player.health);
						Thread.sleep(1000);
					}
					if (in.ready()) {
						if (in.read() == 'a') {
							enemy.takeDamage(50);
							System.out.println("👊😠   👿🖤:" + enemy.health);
						}

					}
				} catch (IOException e) {
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (GameManager._instance.player.isAlive()) {
				System.out.print("You kill the Monster\n");
				int f_amount =(int) (Math.random() * (20 - 10)) + 10;
				int c_amount =(int) (Math.random() * (20 - 10)) + 10;
				GameManager._instance.player._inventory.addItem("food",f_amount);
				GameManager._instance.player._inventory.addItem("coin",c_amount);
				GameManager.print("you found "+f_amount+" food & "+c_amount+" coins\n");
				
			} else {

				GameManager._instance.set_GameState(GameManager.GameState.dead);
			}
		}else {
			if(exchangeItem!="")return;
			if (R(item_percent)) {
				GameManager.print("\n✅ You found one " + item);
				Item i =  new Item(item, 1);
				GameManager._instance.player._inventory.addItem(i.key,i.amount);
			}else {
			GameManager.print("\nbad luck, look again");}
		}

	}

	public boolean R(int percentage) {
		Random r = new Random();
		int i = r.nextInt(100);

		if (i <= percentage) {
			return true;
		}
		return false;

	}
}
