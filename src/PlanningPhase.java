import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

public class PlanningPhase {
	/*
	 * PLANNING PHASE : Each house place down their order tokens
	 */

	public void placeTokens(House[] houses, Hashtable<String, Territory> map) {
		// completely at random for now (ignore the power track)
		LinkedList<Territory> terrLL = new LinkedList<Territory>();

		// goes through all the houses
		for (House house : houses) {
			// then choose the three random territories to place the movement
			// orders in
			terrLL = house.threeRandomOccupied(map);

			// places a random order in each of the territories (but because the
			// houses only have movement orders
			// guaranteed to be a movement
			for (Territory t : terrLL) {
				String order = getRandomOrder(house);
				Order o = house.getOrder(order);
				t.setOrder(o); // place token down to the map
				house.getOrders().remove(o); // remove this token from the
												// player's collections
			}
		}
	}

	// only dealing with march orders
	public String getRandomOrder(House h) {
		// no special order for now
		Random rand = new Random();
		int i = rand.nextInt(2);
		switch (i) {
		case 0:
			if (h.containsOrder("marchn"))
				return "marchn";
			break;
		case 1:
			if (h.containsOrder("march0"))
				return "march0";
			break;
		}
		return getRandomOrder(h);
	}
}
