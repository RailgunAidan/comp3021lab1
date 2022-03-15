package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class TextNote extends Note {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	String content;
	public TextNote(String title) {
		super(title);
	}
	public TextNote(String title, String content) {
		super(title);
		this.content = content;
	}
	/**
	 * load a TextNote from File f
	 * 
	 * the tile of the TextNote is the name of the file
	 * the content of the TextNote is the content of the file
	 * 
	 * @param File f 
	 */

	public TextNote(File f) {
		super(f.getName());
		this.content = getTextFromFile(f.getAbsolutePath());
	}
	/**
	 * get the content of a file
	 * 
	 * @param absolutePath of the file
	 * @return the content of the file
	 */

	private String getTextFromFile(String absolutePath) {
		// TODO
		BufferedReader reader = null;
		String result = "";
		StringBuilder  stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		try {
	        while((result = reader.readLine()) != null) {
	        	reader = new BufferedReader(new FileReader (absolutePath));
	            stringBuilder.append(result);
	            stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    }catch(Exception e) {
			e.printStackTrace();
		}		
		return stringBuilder.toString();
	}
	
	/**
	 * export text note to file
	 * 
	 * 
	 * @param pathFolder path of the folder where to export the note
	 * the file has to be named as the title of the note with extension ".txt"
	 * 
	 * if the tile contains white spaces " " they has to be replaced with underscores "_"
	 * 
	 * 
	 */
	public void exportTextToFile(String pathFolder) {
		//TODO
		if(pathFolder == "") {
			pathFolder = ".";
		}
			File file = new File( pathFolder + File.separator + this.getTitle().replaceAll(" ", "_") + ".txt");
			// TODO
			FileWriter writer = null;
			try {
				writer = new FileWriter(file);
				writer.write(content);
				writer.flush();
				writer.close();
			}catch(Exception e) {
				e.printStackTrace();
			}	


	 }



	
}
