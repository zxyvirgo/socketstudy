import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


/*
 * �������ˣ�
 */
class  LoginServer
{

	public static void main(String[] args) throws Exception
	{
	
		ServerSocket ss = null;  
        try {  
            ss = new ServerSocket(8888);  
            Socket socket = ss.accept();  
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());  
            DataInputStream dis = new DataInputStream(socket.getInputStream());  
            System.out.println("���������յ��ͻ��˵���������" + dis.readUTF());  
            dos.writeUTF("���������������ӳɹ�!");  
            socket.close();  
            ss.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}

