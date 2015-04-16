import java.io.IOException;

import jxl.read.biff.BiffException;

public class Driver {

	public static void main(String[] args) throws BiffException, IOException {
		int lannister = 0;
		int baratheon = 0;
		int stark = 0;
		int greyjoy = 0;
		int tyrell = 0;
		int martell = 0;
		int tie = 0;
		for (int i = 0; i < 1; i++) {
			Game game = new Game();
			game.play();
			String name = game.getWinner();
			game.printState();
			if (name.equals("Lannister"))
				lannister += 1;
			if (name.equals("Baratheon"))
				baratheon += 1;
			if (name.equals("Stark"))
				stark += 1;
			if (name.equals("Greyjoy"))
				greyjoy += 1;
			if (name.equals("Tyrell"))
				tyrell += 1;
			if (name.equals("Martell"))
				martell += 1;
			if (name.equals(""))
				tie += 1;
		}
		System.out.printf("%d, %d, %d, %d, %d ,%d, %d", lannister, baratheon,
				stark, greyjoy, tyrell, martell, tie);
	}
}
