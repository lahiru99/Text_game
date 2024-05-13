

public class Enemy {
	public int health;
	public int damage;

	/**
	 * @param health
	 */
	public Enemy(int health,int damage) {
		this.health = health;
	}
	
	public void takeDamage(int amount) {
		this.health -= amount;
	}
	public boolean isAlive() {
		return this.health>0;
	}
	
	
}
