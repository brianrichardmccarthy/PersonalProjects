package textFileCleanUp;
/**
 * @author Brian McCarthy
 * @verison 1.0
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TextFileCleanUp {

	private ArrayList<String> text = new ArrayList<String>();
	private int num = 1;
	private int count = 0;
	private String fileName = "text";
	private int numOfFiles = 11;

	/**
	 * Constructor for TextFileCleanUp class.<br>
	 * Sets the variables fileName and numOfFiles.<br>
	 * Then calls the openFile and writeFile() methods.
	 * @param fileName (Type: String) base name of the text files e.g text1.txt base name = text
	 * @param numOfFiles (Type: int) number of text files e.g text1.txt, text2.txt numOfFiles = 2
	 */
	public TextFileCleanUp(String fileName, int numOfFiles) {
		this.fileName = fileName;
		this.numOfFiles = numOfFiles;
		openFile();
		writeFile();
	}

	/**
	 * For each text files in the input folder.<br>
	 * This method reads in each file.<br>
	 * Checks each line of the text to each value in the array lists,<br>
	 * To remove any duplicates lines.<br>
	 * 
	 */
	public void openFile() {
		// numOfFiles = 5 num will go from 1 - 5 inclusive
		while (num <= numOfFiles) {
			// store the file in a object
			// src\\textFileCleanUp\\Input\\text1.txt
			File file = new File("src\\textFileCleanUp\\Input\\" + fileName + num + ".txt");
			
			// try with resources opens the file, to process it line by line in the while loop
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				
				// used for each line in current text file
				String line;
				
				// loops through each line in text file
				while ((line = reader.readLine()) != null) {
					
					// variable to check if the current value in line has been entered into the array list
					boolean found = false;
					
					// count keeps track of how many values have been entered into the array list already
					// loops thought all the values in the array list
					for (int x = 0; x < count; x++) {
						
						/*
						 * if the the current position of text is equal to the line
						 * e.g if text.get(0) = one and line = one
						 * set found to true
						 * and skip the reset of the strings in the array list
						 * because there should only be one of each string
						 * (done by setting the loop control variable to a value greater than count)
						 * */
						if (text.get(x).equals(line)) {
							found = true;
							x = count * 2;
						}
					}
					
					// if the current value in line was not found
					// increment count by one and add the current value of line to the array list
					if (!found) {
						count++;
						text.add(line);
					}
				}
				
				// increment num when all lines of the text file have been read and one copy of each has been added to the array list
				// in order to move onto the next text file or to exit the while loop
				num++;
			} catch (Exception e) {
				// if one file fails the skip checking the rest
				// two main exceptions are file not found and IOException
				num = numOfFiles * 2;
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Writes the array list of strings to a text file
	 */
	public void writeFile() {
		
		// creates a new File Object in the output folder
		File file = new File("src\\textFileCleanUp\\Output\\text.txt");
		
		// try with resources to open BufferedWriter Object using file writer object and the file
		try (BufferedWriter write = new BufferedWriter(new FileWriter(file))) {
			
			// loop control variable
			int x = 0;
			
			// while x is less than array list size
			while (x < count) {
				
				// write each String in the array list to the text file
				write.write(text.get(x));
				
				// add a new line to the text file
				// Basically the "\n" equivalent
				write.newLine();
				
				// increment the loop control variable
				x++;
			}
		} catch (IOException e) {
			// catch any problem writing the file
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		
		// new Scanner object to get the base name and the number of text files
		Scanner input = new Scanner(System.in);
		System.out.println("Put the Files into input folder and for each text file name them the same name and increment by one e.g text1.txt text2.txt etc");
		System.out.println("When you've finished this, please enter just the name e,g text1.txt text2.txt enter text");
		
		// base name for text files
		String name = input.nextLine();
		System.out.println("How many files are there? e.g text1.txt, text2.txt enter 2");
		int num;
		try {
			
			// number of text files
			num = input.nextInt();
			
			// run one new instance of TextFileCleanUp
			// because the constructor class calls the other method to perform the task,
			// the new instance doesn't need to be assigned to a variable
			// once it's done the writeFile method the object is destroyed and memory released
			new TextFileCleanUp(name, num);
			System.out.println("Done");
		} catch (InputMismatchException e) {
			// catching anything other than a number entered
			System.out.println("There was a problem with the number you entered.");
		} finally {
			// close the scanner instance
			input.close();			
		}
	}

} 