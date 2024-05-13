

public class Player {
	private int[] Position;
	public int health;
	public Player() {
		// TODO Auto-generated constructor stub
	}
	boolean firstRun = true;

	private int y;
	private int x;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Inventory _inventory;

	public int[] getPosition() {
		return Position;
	}

	public void MoveTo(int[] position) {

		if (position[0] < 0 || position[1] < 0 || position[0] > 4 || position[1] > 4) {
			System.out.println("Cant move At edge");
			return;
		} else {

			if (GameManager.MAP[position[1]][position[0]].canMove()) {
				Position = position;
				x = position[0];
				y = position[1];
				System.out.print("\n"+GameManager.MAP[y][x].artString);
				if(!firstRun) {
					GameManager.MAP[position[1]][position[0]].check();

				}
				firstRun=false;
			} else {
				return;
			}

		}
	}

	public Player(int[] startPos) {
		MoveTo(startPos);
		this.x = startPos[0];
		this.y = startPos[1];
		this._inventory = new Inventory();
		this.health = 100;
//		_inventory.addItem("wood", 10);
//		_inventory.addItem("coin", 100);


	}
	
	public void takeDamage(int amount) {
		health-=amount;
		if(!isAlive()) {
			GameManager._instance.set_GameState(GameManager.GameState.dead);

		}
	}
	public boolean isAlive() {
		return this.health>0;
	}
	public void eat() {
		if(_inventory.isItemInStock("food", 1)) {
			_inventory.getItem("food", 1);
			health+=30;
			GameManager.print("health boosted");
		}else {
			GameManager.print("No food");
		}
	}
}
