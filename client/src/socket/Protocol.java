package socket;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Protocol implements Serializable {
	
	byte length = new Byte("10");
	byte statecode = new Byte("1");
//	byte[] author = "zxy".getBytes();
	byte[] content = "1234567890".getBytes();
	/*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
	byte[] time;*/
	public byte getLength() {
		return length;
	}
	public void setLength(byte length) {
		this.length = length;
	}
	public byte getStatecode() {
		return statecode;
	}
	public void setStatecode(byte statecode) {
		this.statecode = statecode;
	}
/*	public byte[] getAuthor() {
		return author;
	}
	public void setAuthor(byte[] author) {
		this.author = author;
	}*/
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	/*public byte[] getTime() {
		//取当前时间
		Date date = new Date();
		this.time = simpleDateFormat.format(date).getBytes();
		return time;
	}
	*/
	//序列化
	/*public String getMessage(){	
		return "length("+length+"):statecode("+statecode+"):author("+author
				+"):time("+this.getTime()+"):content("+content;
	}*/
	
	

	
	
	
		
}
