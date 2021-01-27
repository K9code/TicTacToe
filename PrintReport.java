package defaultPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class PrintReport extends TicTacToe{

	public static void main(String[] args) {
		
		printReport();
		
	}
		
	private static void printReport() {
		
		File file = new File("TicTacToeReport.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException fehler) {
				fehler.printStackTrace();
			}
		}
				
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.println("TicTacToe Report:");
			pw.flush();
			pw.close();
		}catch (FileNotFoundException fehler) {
			fehler.printStackTrace();
		}
	}
	
	
}
