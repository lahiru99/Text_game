import java.util.Scanner;



public class GameManager {

	public static enum GameState {
		Intro, Prepare, Playing,resume, dead,
	}

	private GameState _GameState;

	public void set_GameState(GameState _GameState) {
		this._GameState = _GameState;
	}

	public static Scene[][] MAP;
	public Player player;
	
	public static GameManager _instance;
	public GameManager() {
		_instance = this;
		game();
	}
	

	public void game() {
		Scanner userInput = new Scanner(System.in);
		String line;
		set_GameState(GameState.Intro);

		while (true) {

			switch (_GameState) {
			case Intro:
				print(Stuff.art[0]);
				line = userInput.nextLine();
				
				typeWriter(Stuff.dialogues[0]);
				line = userInput.nextLine();
				typeWriter(Stuff.dialogues[1]);
				print("Enter to continue");
				line = userInput.nextLine();
				set_GameState(GameState.Prepare);
				new Shop();
				break;
			case Prepare:
				print(Stuff.tables[0]);
				line = "";
				while (!is(line, "exit")) {
					print("\nMENU>");
					line = userInput.nextLine();
					if (is(line, "map")) {
						print(Stuff.tables[1]);
					} else if (is(line, "help")) {
						print(Stuff.tables[2]);
					} else if (is(line, "play")) {
						set_GameState(GameState.Playing);
						break;
					}else if(is(line, "menu")) {
						print(Stuff.tables[0]);
					}
				}
				break;
			case Playing:
				resetGame();

				line = "";

				while (!is(line, "exit") && !(_GameState == GameState.dead)) {
					print("\nPlaying>");

					line = userInput.nextLine();
					//-------------------------
					if (is(line, "map")) {
						print(Stuff.tables[1]);
					}
					//-------------------------
					else if (is(line, "help")) {
						
					}
					//-------------------------
					else if (is(line, ("look|l"))) {
						MAP[player.getY()][player.getX()].look();
					//-------------------------
					}
					else if (is(line, ("use|u"))) {
						Item item = MAP[player.getY()][player.getX()].use();
						player._inventory.addItem(item.key,item.amount);
					//-------------------------
					}
					else if (is(line, "bag")) {
						player._inventory.listItems();
					//-------------------------
					}else if(is(line, "eat")){
						player.eat();
					}
					else if (is(line, "health|h")) {
						print("🙂❤️"+player.health);
					//-------------------------
					} else if(is(line, "shop")) {
						if(MAP[player.getY()][player.getY()].shopAvailable) {
							Shop._instance.enterShop();
						}
					}
					else if (is(line, "menu")) {
						line = "exit";
						print("");
						set_GameState(GameState.Prepare);
						
					}
					else if ("w n e s nw ne se sw".contains(line) && !"\n".contains(line)){
						move(line);
					}else {
						print("");
					}
					//-------------------------

				}

				break;
				
			case dead:
				print("⚰️ YOU DIED⚰️ !\n type menu to go to menu");
				line = "";
				
				while (!is(line, "menu")){
					line = userInput.nextLine();
				}
				set_GameState(GameState.Prepare);
	
			default:
				print("**** invalid ***\n");
				break;
			}

		}
	}
	
	public void resetGame() {
		MAP = InitiateMap();
		this.player = new Player(new int[] {3,3 });
	}

	public void move(String line) {

		String d = MAP[player.getY()][player.getX()].link;
		int currX = player.getX();
		int currY = player.getY();
		
		int[] newPos= new int[2];

		if (d.contains(line)) {
			if (is(line, "w")) {
				// print("w");
				newPos[0]=currX - 1;newPos[1]=currY;
			} else if (is(line, "nw")) {
				// print("nw");
				newPos[0]=currX - 1;newPos[1]=currY-1;
			} else if (is(line, "n")) {
				// print("n");
				newPos[0]=currX;newPos[1]=currY-1;
			} else if (is(line, "ne")) {
				// print("ne");
				newPos[0]=currX + 1;newPos[1]=currY-1;
			} else if (is(line, "e")) {
				// print("e");
				newPos[0]=currX + 1;newPos[1]=currY;
			} else if (is(line, "se")) {
				// print("se");
				newPos[0]=currX+1;newPos[1]=currY+1;
			} else if (is(line, "s")) {
				// print("s");
				newPos[0]=currX;newPos[1]=currY+1;
			} else if (is(line, "sw")) {
				// print("sw");
				newPos[0]=currX - 1;newPos[1]=currY+1;
			}
			
			player.MoveTo(newPos);
		} else {
			print(" ✖ Cant Move "+line);
		}

		String loc = MAP[player.getY()][player.getX()].scene;
	
	}

	// lazy codes
	public static boolean is(String s, String s2) {
		return s.matches(s2);
	}

	public static void print(String string) {
		System.out.print(string);
	}
	
	public Scene[][] InitiateMap() {

		Scene l0 = new Scene();
		Scene l1 = new Scene("forest",Stuff.art[2], "wood", "",0,"look around to find wood","e w","🦘");
		Scene l2 = new Scene("rocky beach",Stuff.art[1] ,"boat","wood", 4,"Look for the magical wood,Go to the enchanted woods","w e","👹");
		Scene l3 = new Scene("shark infested",Stuff.art[3],"",true,"boat",1,"I Hope this boat survives", "w e","🦈",98);
		Scene l3i= new Scene("shark infested",Stuff.art[3],"",true,"boat",1,"I Hope sharks dont eat me", "w n","🦈",98);
		Scene l4 = new Scene("sandy beach",Stuff.art[4],"Looks like there is a shop, type shop", "s nw ne","👹");
		Scene l5 = new Scene("jungle",Stuff.art[5], "", "",0,"","w e se","👾");
		Scene l6= new Scene("River",Stuff.art[6],"",true,"plank",1,"Scary river","w e n s","👾",98);
		Scene l7 = new Scene("waterfall",Stuff.art[8],"","",0, "", "","👾");
		Scene l8 = new Scene("mountains",Stuff.art[7], "", "",0,"", "nw sw","👾");
		Scene l9 = new Scene("jungle",Stuff.art[5], "","",0, "", "s e","👾");
		Scene l10 = new Scene("desert",Stuff.art[9], "","",0, "", "w se","👾");
		Scene l11 = new Scene("cave tresure",Stuff.art[10], "","",0, "", "w e","👾");
		Scene l12 = new Scene("gravel path",Stuff.art[11], "","",0, "", "w e","👾");
		Scene l13 = new Scene("fishing village",Stuff.art[12], "","",0, "",  "w e","👾");
		Scene l14 = new Scene("Home",Stuff.art[13], "","",0, "",  "","👾");

		Scene[][] map = { 
				{ l14, l13, l12, l11, l0 }, 
				{ l0, l9, l9, l10, l0 }, 
				{ l7, l6, l5, l0, l8 },
				{ l0, l0, l0, l4, l0 }, 
				{ l1, l2, l3, l3i, l0 } };

		return map;
	}
	
	public void typeWriter(String s) {
		for (char e : s.toCharArray()) {
			System.out.print(e);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	

}
