package base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Folder implements Comparable<Folder>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Note> notes;
	private String name;
	
	public Folder(String name) {
		this.name = name;
		notes = new ArrayList<Note>();
	}
	
	public void addNote(Note note) {
		//System.out.println("Adding notes");
		notes.add(note);
	}
	
	public String getName() {return name;}
	
	public ArrayList<Note> getNotes(){return notes;}
	
	@Override
	public String toString() {
		int nText = 0;
		int nImage = 0;
		
		for(Note n : notes) {
			if (n instanceof TextNote) {
				nText++;
			}
			else if (n instanceof ImageNote) {
				nImage++;
			}
		}
		
		return name + ":" + nText + ":" + nImage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Folder other = (Folder) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int compareTo(Folder o) {
		// TODO Auto-generated method stub
		if (this.name.compareTo(o.name) == 0)
			return 0;
		if (this.name.compareTo(o.name) > 0)
			return 1;
		if (this.name.compareTo(o.name) < 0)
			return -1;
		return 0;
	}
	
	public void sortNotes() {
		//TO DO
		Collections.sort(notes);
	}
	
	//helper method
	public boolean matching(String content, String[] keywords) {
		boolean found = false;
		for(int i = 0; i<keywords.length; i++) {
			//System.out.println("Finding a keyword " + keywords[i] + " in " + content);
			if(i+1<keywords.length && keywords[i+1].contains("or")) {
				if(content.contains(keywords[i]) || content.contains(keywords[i+2])) {
					//skip "or" and the next letter for continue matching
					//System.out.println("Found a keyword " + keywords[i] + " or " + keywords[i+2] + " in " + content);
					found = true;
					i+=2;
				}
				else {
					//System.out.println("Unable to find a keyword " + keywords[i] + " or  " + keywords[i+2] + " in " + content);
					found = false;
				}
			}
			// no "or" next to the keyword
			else {
				if(content.contains(keywords[i])) {
					//System.out.println("Found a keyword " + keywords[i] + " in " + content);
					found = true;
				}
				else {
					//System.out.println("Unable to find a keyword " + keywords[i] + " in " + content);
					found = false;
				}
			}
			if(!found) {
				return found;
			}
		}
		return found;
	}
	
	public List<Note> searchNotes(String keywords){
		//create result list
		ArrayList<Note> result_list = new ArrayList<Note>();
		// create a word list removing all spaces, just words and change all to lower case
		String s_keywords = keywords.toLowerCase();
		String[] words = s_keywords.split("\\W+");
		//System.out.println(words.toString());
		// going through all the notes
		for(Note n : notes) {
			if (n instanceof TextNote) {
				//casing n back to textnote
				TextNote text = (TextNote)n;
				// combine title and content into a single string
				String title = text.getTitle().toLowerCase();
				String content = text.content.toLowerCase();
				String search_pool = title + " " + content;
				// call matching function
				if(matching(search_pool,words)) {
					//if match add to result_list
					result_list.add(n);
				}
				
			}
			else if (n instanceof ImageNote) {
				String title = n.getTitle().toLowerCase();
				if(matching(title,words)) {
					//if match add to result_list
					result_list.add(n);
				}
				
			}
		}
		//result
		return result_list;
		
	}
	
	public boolean removeNotes(String title) {
		//TODO
		// Given title of note and then delete it from the folder
		// True if delete success, otherwise return false
		Note nFound = null;
		for (Note n : notes) {
			if(n.getTitle().equals(title)) {
				nFound = n;
				break;
			}
		}
		if(nFound != null) {
			// Found
			notes.remove(nFound);
			return true;
		}
		return false;
	}
	
	
	
	
}
