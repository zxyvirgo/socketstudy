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
                new CreateServerThread(socket);//��������ʱ����һ���̴߳���
            }
        } catch (IOException e) {
        } finally {
            close();
        }
    }

    //�߳���
    class CreateServerThread extends Thread {
        private Socket client;
        private BufferedReader bufferedReader;
        private PrintWriter printWriter;
        int length = 0;

        public CreateServerThread(Socket s) throws IOException {
            client = s;

            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            printWriter = new PrintWriter(client.getOutputStream(), true);
           //�ͻ��˷���һ������
            System.out.println("Client(" + getName() + ") come in...");
            start();
        }

        public void run() {
            try {
            	
            	//��ȡ�ͻ��˵���������
               String line = bufferedReader.readLine();
               System.out.println(line);
                while (line.indexOf("end")!= -1) {
                	//���������end�������̨����û��bye����Ϣ����������������
                    printWriter.println("continue, Client(" + getName() + ")!");
                    printWriter.println("continue, Client(" +client.getRemoteSocketAddress() + ")!");
                    line = bufferedReader.readLine();
                    System.out.println("Client(" + getName() + ") say: " + line);
                }
                
                //��ͻ��˷�������,������ݳ��ȴﵽ�ˣ��Ͳ��ټ����ͻ��˵����󣬾ͻظ�bye
               
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