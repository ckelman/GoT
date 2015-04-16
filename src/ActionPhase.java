import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

import jxl.read.biff.BiffException;

public class ActionPhase {

	public Hashtable<String, House> action_house_hash;

	public ActionPhase(Hashtable<String, House> h) {
		action_house_hash = h;
	}

	/*
	 * ACTION PHASE : Each house reveal and resolve order tokens Assume we only
	 * have march order now
	 */

	public void resolveMarchOrders(Hashtable<String, Territory> map)
			throws BiffException, IOException {

		// iterate through the houses in the turn order once for now, should be
		// 3 times
		for (int i = 0; i < Tracks.ironThrone.length; i++) {

			// System.out.println("hi");

			// get the current house and the keys for its territories to look
			// them up in the hashtable
			House house = Tracks.ironThrone[i];
			LinkedList<String> terrs = house.getTerrs();
			// LinkedList<Territory> conquerredTerrs = new LinkedList<Territory>
			// ();
			for (int j = 0; j < terrs.size(); j++) {

				String Tname = terrs.get(j);

				// System.out.println("hey");

				// get the actual territory given the name from the map
				Territory T = map.get(Tname);
				String orderName = "";

				if (T.getOrder() != null) {
					orderName = T.getOrder().getName();
				}

				// makes sure the order is actually a march order
				if (orderName.equals("march0") || orderName.equals("marchn")) {
					// get the adjacent territories that the current units are
					// moving into

					// get the list of valid places to move from the selected
					// territory
					LinkedList<Territory> adjT = T.getValidMoves();

					LinkedList<Unit> unitList = T.getUnits();

					// iterate through each unit in the territory
					for (int k = 0; k < unitList.size(); k++) {
						// System.out.println("yo");

						Unit u = unitList.get(k);
						
						// pick a territory from the valid moves list to move
						// the current piece to
						// can be same territory as current territory
						int random = (int) (Math.random() * adjT.size());

						// if the unit is moving to a territory that is not this
						// one, we need to delete it from the current
						// territory and add it to the newly selected territory
						// if we're moving to this territory we dont need to do
						// anything to this unit
						if (adjT.get(random).getName() != T.getName()) {

							Unit temp = u.clone();
							T.deleteUnit(u);
							// because we are deleting an element from the
							// unitlist, we need to not increment k
							k--;

							// add the cloned unit to the new territory
							adjT.get(random).addUnits(temp);

							// if the territory being moved to was owned by
							// another house (but empty) we must remove it
							// from the list of owned territoried of the house
							// that used to own it
							if (adjT.get(random).get_owner() != "")
								action_house_hash.get(
										adjT.get(random).get_owner())
										.removeTerr(T.getName());

							// then add the territory to the house moving into
							// it (the method handles duplicates)
							action_house_hash.get(house.getName()).addTerr(
									T.getName());

							// set the owner of the territory to the new owner
							adjT.get(random).set_owner(T.get_owner());

						}

					}

					T.deleteOrder();
				}
			}

		}
	}

	public int randomInt(int max) {
		Random rand = new Random();
		int i = rand.nextInt(max + 1); // return [0 - max]
		// random.nextInt(max - min + 1) + min === > return [min, max]
		return i;
	}

	// randomly choose several adjacent territories that the current units are
	// moving into
	// public LinkedList<Territory> getConquerredTerritory(House house,
	// Territory terr, Order order) throws BiffException, IOException{
	// LinkedList<Territory> conquerred = new LinkedList<Territory> ();
	// // Territory[] adjT = terr.getAdj();
	// Territory[] adjT = terr.getAdjMovable();
	//
	//
	// LinkedList<Unit> unitList = terr.getUnits();
	// LinkedList<Unit> unitToDelete = new LinkedList<Unit> ();
	// int random = (int)(Math.random()*adjT.length);
	//
	// for(Unit unit: unitList){
	// if ((adjT[random].getUnits().isEmpty() ||
	// adjT[random].get_owner().equals(house.getName())) &&
	// adjT[random].getNeutral()==false){
	//
	// if(adjT[random].get_owner() != house.getName()){
	//
	// //remove territory from current owner's territories
	// if(adjT[random].get_owner() != ""){
	//
	// House oldhouse = action_house_hash.get(adjT[random].get_owner());
	// oldhouse.getTerrs().remove(adjT[random].getName());
	// }
	//
	// //set the owner of the territory to be the new owner
	// adjT[random].set_owner(house.getName());
	// }
	//
	// //move in the new unit
	// adjT[random].addUnits(unit.clone());
	// conquerred.add(adjT[random]);
	// unitToDelete.add(unit);
	// }
	// }
	// if(unitToDelete.size() > 0){
	// for(Unit unit: unitToDelete){
	// terr.deleteUnit(unit);
	// }
	// }
	// return conquerred;
	// }
}
