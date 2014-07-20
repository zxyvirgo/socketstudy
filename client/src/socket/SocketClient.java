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
            //如果服务端返回end，则结束
            while(result.indexOf("bye") == -1){
            	//从键盘获取数据
                BufferedReader sysBuff = new BufferedReader(new InputStreamReader(System.in));
                //向服务端发送并冲刷管道
                Protocol protocol = new Protocol();
                 //将用户的输入信息，封装成协议，并发送给服务端
                protocol.setInfo(sysBuff.readLine());
                printWriter.println(protocol.getMessage());
                printWriter.flush();
                
                //得到服务端的返回信息
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