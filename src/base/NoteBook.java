package base;

import java.util.ArrayList;

public class NoteBook {
	private ArrayList<Folder> folders;
	
	public NoteBook() {
		folders = new ArrayList<Folder>();
	}
	
	public boolean createTextNote(String folderName, String title) {
		TextNote note = new TextNote(title);
		//System.out.println("Creating Textnote");
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
}
