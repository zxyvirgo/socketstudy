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
 * 服务器端：
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
            System.out.println("服务器接收到客户端的连接请求：" + dis.readUTF());  
            dos.writeUTF("接受连接请求，连接成功!");  
            socket.close();  
            ss.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}

