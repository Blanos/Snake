package com.mygdx.game.access;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Base64;

import com.mygdx.game.GameWorld;

public class InputFile {

	//private boolean add_at_end = false;
	
	//private GameWorld myWorld;
	
	public static void main(String[] args) {

		InputFile inputFile = new InputFile();
		
		String content = inputFile.readFile("data.txt");
		
		String[] records = content.split("\n");
		
		System.out.println(records.length);
		
		String newCont = "";
		for(int i = 0; i < records.length; i++){
			newCont = newCont+"dsdf" + records[i] +"\n";
		}
		inputFile.writeFile("output.txt", newCont);
		
	}

	public String readFile(String name) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(name));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				//sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			br.close();

			return everything;

		} catch (Exception e) {
			System.out.println("Uuuupsss, Error: " + e);
		}

		return null;

	}

	public void writeFile(String fileName, String content) {

		//FileWriter writer = new FileWriter(fileName, true);
		PrintWriter writer = null;
		
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (Exception e) {

			System.out.println("Upppsss, Error: " + e);
		}
		//for(int i = 0; i < myWorld.getHighscores().length; i++)
		//{
			writer.println(content);
		//}
		//writer.newLine();
		
		writer.close();

	}
	
	
}
