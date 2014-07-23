package socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
	

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 2013);
            socket.setSoTimeout(60000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            DataInputStream dataInputStream = new DataInputStream(
            		new BufferedInputStream(socket.getInputStream()));
            
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
           
            String result ="";
            //如果服务端返回end，则结束
            while(result.indexOf("bye") == -1){
            	
            	/*输出字节流，3个字节记录长度，一个记录状态，余下的是内容
            	处理包的时候，去掉其它回车，在末尾加上一个回车
            	如果没有contine，则作为第一次的发送数据进行发送，如果有那么
            	就是另一类数据的发送*/
            	byte[] output;
            	output ="010:1:ajhkokkll2011:1:sgjkljkl\n".getBytes();
            	dataOutputStream.write(output);
            	dataOutputStream.flush();
                
                //得到服务端的返回信息
                result = bufferedReader.readLine();
                System.out.println("Server say : " + result);
            	
            }
            
            dataOutputStream.close();
            dataInputStream.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
}