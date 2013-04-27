package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author Ana Luiza Motta Gones 
 * @author Leticia Farias Wanderley
 *	@author Maysa De Macedo Souza
 *
 */
public class SystemReader {
			
	private ArrayList<BabyName> names  = new ArrayList<BabyName>();

	/**
	 * Constructor of FileReader 
	 */
	public SystemReader(){
	}

	/**
	 * Opens the file of BabyNames
	 * @param fileName
	 * 		Name of the file
	 * @throws Exception if the file is not found
	 */
	public void openFile(String fileName) throws Exception{
		String line;
		String[] list;
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			list = line.split(",");
			this.names.add(new BabyName(list[0], Integer.parseInt(list[1])));
		}
		scanner.close();
	}

	/**
	 * Retrieves the list of BabyNames
	 * @return names
	 * 		List of names from the file
	 */
	public ArrayList<BabyName> getNames() {
		return names;
	}	
}