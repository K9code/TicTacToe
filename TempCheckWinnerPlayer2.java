package defaultPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempCheckWinnerPlayer2 extends TicTacToe{

	public static void tempCheckWinnerPlayer1(int cpuPos) {
			
			List topRow = Arrays.asList(1, 2, 3);
			List midRow = Arrays.asList(4, 5, 6);
			List botRow = Arrays.asList(7, 8, 9);
			
			List leftCol = Arrays.asList(1, 4, 7);
			List midCol = Arrays.asList(2, 5, 8);
			List rightCol = Arrays.asList(3, 6, 9);
	
			List cross1 = Arrays.asList(1, 5, 9);
			List cross2 = Arrays.asList(3, 5, 7);
			
			List<List> winning= new ArrayList<List>();
			winning.add(topRow);
			winning.add(midRow);
			winning.add(botRow);
			winning.add(leftCol);
			winning.add(midCol);
			winning.add(rightCol);
			winning.add(cross1);
			winning.add(cross2);
			
			TicTacToe.playerPositions.add(cpuPos);
			
			for (List l : winning) {
				if (TicTacToe.playerPositions.containsAll(l)) {
					TicTacToe.winningPosPlayer = cpuPos;
					break;
				}
			}
			int lastInd = TicTacToe.playerPositions.lastIndexOf(cpuPos);
			TicTacToe.playerPositions.remove(lastInd);
			
	}
	
}
