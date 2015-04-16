import java.util.LinkedList;

public class Territory {
	private String name;
	private String owner;
	private int supply; // number of supplies
	private int crown; // number of crowns
	private int castle;// castle = 1, stronghold = 2, no castle or stronghold =
						// 0;
	private boolean landOrSea; // true(1) = sea, false(0) = land
	private Territory[] adjTerrs; // name of its adjacent areas
	private LinkedList<Unit> units = new LinkedList<Unit>(); // the units in
																// this
																// territory
	private Order order;
	private boolean neutral = false;

	public Territory() {

	}

	public Territory(String name, int supply, int crown, int castle,
			boolean landOrSea) {
		this.name = name;
		this.supply = supply;
		this.crown = crown;
		this.castle = castle;
		this.landOrSea = landOrSea;
		this.owner = "";
	}

	public String get_owner() {
		return owner;
	}

	public void set_owner(String new_owner) {
		owner = new_owner;
	}

	public int num_adjacents() {
		return adjTerrs.length;
	}

	public void deleteAllUnits() {
		units.clear();
	}

	public String getName() {
		return name;
	}

	public int getSupply() {
		return supply;
	}

	public int getCrown() {
		return crown;
	}

	public int getCastle() {
		return castle;
	}

	public boolean getLandOrSea() {
		return landOrSea;
	}

	public Territory[] getAdj() {
		return adjTerrs;
	}

	// returns a list of territories that can be moved to
	// The territories that can be reached from this territory and don't have an
	// enemy unit in them
	public LinkedList<Territory> getValidMoves() {
		LinkedList<Territory> moves = new LinkedList<Territory>();
		Territory[] helper = getAdjMovable();
		for (Territory t : helper) {
			if (t.isEmpty() || t.get_owner().equals(owner)) {
				moves.add(t);
			}
		}
		return moves;
	}

	// returns the list of adjacent territories that are reachable from this
	// territory
	// the adjacent territoris of the same type + if this is a land, and land
	// reachable through sea this territory's
	// house currently has a ship in
	public Territory[] getAdjMovable() {
		LinkedList<Territory> valid = new LinkedList<Territory>();
		for (Territory t : adjTerrs) {
			if (t.landOrSea == landOrSea)
				valid.add(t);
			else if (t.landOrSea && t.get_owner() == owner) {
				LinkedList<String> startVisited = new LinkedList<String>();
				startVisited.add(name);
				LinkedList<Territory> bySea = adjacentBySea(t, startVisited);
				for (Territory toAdd : bySea) {
					if (!valid.contains(toAdd)) {
						valid.add(toAdd);
					}
				}

			}
		}
		Territory[] answer = new Territory[valid.size() + 1];
		for (int i = 0; i < answer.length - 1; i++) {
			answer[i] = valid.get(i);
		}
		answer[answer.length - 1] = this;
		return answer;
	}

	// given a land territory, finds all other lands that are reachable through
	// the sea
	// keeps track of seas already visited to avoid an infinite loop
	public LinkedList<Territory> adjacentBySea(Territory territory,
			LinkedList<String> visited) {
		LinkedList<Territory> answer = new LinkedList<Territory>();
		for (Territory t : territory.adjTerrs) {
			// System.out.println(t.getName());
			if (!visited.contains(t.getName())) {
				if (!t.landOrSea && t.getName() != name) {
					answer.add(t);
				} else if (t.landOrSea && t.getName() != name
						&& t.get_owner() == owner) {
					visited.add(name);
					LinkedList<Territory> nextVisited = t.adjacentBySea(t,
							visited);

					for (Territory next : nextVisited) {
						answer.add(next);
					}

				}
			}
		}
		return answer;
	}

	public LinkedList<Unit> getUnits() {
		return units;
	}

	public Order getOrder() {
		return order;
	}

	public boolean getNeutral() {
		return neutral;
	}

	public boolean isEmpty() {
		return units.size() == 0;
	}

	public void setName(String n) {
		name = n;
	}

	public void setSupply(int s) {
		supply = s;
	}

	public void setCrown(int c) {
		crown = c;
	}

	public void setCastle(int c) {
		castle = c;
	}

	public void setLandOrSea(boolean info) {
		landOrSea = info;
	}

	public void setAdj(Territory[] adj) {
		adjTerrs = adj;
	}

	public void addUnits(Unit u) {
		units.add(u);
	}

	public void setOrder(Order o) {
		order = o;
	}

	public void setNeutral(boolean n) {
		neutral = n;
	}

	public void deleteOrder() {
		order = null;
	}

	public void deleteUnit(Unit u) {
		units.remove(u);
	}

	public String toString() {
		return String.format("%s, %d, %d, %d, %b, %s, %s\n", name, supply,
				crown, castle, landOrSea, units, order);
	}
}
