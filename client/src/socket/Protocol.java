package socket;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Protocol {
	
	String length="";
	String version = "v1.2";
	String author = "zxy";
	String time ="";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-mm-dd hh:mm:ss");
	String info = "";
	String end = "end";
	String message = "";

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTime() {
		Date date = new Date();
		this.time = simpleDateFormat.format(date);
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = version+author+time+info+end;
	}
	
	
	
}
