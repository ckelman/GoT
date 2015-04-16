import java.util.Hashtable;
import java.util.LinkedList;

public class House {
	private String name;
	private LinkedList<String> Terrs;
	private LinkedList<Order> Orders;
	private int powerToken; // how much money that the current house has
	private LinkedList<Integer> Housecards; // combat strength

	public String toString() {
		return Orders.toString();

	}

	public House() {
		Terrs = new LinkedList<String>();
		Orders = new LinkedList<Order>();
		powerToken = 5;
		Housecards = new LinkedList<Integer>();

	}

	public House(String housename) {
		name = housename;
		Terrs = new LinkedList<String>();
		Orders = new LinkedList<Order>();
		powerToken = 5;
		Housecards = new LinkedList<Integer>();
	}

	public House(String n, LinkedList<String> territories,
			LinkedList<Order> ords, int power, LinkedList<Integer> cards) {
		name = n;
		Terrs = territories;
		Orders = ords;
		powerToken = power;
		Housecards = cards;

	}

	public House clone() {
		House house = new House(name, Terrs, Orders, powerToken, Housecards);
		return house;
	}

	public String getName() {
		return name;
	}

	public void deleteOrders() {
		Orders.clear();
	}

	public void addOrders() {
		Order raid = new Order(Order.ORDERS.RAID);
		Order march0 = new Order(Order.ORDERS.MARCH0);
		Order march1 = new Order(Order.ORDERS.MARCH0);
		Order marchn = new Order(Order.ORDERS.MARCHN);
		Order defense = new Order(Order.ORDERS.DEFENSE);
		Order support = new Order(Order.ORDERS.SUPPORT);
		Order consolidate = new Order(Order.ORDERS.CONSOLIDATE);
		Order sraid = new Order(Order.ORDERS.sRAID);
		Order smarch = new Order(Order.ORDERS.sMARCH);
		Order sdefense = new Order(Order.ORDERS.sDEFENSE);
		Order ssupport = new Order(Order.ORDERS.sSUPPORT);
		Order sconsolidate = new Order(Order.ORDERS.sCONSOLIDATE);
		// for (int i = 0;i<2; i++){
		// Orders.add(raid);
		// Orders.add(defense);
		// Orders.add(support);
		// Orders.add(consolidate);
		// }
		Orders.add(march0);
		Orders.add(march1);
		Orders.add(marchn);

		// Orders.add(sraid);
		// Orders.add(smarch);
		// Orders.add(ssupport);
		// Orders.add(sdefense);
		// Orders.add(sconsolidate);
	}

	public boolean containsOrder(String o) {
		for (Order order : Orders) {
			if (order.getName().equals(o)) {
				return true;
			}
		}
		return false;
	}

	public void setPowertoken(int p) {
		powerToken = p;
	}

	public LinkedList<Order> getOrders() {
		return Orders;
	}

	public Order getOrder(String o) {
		for (Order order : Orders) {
			if (order.getName().equals(o)) {
				return order;
			}
		}
		return null;
	}

	// returns all territories that is currently occupied by at least one unit
	// of this house
	public LinkedList<Territory> getOccupiedTerrs(
			Hashtable<String, Territory> map) {
		LinkedList<Territory> occupied = new LinkedList<Territory>();
		for (String t : Terrs) {
			Territory ter = map.get(t);
			if (!ter.isEmpty()) {
				occupied.add(ter);
			}
		}
		return occupied;
	}

	// returns three random territories that currently has a unit in them
	// (because three movement orders)
	// if the house has less than three territories with a unit in them, returns
	// all the house's occupied territories
	public LinkedList<Territory> threeRandomOccupied(
			Hashtable<String, Territory> map) {
		LinkedList<Territory> occupied = getOccupiedTerrs(map);
		LinkedList<Territory> threeRandom = new LinkedList<Territory>();

		for (int i = 0; i < 3 && occupied.size() > 0; i++) {
			int rand = (int) (Math.random() * occupied.size());
			threeRandom.add(occupied.remove(rand));
		}
		return threeRandom;
	}

	public LinkedList<String> getTerrs() {
		return Terrs;
	}

	public void addTerr(String s) {
		if (!Terrs.contains(s))
			Terrs.add(s);
	}

	public void removeTerr(String s) {
		Terrs.remove(s);
	}

	public LinkedList<Integer> getHouseCards() {
		return Housecards;
	}

	public int getPowertoken() {
		return powerToken;
	}

	public void printHouseTerrs() {
		System.out.println(name);
		for (String t : Terrs) {
			System.out.println(t);
		}
	}
}
