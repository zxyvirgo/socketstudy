import java.net.*;
import java.io.*;
/*
 * �ͻ��ˣ�
 */
class  LoginClient
{
	public static void main(String[] args)throws Exception 
	{
		 Socket socket = null;  
	        try {  
	            socket = new Socket("localhost",8888);  
	            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());  
	            DataInputStream dis = new DataInputStream(socket.getInputStream());  
	            dos.writeUTF("���ǿͻ��ˣ���������!");  
	            System.out.println(dis.readUTF());  
	            socket.close();  
	        } catch (UnknownHostException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}
}
