package socket;
import java.io.*;
import java.net.*;

public class Server extends ServerSocket {
    private static final int SERVER_PORT = 2013;

    public Server() throws IOException {
        super(SERVER_PORT);

        try {
            while (true) {
                Socket socket = accept();
                new CreateServerThread(socket);//当有请求时，启一个线程处理
            }
        } catch (IOException e) {
        } finally {
            close();
        }
    }

    //线程类
    class CreateServerThread extends Thread {
        private Socket client;
        private BufferedReader bufferedReader;
        private PrintWriter printWriter;
        int length = 0;

        public CreateServerThread(Socket s) throws IOException {
            client = s;

            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            printWriter = new PrintWriter(client.getOutputStream(), true);
           //客户端发送一次请求
            System.out.println("Client(" + getName() + ") come in...");
            start();
        }

        public void run() {
            try {
            	
            	//获取客户端的输入数据
               String line = bufferedReader.readLine();
               System.out.println(line);
                while (line.indexOf("end")!= -1) {
                	//如果不包含end，则向后台发送没有bye的消息，并继续接受数据
                    printWriter.println("continue, Client(" + getName() + ")!");
                    printWriter.println("continue, Client(" +client.getRemoteSocketAddress() + ")!");
                    line = bufferedReader.readLine();
                    System.out.println("Client(" + getName() + ") say: " + line);
                }
                
                //向客户端发送数据,如果数据长度达到了，就不再继续客户端的请求，就回复bye
               
                printWriter.println("bye, Client(" + getName() + ")!");
                
                System.out.println("Client(" + getName() + ") exit!");
                printWriter.close();
                bufferedReader.close();
                client.close();
            } catch (IOException e) {
            }
        }
    }

    public static String processInput(String input){
    	String output = null;
    	int state=0;
    	if(state == 0){
    		
    		output = "waiting";
    		state = 1;
    	}
    	else if(state == 1){
    		
    	}
    	
    	return output;
    }
    public static void main(String[] args) throws IOException {
        new Server();
    }
}