package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 2013);
            socket.setSoTimeout(60000);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
           
            String result = "";
            //�������˷���end�������
            while(result.indexOf("bye") == -1){
            	//�Ӽ��̻�ȡ����
                BufferedReader sysBuff = new BufferedReader(new InputStreamReader(System.in));
                //�����˷��Ͳ���ˢ�ܵ�
                Protocol protocol = new Protocol();
                 //���û���������Ϣ����װ��Э�飬�����͸������
                protocol.setInfo(sysBuff.readLine());
                printWriter.println(protocol.getMessage());
                printWriter.flush();
                
                //�õ�����˵ķ�����Ϣ
                result = bufferedReader.readLine();
                System.out.println("Server say : " + result);
                
            }

            printWriter.close();
            bufferedReader.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
}