import java.util.Random;


		/*
		 * WESTEROS PHASE : (skip game round 1)
		 * Advance game round marker
		 * Draw Westeros Cards
		 * Advance Wildlings track
		 * Resolve Westeros cards
		 */
public class WesterosPhase {
	
	public WesterosPhase(){
		advance();
		westerosCards();
	}
	
	public void advance(){ //advance game round and wilding track
		Tracks.round+=1;
		Tracks.wildlings+=2;
	}
	
	public void westerosCards(){ //draw and resolve
		westerosFirst();
		
	}
	
	public void westerosFirst(){
		Random rand = new Random();
		int i = rand.nextInt(9); //0-9
		if(i<3) supply(); // i = 0, 1, 2
		if(i>=3 && i<6) mustering(); //i = 3,4,5
		if(i>=6&&i<8){
			Random random = new Random();
			int j = random.nextInt(2);
			switch(j){
			case 0:
				supply();
				break;
			case 1:
				mustering();
				break;
			case 2:
				break;
			}
			Tracks.wildlings+=2;
		}
		if(i==8) westerosFirst();
	}
	
	public void supply(){
		
	}
	
	public void mustering(){
		
	}
	
}
