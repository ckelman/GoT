import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;

import jxl.read.biff.BiffException;


public class Game{
	
	public House lannister = new House ("Lannister");
	public House baratheon = new House ("Baratheon");
	public House stark = new House ("Stark");
	public House greyjoy = new House ("Greyjoy");
	public House tyrell = new House ("Tyrell");
	public House martell = new House ("Martell");
	public House[] houses = {baratheon, lannister, stark, martell, greyjoy, tyrell};
	public Hashtable<String, Territory> territories;
	public Hashtable<String, House> house_hash;
	
	public void play() throws BiffException, IOException{
		
		setup();
		
//		printState();
		
//		 for(Territory t: territories.values()){
//			 System.out.println(t.getName() + ": " + t.get_owner());
//		 }
		
		// PLANNING PHASE AND ACTION PHASE
		PlanningPhase pp = new PlanningPhase ();
		ActionPhase ap = new ActionPhase(house_hash);
		for (int i = 0;i<1;i++){
//		while(!isWinner()){

			pp.placeTokens(houses, territories); //Each house place down their order tokens
			ap.resolveMarchOrders(territories);
			lannister.deleteOrders();
			lannister.addOrders();
			baratheon.deleteOrders();
			baratheon.addOrders();
			stark.deleteOrders();
			stark.addOrders();
			greyjoy.deleteOrders();
			greyjoy.addOrders();
			tyrell.deleteOrders();
			tyrell.addOrders();
			martell.deleteOrders();
			martell.addOrders();
		}	
		
		System.out.println("-------------------------------------------------");
		
//		for(Territory t: territories.get("Dragon Stone").getAdjMovable())
//		System.out.println(t.getName()+" : " + t.get_owner());
//		
//		System.out.println(territories.get("Shipbreaker Bay").getLandOrSea());
//		System.out.println(territories.get("Dragon Stone").getLandOrSea());
		
//		System.out.println(territories.get("Shipbreaker Bay").getLandOrSea());
//		
//		for(Territory t: territories.get("Ironman's Bay").getValidMoves())
//			System.out.println(t.getName()+" : " + t.get_owner());
		
//		territories.get("Shipbreaker Bay").setLandOrSea(true);
//		System.out.println(territories.get("Shipbreaker Bay").getLandOrSea());
//		
//		for(Territory t: territories.get("Shipbreaker Bay").getAdjMovable())
//			System.out.println(t.getName()+" : " + t.get_owner());
		
//		for(Territory t: territories.values()){
//			if(t.getLandOrSea())
//			System.out.println(t.getName()+ " : " + t.getLandOrSea());
//		}


		
	}
	
	public void setup() throws BiffException, IOException{
		house_hash = new Hashtable<String, House>();
		for(House h: houses){
			house_hash.put(h.getName(), h);
		}
		
		// GETTING START PHASE
				SetUpPhase setUp = new SetUpPhase();
				// Initialize the map
				territories  = setUp.initializeTerrList(); 
//				System.out.println(territories);
//				System.out.println("average number of adjacents: " + avg_adjacents());
//				System.out.println("total territories: " + total_terrs());
//				
				// Each house put down their starting units
				setUp.initializeUnits(lannister, baratheon, stark, greyjoy, tyrell, martell);  
				// Each house mark down their position on power tracks
				setUp.initializePowerTrack(lannister, baratheon, stark, greyjoy, tyrell, martell); 
				// Each house get 15 order tokens
				setUp.initializeHouseOrders(lannister, baratheon, stark, greyjoy, tyrell, martell);
				// Wildling track on position 0, Round tracks at position 1
				setUp.initializeWildingAndRoundTrack();
				// Initialize House Cards
				setUp.initializeHouseCards(lannister, baratheon, stark, greyjoy, tyrell, martell); 
				//Place down neutral force token for 6 players
				setUp.intiailizeNeutralTokens(6);
				
				territories.get("Shipbreaker Bay").setLandOrSea(true);

	}
	
	public double avg_adjacents(){
		Collection<Territory> ts = territories.values();
		double total = 0;
		for(Territory t: ts)
		{
			total +=t.num_adjacents();
		}
		return total / ts.size();
	}
	
	public int total_terrs(){
		Collection<Territory> ts = territories.values();

		return ts.size();
	}
	
	public boolean isWinner(){
		for(House house: houses){
			int count = 0;
			
			for(String Tname: house.getTerrs()){
				Territory T = territories.get(Tname);
				if(T.getCastle()==1 || T.getCastle() == 2){ //castle = 1, stronghold = 2, no castle or stronghold = 0;
					 count+=1;
				}
			}
			
			if (count >= 7)
				return true;
		}
		
		return false;
	}
	
	public void printState(){
		for (House h :houses){
			System.out.println("------------------------------------------------");
			System.out.println(h.getName());
			System.out.println(".................................................");
			
			for (String s: h.getTerrs()){
				Territory t = territories.get(s);
				System.out.print(s + "| ");
				
				int footmen = 0;
				int knights = 0;
				int ships = 0;
				
				for (Unit u : t.getUnits()){
					if (u.getName() == "FOOTMAN"){
						footmen++;
					}
					else if (u.getName() == "KNIGHT"){
						knights++;
					}
					else if (u.getName() == "SHIP"){
						ships++;
					}
				}
				System.out.println("Footmen: "+ footmen+ " | Knights: " + knights + " | Ships: " + ships );
			}
			
			System.out.println("------------------------------------------------");
			
		}
	}
	
	public String getWinner(){
		int castleTerr = 0;
		int max = 0;
		int count = 1;
		String houseName = "";
		for (int i = 0; i<6 ; i++){
			castleTerr = 0;
			House house = new House();
			switch (i){
			case 0: 
				house = lannister;
				break;
			case 1:
				house = baratheon;
				break;
			case 2:
				house = stark;
				break;
			case 3:
				house = greyjoy;
				break;
			case 4:
				house = tyrell;
				break;
			case 5:
				house = martell;
				break;
			}
			for(String Tname: house.getTerrs()){
				Territory T = territories.get(Tname);
				if(T.getCastle()==1 || T.getCastle() == 2){ //castle = 1, stronghold = 2, no castle or stronghold = 0;
					 castleTerr+=1;
				}
			}
			if(castleTerr> max){
				max = castleTerr;
				houseName = house.getName();
				count = 1;
			}
			else if(castleTerr == max){
				count ++;
			}
			
//			if (castleTerr> max){
//				for (String h : house.getTerrs())
//				System.out.println(h);
//			}
			
		}
		if(max == 1){
			printState();
		}
		System.out.println(max);
		
		if(count > 1)
			return "";
		
		return houseName;
	}
	
}
