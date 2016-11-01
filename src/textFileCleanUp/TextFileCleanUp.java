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
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextFileCleanUp {

	private List<String> text = new ArrayList<String>();
	private int num = 1;
	private String fileName = "text";
	private int numOfFiles = 11;

	/**
	 * Constructor for TextFileCleanUp class.<br>
	 * Sets the variables fileName and numOfFiles.<br>
	 * Then calls the openFile and writeFile() methods.
	 * 
	 * @param fileName
	 *            (Type: String) base name of the text files e.g text1.txt base
	 *            name = text
	 * @param numOfFiles
	 *            (Type: int) number of text files e.g text1.txt, text2.txt
	 *            numOfFiles = 2
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
	private void openFile() {
		// numOfFiles = 5 num will go from 1 - 5 inclusive
		while (num <= numOfFiles) {
			// store the file in a object
			// src\\textFileCleanUp\\Input\\text1.txt
			File file = new File("src\\textFileCleanUp\\Input\\" + fileName + num + ".txt");

			// try with resources opens the file, to process it line by line in
			// the while loop
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

				// used for each line in current text file
				String line;

				// loops through each line in text file
				while ((line = reader.readLine()) != null) {

					// sort the arraylist
					Collections.sort(text);

					// bimary search the current line to see if it is in the arraylist already
					// if binary search returns a value less than zero add the string to the array list.
					if (Collections.binarySearch(text, line) < 0) {
						text.add(line);
					}
				}

				// increment num when all lines of the text file have been read
				// and one copy of each has been added to the array list
				// in order to move onto the next text file or to exit the while
				// loop
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
	private void writeFile() {

		// creates a new File Object in the output folder
		File file = new File("src\\textFileCleanUp\\Output\\text2.txt");

		// try with resources to open BufferedWriter Object using file writer
		// object and the file
		try (BufferedWriter w = new BufferedWriter(new FileWriter(file))) {

			for (int x = 0; x < text.size(); x++) {

				// write each String in the array list to the text file
				w.write(text.get(x));

				// add a new line to the text file
				// Basically the "\n" equivalent
				w.newLine();
			}

			// close the buffer writer
			w.close();
			
		} catch (IOException e) {
			// catch any problem writing the file
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

		// new TextFileCleanUp("text", 4);

		// new Scanner object to get the base name and the number of textfiles
		Scanner input = new Scanner(System.in);
		System.out.println(
				"Put the Files into input folder and for each text file name them the same name and increment by one e.g text1.txt text2.txt etc");
		System.out.println("When you've finished this, please enter just the name e,g text1.txt text2.txt enter text");
		// base name for text files
		String name = input.nextLine();
		System.out.println("How many files are there? e.g text1.txt, text2.txt enter 2");
		// number of text files
		int num = input.nextInt();
		try {

			// run one new instance of TextFileCleanUp
			// because the constructor class calls the other method to perform
			// the task,
			// the new instance doesn't need to be assigned to a variable
			// once it's done the writeFile method the object is destroyed and
			// memory released
			new TextFileCleanUp(name, num);
			System.out.println("Done");
		} catch (InputMismatchException e) {
			// catching anything other than
			// number entered
			System.out.println("There was a problem with the number you entered.");
		} finally { 
			// close the scanner instance
			input.close();
		}
	}

}