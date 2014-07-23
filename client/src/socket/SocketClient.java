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
            //�������˷���end�������
            while(result.indexOf("bye") == -1){
            	
            	/*����ֽ�����3���ֽڼ�¼���ȣ�һ����¼״̬�����µ�������
            	�������ʱ��ȥ�������س�����ĩβ����һ���س�
            	���û��contine������Ϊ��һ�εķ������ݽ��з��ͣ��������ô
            	������һ�����ݵķ���*/
            	byte[] output;
            	output ="010:1:ajhkokkll2011:1:sgjkljkl\n".getBytes();
            	dataOutputStream.write(output);
            	dataOutputStream.flush();
                
                //�õ�����˵ķ�����Ϣ
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