package rpg_Game;
import java.util.Random; 
import java.util.Scanner;

public class game {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		Random rand = new Random();
		int rand_int = rand.nextInt(10);
		int width = 8, height = 8, timeLimit = 30, health = 100;
		int[][] map = new int[width][height];
		int[][] mapPos = new int[width][height];
		int[] heroPos = new int[2];
		int random1,win = 0;
		
		buildMap(map,mapPos,width,height,rand);
		
		while ((heroPos[0] != width - 1) || (heroPos[1] != height - 1)) { 
			
			if(timeLimit > 0 && health > 0) {
				displayMap(map,health,timeLimit);
				win = playerMove(heroPos,map,width,height,input,rand);
				health = enemyEncounter(rand,input,health);
				timeLimit -= 2;
				
			if(timeLimit == 6)
				System.out.print("You're running out of time!\n");
			
			if(timeLimit <= 6 && health <= 0) { 
				System.out.print("Health: "+health+"\n");
				System.out.print("Time Limit: "+timeLimit+"\n");
				System.out.print("Game over. You died.");
				break;
			}
			if(timeLimit == 0 || timeLimit < 0) {
				System.out.print("Health: "+health+"\n");
				System.out.print("Time Limit: "+timeLimit+"\n");
				System.out.print("Game over. You ran out of time.");
				break;
			}
			if (health < 0 || health == 0) {
				System.out.print("Health: "+health+"\n");
				System.out.print("Time Limit: "+timeLimit+"\n");
				System.out.print("Game over. You died.");
				break;
			}
			if(win == 1) 
				break;
		}
	  }
	}
	
	public static void buildMap(int[][] map, int[][] mapPos, int width, int height, Random rand){
		
		  for(int i=0; i<width; i++){
		   for(int j=0; j<height; j++){
		    map[i][j]=0;
		    mapPos[i][j] = 0;
		    }
		 }
		  mapPos[0][0] = 0;
		  map [0][0] = 2;
		  map[0][1] = 1;
		  map[1][0] = 1;
		  map[1][1] = 1;
		  map[(width-1)][(height-1)] = 3;
		}
	
	
	public static void displayMap(int [][]map, int health, int timeLimit){
		
		char character = ' ';
		System.out.print("Health: "+health+"\n");
		System.out.print("Time Limit: "+timeLimit+"\n\n");
		  for(int row=0; row<map.length; row++){
			  for(int col=0; col<map[row].length; col++){
				  if(map[row][col] == 0)
					  character = 'X';
				  else if(map[row][col] == 3)
					  character = 'G';
				  else if(map[row][col] == 2)
					  character = 'H';
				  else if(map[row][col] == 0)
					  character = ' ';
				  else if(map[row][col] == 1)
					  character = '*';
			    System.out.print("|"+character);
			    }
			    System.out.println("|");
			  }
	}
	

	public static int playerMove(int[] heroPos, int[][] map, int width, int height, Scanner input, Random rand) {
		
		int move = 0,win = 0;
		System.out.print(
			   "\n1) North\n"
				+"2) South\n"
				+"3) East\n"
				+"4) West\n"
				+"\nEnter 1 - 4 to move: \n"
			);
		 move = input.nextInt();
		 while (move < 1 || move > 4) {
			 System.out.print("Enter a number 1 - 4");
			 move = input.nextInt();
		 }
		    if (move == 1)
		        heroPos[0] -= 1;
		      else if (move == 2)
		        heroPos[0] += 1;
		      else if (move == 3)
		        heroPos[1] += 1;
		      else if (move == 4) 
		        heroPos[1] -= 1;
		      
		    for (int i = -1; i <= 1; i++) {
		        for (int j = -1; j <= 1; j++) {
		          if ((heroPos[0] + i >= 0) && (heroPos[0] + i <= height - 1) && (heroPos[1] + j >= 0) && (heroPos[1] + j <= width - 1)) {
		            map[(heroPos[0] + i)][(heroPos[1] + j)] = rand.nextInt(2);
		          }
		       }
		    }
	          map[heroPos[0]][heroPos[1]] = 2;
	          if(map[heroPos[0]][heroPos[1]] == map[(width-1)][(height-1)]) {
	        	  map[heroPos[0]][heroPos[1]] = 2;
	        	  System.out.print("Congratulations, You've saved the hostages!");
	        	  win =1;
	          }
	          map[(width-1)][(height-1)] = 3;
	          if(map[heroPos[0]][heroPos[1]] == map[(width-1)][(height-1)]) {
	        	  input.close();
	          }
	          return win;
	}

	
	public static int enemyEncounter(Random rand, Scanner input,int health){
		
		int random = rand.nextInt(3);
		int choice = 0;
		int energy = health;
			
		if(random == 1){
			System.out.print("\nEnemy appeared!\n");
			System.out.print("\n 1) Fight\n 2) Hide");
			System.out.print("\n\nEnter 1 -2: ");
			choice = input.nextInt();
			
			while(choice < 1 || choice > 2) {
				System.out.print("Enter a valid number");
				choice = input.nextInt();
			}
			if(choice == 1) 
				energy = fight(input,health,rand);
				
			if(choice == 2)
				System.out.print("\nHiding...\n\n");
		}
		return energy;
	}
	
	
	
public static int fight(Scanner input, int health, Random rand) {
		
		int energy = health;
		int random = rand.nextInt(2);
		
		System.out.print("\nChoose your weapon!\n");
		System.out.print("\n 1) Laser\n");
		System.out.print(" 2) Sword\n");
		System.out.print("\nEnter 1 -2: ");
		
		int decision = input.nextInt();
		
		while(decision < 1 || decision > 2) {
			System.out.print("\nEnter a valid number: ");
			decision = input.nextInt();
		}
		
		if(decision == 1) {
			if(random == 1 || random == 2) {
				System.out.print("\nYou've won the fight!\n");
				System.out.print("Damage: - 10\n");
				energy-=10;
				return energy;
			}
			else {
				System.out.print("\nYou've lost the fight!\n");
				System.out.print("Damage: - 15\n");
				energy-=15;
				return energy;
			}
		}
		else {
			if(random == 1 || random == 2) {
				System.out.print("\nYou've won the fight!\n");
				System.out.print("Damage: - 15\n");
				energy-=15;
				return energy;
			}
			else{
				System.out.print("\nYou've lost the fight!\n");
				System.out.print("Damage: - 30\n");
				energy-=30;
				}
				return energy;
			}
}

}
