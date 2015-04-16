
public class Order {
	private String name;
	private int combatStrength;
	
	public String getName(){
		return name;
	}
	public Order(){
		
	}
	public String toString(){
		return name;
	}
	
	public Order(ORDERS order) {
		switch (order) {
		case RAID: 
			name = "raid";
			combatStrength = 0; //need modification
			break;
		case MARCH0: 
			name = "march0";
			combatStrength = 0; 
			break;
		case MARCHN: 
			name = "marchn";
			combatStrength = -1; 
			break;
		case DEFENSE: 
			name = "defense";
			combatStrength = 1; 
			break;
		case SUPPORT: 
			name = "support";
			combatStrength = 0; 
			break;
		case CONSOLIDATE: 
			name = "consolidate";
			combatStrength = 0; //need modification
			break;
		case sRAID: 
			name = "sraid";
			combatStrength = 0; //need modification
			break;
		case sMARCH: 
			name = "smarch";
			combatStrength = 1; 
			break;
		case sDEFENSE: 
			name = "sdefense";
			combatStrength = 2; 
			break;
		case sSUPPORT:
			name = "ssupport";
			combatStrength = 1; 
			break;
		case sCONSOLIDATE:
			name = "sconsolidate";
			combatStrength = 0; //need modification
			break;
		}
	}
	
	public enum ORDERS {
		RAID,
		MARCH0,
		MARCHN,
		DEFENSE,
		SUPPORT,
		CONSOLIDATE,
		sRAID,
		sMARCH,
		sDEFENSE,
		sSUPPORT,
		sCONSOLIDATE,
	}
}
