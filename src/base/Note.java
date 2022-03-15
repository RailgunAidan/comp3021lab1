package base;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Note implements Comparable<Note>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
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

	@Override
	public int compareTo(Note o) {
		// TODO Auto-generated method stub
		if (this.date.compareTo(o.date)>0)
			return -1;
		if (this.date.compareTo(o.date)==0)
			return 0;
		if (this.date.compareTo(o.date)<0) {
			return 1;
		}
		return 0;
	}
	
	public String toString() {
		return date.toString() + "\t" + title;
	}
	
	
	
}
