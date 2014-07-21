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
        private  DataInputStream  dataInputStream ;
        private DataOutputStream dataOutputStream;
        private BufferedReader bufferedReader;
        int length=0;
       char statecode;
        int templength = 0;

        public CreateServerThread(Socket s) throws IOException {
            client = s;

         //   dataInputStream = new DataInputStream(new BufferedInputStream(client.getInputStream()));
            
            dataOutputStream = new DataOutputStream(client.getOutputStream());
           //�ͻ��˷���һ������
            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Client(" + getName() + ") come in...");
            start();
        }

        public void run() {
            try {
            	            	
            	//��ȡ�ͻ��˵�����
            	byte[] input =bufferedReader.readLine().getBytes();   
            	int totalength = input.length;
            	
            	for (int j=0 ;j<totalength; j++){
            		
            	//��ֳɶ����
            	
            	//��ȡ��ǰ3���ֽ��Ǽ�¼������Ϣ����
            	int number0 = input[j];
            	int number1 = input[j+1];
            	int number2 = input[j+2];
            	
            	//�жϰ�ͷ�Ƿ���Ϲ淶
            	if(number0<58&&number0>47&&number1<58&&number1>47&&number2<58&&number2>47){
            		
            	length = (number0-48)*100+(number1-48)*10+(number2-48);
            	System.out.println("the length is"+length);
            	   
            	statecode = (char)input[j+5];
            	
            		switch(statecode){
            		
            		case 49:{
            		
	                      //�Ӿ����ͷ��ʼ�ĵ�7���ַ��𣬲������� ��������ΰ�ͷ�ĳ���
	            	      //���µ����ݲ�������ôӦ����ͻ��˷��������ÿͻ��˼�����������
	            		  if (length <=totalength-j-7){
	            		  byte[] content = new byte[length];  		
	            	      for (int i=0; i<=length; i--){
	                      	content[i] = input[7+i+j];                 
	                      }
	            	      //ȡ���Ժ�������ݡ�
	            	      System.out.println(new String(content));
	            		  }else{
	            			  //�����û�����꣬˵������Ҫ�ͻ��˼����������ݣ�����¼������Ѿ����͵ĳ���
	            			  dataOutputStream.writeBytes("continue");         			  
	            			  templength = totalength -j -7;
	            		  }
	            		  
	            	      break;
            		}
            		case '2':{
            			  //״̬��2��ʾ�������Ϣ����ǰһ����Ϣ�Ĳ��䣬�������Ϣ��Ϊ���䣬��һ��
            			 //��û�����꣬��ô�ͼ������Ѿ��ܷ����꣬��ô�Ͳ����ˡ�
            			  if (length-templength<=totalength-j-7){
                    		  byte[] content = new byte[length];  		
                    	      for (int i=0; i<=length; i--){
                              	content[i] = input[7+i+j];                 
                              }
                    	      //ȡ���Ժ�������ݡ�
                    	      System.out.println(new String(content));
            			  }else{
                    			  //�����û�����꣬˵������Ҫ�ͻ��˼����������ݣ�����¼������Ѿ����͵ĳ���
            				  dataOutputStream.writeBytes("continue");         			  
                    		  templength = totalength -j -7;
                    	  }
                    		  
                    	      break;
            			
            		}
            		default:{
            			//�����һ�������Ϳ�ʼ��һ����
            			j = j+length+7;
            			System.out.println("j is"+j);
            			
            		}      		
            		}//switch
            		
       	
            	}//end if ��֤ͷ�ϵ������ֽڶ���0-9�����֣�������͵İ�����͹ر�����
            	
            	}//forѭ������������ȡ��ȫ���ֽ���
            	dataOutputStream.writeBytes("bye");
                //��ȡ�����Ժ󣬹ر�����
                System.out.println("Client(" + getName() + ") exit!");
                dataInputStream.close();
                dataOutputStream.close();
                client.close();
            } catch (IOException e) {
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}