package defaultPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;


public class TicTacToe {
	
	//Global User List declaration
	static ArrayList<Integer> playerPositions = new ArrayList<Integer>(); 
	static ArrayList<Integer> cpuPositions = new ArrayList<Integer>();
	static List<Integer> freePosList = new ArrayList<Integer>(); 
	static List<Integer> freePosListPlayer = new ArrayList<Integer>(); 
	static int winningPos = 0;
	static int winningPosPlayer = 0;

// ------------------------Main------------------------------	
	
	public static void main(String[] args) {
		
		char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
				{'-', '+', '-', '+', '-'},
				{' ', '|', ' ', '|', ' '},
				{'-', '+', '-', '+', '-'},
				{' ', '|', ' ', '|', ' '}};
		
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		
		System.out.println("Enter your placement (1-9):");
		boolean a = true;
		while (a == true) { 
				
			System.out.println("----Player-----\nPlayer: ");
			int playerPos = scan.nextInt();
			
			//checking for free positions for player
			while (playerPositions.contains(playerPos) || cpuPositions.contains(playerPos)) {
				System.out.println("you cannot play that position, please try again:");
				playerPos = scan.nextInt();
			}
			
			placePiece(gameBoard, playerPos, "player");
			printGameBoard(gameBoard);
			
			String resoult = checkWinner();
			System.out.println(resoult);
			
// ------------------------CPU-------------------------------
			
			int nextPos = 0;
			int cpuPos = rand.nextInt(9)+1;
			int l = 0;
	
				if (9 - (playerPositions.size() + cpuPositions.size()) == 8) {
					while (checkPosBol(cpuPos)){
						cpuPos = rand.nextInt(9)+1;
					}
				}else {
					buildAvailablePosList();
					
					for (int b : freePosList) {
						l++;
						if (l > (9 - (playerPositions.size() + cpuPositions.size()))) {
							break;
						}
							
						if ((winningPos == 0)) {
							if ((winningPosPlayer == 0)) {
								nextPos = b;
								cpuPos = b;
								tempCheckWinner(cpuPos);
								tempCheckWinnerPlayer1(cpuPos);
							}else {
								cpuPos = winningPosPlayer;
								break;
							}
						}else {
							cpuPos = winningPos;
							break;
						}
					}	
					if (winningPos == 0) {
						
						for (int f = 0; f< (9 - (playerPositions.size() + cpuPositions.size())); f++) {
							
							if (winningPosPlayer != 0)	{
								break;
							}
							
							buildAvailablePosList();
							
							int loopCounter = 0;
							for (int b : freePosList) {
							
								if ((winningPosPlayer == 0)) {
									cpuPos = b;
									tempCheckWinnerPlayer(cpuPos);
								}else {
									cpuPos = winningPosPlayer;
									break;
								}
							}
						}		
					}
				}	
				System.out.println("------CPU------\nCPU: " + cpuPos);			
				placePiece(gameBoard, cpuPos, "cpu");
				
				printGameBoard(gameBoard);
				
				resoult = checkWinner();
				System.out.println(resoult);
			}
	}

// ----------------------end of main--------------------------

// --------------------print Bord Function--------------------
	
	public static void printGameBoard(char[][] gameBoard) {
		for(char [] row : gameBoard) {
			for(char c: row) {
				System.out.print(c);
			}
			System.out.println();
		}

	}

// ---------------------place Piece Function-------------------	

	public static void placePiece(char[][] gameBoard, int pos, String user) {
		
		//build chosen fields List
		
		char symbol= ' ';
		if (user.equals("player")) {
			symbol = 'X';
			
			//add to player List
			playerPositions.add(pos);
			
		} else if (user.equals("cpu")) {
			symbol = 'O';
			
			//add to cpu List
			cpuPositions.add(pos);
			
		}
		
		switch(pos) {
			case 1:
				gameBoard[0][0] = symbol;
				break;
			case 2:
				gameBoard[0][2] = symbol;
				break;
			case 3:
				gameBoard[0][4] = symbol;
				break;
			case 4:
				gameBoard[2][0] = symbol;
				break;
			case 5:
				gameBoard[2][2] = symbol;
				break;
			case 6:
				gameBoard[2][4] = symbol;
				break;
			case 7:
				gameBoard[4][0] = symbol;
				break;
			case 8:
				gameBoard[4][2] = symbol;
				break;
			case 9:
				gameBoard[4][4] = symbol;
				break;
		}
		
	}
	
	public static String checkWinner() {
		
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
		
		for (List l : winning) {
			if (playerPositions.containsAll(l)) {
				System.out.println("Winner, Winner Chicken Dinner");
				Runtime.getRuntime().exit(0);
				return "";
				
			}else if (cpuPositions.containsAll(l)) {
				System.out.println("CPU wins! Sorry :(");
				Runtime.getRuntime().exit(0);
				return "";
				
			}else if(playerPositions.size() + cpuPositions.size() == 9) {
				
				if (winningPosPlayer == 0) {
					
					System.out.println("No Winner!");
					
	//				System.exit(0); --> funktioniert nicht
					
					Runtime.getRuntime().exit(0);
	
					return "";
				}
			}
		}
		return"";
	}
	
	public static void buildAvailablePosList() {
		
		Random rand = new Random();
		int tempcpuPos = rand.nextInt(9) + 1;
		List<Integer> freePos = new ArrayList<Integer>();
		int it = 9 - (playerPositions.size() + cpuPositions.size()); 
		
		for (int i = 0; i < it; i++) {
			while (freePos.contains(tempcpuPos) || checkPosBol(tempcpuPos)){
					tempcpuPos = rand.nextInt(9) + 1;
				}

			freePos.add(tempcpuPos);
			tempcpuPos = rand.nextInt(9) + 1;
				
		}
		
		freePosList = freePos;
		
	}
	
	public static boolean checkPosBol(int cpuPos) {
		if (playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos)) {
			return true;
		}
		return false;
	}
	
	public static void tempCheckWinner(int cpuPos) {
		
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
		
		cpuPositions.add(cpuPos);
		
		for (List l : winning) {
			if (cpuPositions.containsAll(l)) {
				winningPos = cpuPos;
				break;
			}
		}
		int lastInd = cpuPositions.lastIndexOf(cpuPos);
		cpuPositions.remove(lastInd);
		
	}
	
	public static void tempCheckWinnerPlayer(int cpuPos) {
		
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
		
		playerPositions.add(cpuPos);
		
		for (List l : winning) {
			if (playerPositions.containsAll(l)) {
				winningPosPlayer = cpuPos;
				break;
			}
		}
		int lastInd = playerPositions.lastIndexOf(cpuPos);
		playerPositions.remove(lastInd);
		
	}
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
		
		playerPositions.add(cpuPos);
		
		for (List l : winning) {
			if (playerPositions.containsAll(l)) {
				winningPosPlayer = cpuPos;
				break;
			}
		}
		int lastInd = playerPositions.lastIndexOf(cpuPos);
		playerPositions.remove(lastInd);
		
	}
	
}
