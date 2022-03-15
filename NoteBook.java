package base;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteBook implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Folder> folders;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public NoteBook(String file) {
		//TODO
		FileInputStream fis = null;
		ObjectInputStream in = null;
		NoteBook n = null;
		try {
			fis = new FileInputStream(file);
			in = new ObjectInputStream(fis);
			n = (NoteBook)in.readObject();
			in.close();
		}catch(Exception e) {
			System.out.println("Notebook load exception");
			e.printStackTrace();
		}
		// TODO
		this.folders = n.folders;
		n = null;
	}
	
	public boolean createTextNote(String folderName, String title) {
		TextNote note = new TextNote(title);
		//System.out.println("Creating Textnote");
		return insertNote(folderName, note);
	}
	
	public boolean createTextNote(String folderName, String title, String content) {
		TextNote note = new TextNote(title, content);
		//System.out.println("Creating Textnote 2.0");
		return insertNote(folderName, note);
	}
	
	public boolean createImageNote(String folderName, String title) {
		ImageNote note = new ImageNote(title);
		//System.out.println("Creating Imagenote");
		return insertNote(folderName, note);
	} 
	
	public ArrayList<Folder> getFolders(){
		return folders;
	}

	@SuppressWarnings("null")
	private boolean insertNote(String folderName, Note note) {
		Folder f = null;
		for (Folder f1:folders) {
			if(f1.getName().equals(folderName)) {
				//System.out.println("Copy folder address");
				f = f1;
				//System.out.println(f.getNotes().size());			
			}
		}
		if(f == null) {
			//System.out.println("Creating Folder");
			f = new Folder(folderName);
			folders.add(f);
			f.addNote(note);			
			return true;
		}
		// System.out.println(f.getName());
		else {
			for(Note n : f.getNotes()) {
				if(n.equals(note)) {
					System.out.println("Creating note " + note.getTitle() + " under folder " + folderName + " failed");
					return false;
				}
			}
			//System.out.println("Call addNote");
			f.addNote(note);			
			return true;
		}
	}
	
	public void sortFolders() {
		// TO DO
		Collections.sort(folders);
	}
	
	
	public List<Note> searchNotes(String keywords){
		//create result list
		List<Note> final_result_list = new ArrayList<Note>();
		List<Note> result_list = new ArrayList<Note>();
		//go through all folders
		for(Folder folder: folders) {
			// return a matched note list from each folder
			result_list = folder.searchNotes(keywords);
			// copy all to to final result
			for(Note match_node: result_list) {
				final_result_list.add(match_node);
			}
		}
		return final_result_list;
	}
	
	public boolean save(String file) {
		// TO DO
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			//TO DO
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(this);
			out.close();						
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Notebook save exception");
			return false;
		}
		return true;
	}
}
