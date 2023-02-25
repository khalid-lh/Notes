package est.dsic.models;

import java.util.Date;

public class Note {

	private int id ;
	private String note;
	private String date;
	
	
	public Note(){
		super();	
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date2) {
		this.date = date2;
	}
	
}
