package base;

import java.util.Date;
import java.util.Objects;

public class Note {
	private Date date;
	private String title;
	
	public Note(String title) {
		this.date = new Date(System.currentTimeMillis());
		this.title = title;
	}
	
	public String getTitle() {return title;}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}
	
	// @Override
	public boolean equals(Note obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.title.equals(obj.title)) {
			return true;
		}
		//if (getClass() != obj.getClass())
			//return false;
		//Note other = (Note) obj;
		//return Objects.equals(title, other.title);
		return false;
	}
	
	
}
