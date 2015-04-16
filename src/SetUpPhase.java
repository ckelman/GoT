import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SetUpPhase {
	
	public Hashtable<String, Territory> territories  = new Hashtable<String, Territory> ();
	
	public SetUpPhase() throws BiffException, IOException{
		
	}
	
	// Initial territory list, get the name, supply, number of crowns, number of castles, if land or sea and the adjacent territories of one territory
	public Hashtable<String, Territory> initializeTerrList( ) throws BiffException, IOException {
		Workbook workbook = Workbook.getWorkbook(new File("GOTMAPS.xls"));
		Sheet sheet = workbook.getSheet(1);
		int row = sheet.getRows()-1; 
		
		for (int i=1; i<=row; i++){
			Cell col0 = sheet.getCell(0, i); // name 
			Cell col1= sheet.getCell(1, i); // supply
			Cell col2 = sheet.getCell(2, i); // crown 
			Cell col3 = sheet.getCell(3, i); // castle
			Cell col4 = sheet.getCell(4, i); // landOrSea	
			
			String name = col0.getContents();
			int supply = Integer.parseInt(col1.getContents());
			int crown = Integer.parseInt(col2.getContents());;
			int castle = Integer.parseInt(col3.getContents());
			boolean landOrSea = false; 
			if (Integer.parseInt(col4.getContents())==1){ //sea :true
				landOrSea = true;
			}
			Territory territory = new Territory(name, supply, crown, castle, landOrSea);
			territories.put(name, territory);
		}
		
		for (int i=1; i<=row; i++){
			Cell col0 = sheet.getCell(0, i); // name 
			Cell col5 = sheet.getCell(5, i); //the string name of adjacent areas 
			
			String name = col0.getContents();
			Territory T = territories.get(name); // current territory
			
			String adjNames[] = col5.getContents().split(", ");
			Territory[] adjT = new Territory [adjNames.length];
			// Get the adjacent territories of the current territory this march order is on
			for(int j = 0; j<adjNames.length; j++){
				adjT[j] = territories.get(adjNames[j]);
			}
			T.setAdj(adjT);
		}
		workbook.close();
		return territories;
	}

	// Each house put down their starting units 
	public void initializeUnits(House lannister, House baratheon, House stark, House greyjoy, House tyrell, House martell) throws BiffException, IOException{
		Workbook workbook = Workbook.getWorkbook(new File("GOTMAPS.xls"));
		Sheet sheet = workbook.getSheet(0); //starting units
		
		for (int j = 1; j<=6; j++){ //column : house name
			for (int i=1; i<=3; i++){ //row: territories
				Cell col1 = sheet.getCell(j, i); 
				String terrs[] = col1.getContents().split(", "); 
				for (String t: terrs){
					Territory occupied = territories.get(t);
					
					switch (i) {
					case 1: //footman
						Unit footman = new Unit (Unit.UNITS.FOOTMAN);
						occupied.addUnits(footman);
						break;
					case 2: //knight
						Unit knight = new Unit (Unit.UNITS.KNIGHT);
						occupied.addUnits(knight);
						break;
					case 3: //ship
						Unit ship = new Unit (Unit.UNITS.SHIP);
						occupied.addUnits(ship);
						break;
					}
					
					switch(j) {
					case 1: 
						if(!lannister.getTerrs().contains(occupied.getName())) {
							lannister.getTerrs().add(occupied.getName());
							occupied.set_owner("Lannister");
						}
						
						break;
					case 2:
						if(!baratheon.getTerrs().contains(occupied.getName())){
							baratheon.getTerrs().add(occupied.getName());
							occupied.set_owner("Baratheon");
						}
						break;
					case 3: 
						if(!stark.getTerrs().contains(occupied.getName())){
							stark.getTerrs().add(occupied.getName());
							occupied.set_owner("Stark");
						}
						break;
					case 4: 
						if(!greyjoy.getTerrs().contains(occupied.getName())){
							greyjoy.getTerrs().add(occupied.getName());
							occupied.set_owner("Greyjoy");
						}
						break;
					case 5: 
						if(!tyrell.getTerrs().contains(occupied.getName())){
							tyrell.getTerrs().add(occupied.getName());
							occupied.set_owner("Tyrell");
						}
						break;
					case 6:
						if(!martell.getTerrs().contains(occupied.getName())){
							martell.getTerrs().add(occupied.getName());
							occupied.set_owner("Martell");
						}
						break;
					}
				}	
			}
		}
	}
	
	// For sanity check, ignore it
	public void printTerritories(){
		System.out.println(territories);
	}

	// Each house gets their order tokens
	public void initializeHouseOrders(House lannister, House baratheon, House stark, House greyjoy, House tyrell, House martell){
		lannister.addOrders();
		baratheon.addOrders();
		stark.addOrders();
		greyjoy.addOrders();
		tyrell.addOrders();
		martell.addOrders();
	}
	
	// Initialize Power Tracks
	public void initializePowerTrack(House lannister, House baratheon, House stark, House greyjoy, House tyrell, House martell) throws BiffException, IOException{
		Workbook workbook = Workbook.getWorkbook(new File("GOTMAPS.xls"));
		Sheet sheet = workbook.getSheet(2); //power tracks
		
		for (int j=0; j<5; j++){ //column : trackname
			for (int i=1; i<=6; i++){ //row: positions
				Cell col = sheet.getCell(j, i); 
				House house = new House();
				if (col.getContents().equals("Lannister")) house = lannister;
				if (col.getContents().equals("Baratheon")) house = baratheon;
				if (col.getContents().equals("Stark")) house = stark;
				if (col.getContents().equals("Greyjoy")) house = greyjoy;
				if (col.getContents().equals("Tyrell")) house = tyrell;
				if (col.getContents().equals("Martell")) house = martell;
				
				switch (j) {
				case 0: // Ironthrone
					Tracks.ironThrone[i-1] = house;
					break;
				case 1: // fiefdom track
					Tracks.fiefdoms[i-1] = house;
					break;
				case 2: //King's court
					Tracks.kingsCourt[i-1] = house;
					break;
				case 3: // Supply
					if (i<3) {
						String supplies[] = col.getContents().split(", ");
						for (String s: supplies){
							Tracks.supply[i-1] = new LinkedList<String>();;
							Tracks.supply[i-1].add(s);
						}
					}
					break;
				case 4: // Victory
					if (i<3) {
						String victories[] = col.getContents().split(", ");
						for (String v: victories){
							Tracks.victory[i-1] = new LinkedList<String>();;
							Tracks.victory[i-1].add(v);
						}
					}
					break;
				}
			}
		}
	}
	
	// Wildling track on position 0
	// Round track on position 1
	public void initializeWildingAndRoundTrack(){
		Tracks.wildlings = 0;
		Tracks.round = 1;
	}
	
	// Initialize House Cards
	public void initializeHouseCards(House lannister, House baratheon, House stark, House greyjoy, House tyrell, House martell){
		int n = 0;
		for(int i = 0; i<7;i++){
			switch (i){
			case 0:
				n = 0;
				break;
			case 1:
				n = 1;
				break;
			case 2:
				n = 1;
				break;
			case 3:
				n = 2;
				break;
			case 4:
				n = 2;
				break;
			case 5:
				n = 3;
				break;
			case 6:
				n = 4;
				break;
			}
			lannister.getHouseCards().add(n);
			baratheon.getHouseCards().add(n);
			stark.getHouseCards().add(n);
			greyjoy.getHouseCards().add(n);
			tyrell.getHouseCards().add(n);
			martell.getHouseCards().add(n);
		}
	}
	
	public void intiailizeNeutralTokens(int player) throws BiffException, IOException{ // depends on how many player are there
		Workbook workbook = Workbook.getWorkbook(new File("GOTMAPS.xls"));
		Sheet sheet = workbook.getSheet(3);
		int column = player - 3; 
		int numOfTerr = sheet.getColumn(column).length - 1;
		for (int i=1; i<=numOfTerr; i++){
			Cell col0 = sheet.getCell(column, i); // 6
			String name = col0.getContents();
			Territory T = territories.get(name);
			T.setNeutral(true);
		}
	}
}








