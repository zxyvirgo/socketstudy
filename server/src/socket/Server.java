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
        int length=0;//��ͷ����ȡ����ϣ�������ݳ���
       char statecode;
        int templength = 0;
        int hopelength = 0;
        char state = '1';
        int j;//�����Ե�ǰ�Ļ�ȡ��¼��ƫ��

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
            	//���ݿͻ�����������ݼ�����ĳ���
            	int totalength = input.length;
            	
            	for (j=0 ;j<totalength; ){
            		
            	//��ֳɶ����
            	switch(state){	//����state=1��ʱ�򣬽�ȥ���ǽ�����ͷ
            	//��ȡ��ǰ3���ֽ��Ǽ�¼������Ϣ����
	            	case '1':{
	            		
		            	int number0 = input[j];
		            	int number1 = input[j+1];
		            	int number2 = input[j+2];
		            	
		            	//�жϰ�ͷ�Ƿ���Ϲ淶�������һ����ǰһ�εĲ���Ļ������ܸ���û�а�ͷ
		            	if(number0<58&&number0>47&&number1<58&&number1>47&&number2<58&&number2>47){
		            		
			            	length = (number0-48)*100+(number1-48)*10+(number2-48);
			            	System.out.println("the length is"+length);
		            	   
			            		  //statecode = (char)input[j+4];
			                      //�Ӿ����ͷ��ʼ�ĵ�7���ַ��𣬲������� ��������ΰ�ͷ�ĳ���
		            			  int leavingslength = totalength-j-7;
		            			  if (length <=leavingslength){
		            				  
				            		  byte[] content = new byte[length];  		
				            	      for (int i=0; i<length; i++){
				                      	content[i] = input[6+i+j];                 
				                      }
				            	      //ȡ���Ժ�������ݡ�
				            	      System.out.println(new String(content));
				            	      
			            	      
			            		  }else{            			  
			            			
			            			  //�����û�����꣬˵������Ҫ�ͻ��˼����������ݣ�����¼������Ѿ����͵ĳ���
			            			  templength = totalength -j -6;
			            			  //����Ҫ��ȡ�µĴ�����Ҫ��0�����Ҽ�¼�µ�ǰ������          			  
			            			  byte[] content = new byte[templength];  		
				            	      for (int i=0; i<templength; i++){
				                      	content[i] = input[6+i+j];                 
				                      }
				            	      //ȡ���Ժ�������ݡ�
				            	      System.out.println(new String(content));
				            	      state = '2';
			            		  }
		            			  j = j+length+6; 
			            		
		            	}//end if ��֤ͷ�ϵ������ֽڶ���0-9�����֣�������͵İ�����͹ر�����
		            	else{
		            		
			            		//��ͷ���󣬻ص���㲢�ҹر�����
			            		dataOutputStream.writeBytes("bye");
			                    //��ȡ�����Ժ󣬹ر�����
			                    System.out.println("Client(" + getName() + ") exit!");
			                    dataInputStream.close();
			                    dataOutputStream.close();
			                    client.close();
		            	}
	            	
		            	break;//�Ӱ�ͷ������ݷ�������
	            	
	            	}//end case 1����ʼʱ��ͷ�����
	            	
            	case '2':{//��ȡ�Ĳ��ǰ�ͷ�������ֻ�Ƕ�ǰһ�����ݵĲ���
            		
            		//ϣ���ĳ��ȣ�����ȫ���ȼ�ȥ�Ѿ���ȡ���ĳ���
            		length = length - templength;
            		if (totalength > length){
	            		byte[] content = new byte[length];  		
	            		for (int i=0; i<length; i++){
	                     	content[i] = input[i+j];                 
	                     }
	           	      //ȡ���Ժ�������ݡ�
	            		System.out.println(new String(content));
	            		j = j+hopelength+6;
	           	      	state = '1';
            		}
            		else{//�����һ������û�л�ȡ��ϣ�������ݣ���ô����Ҫ��2״̬������ȡ
            			templength = totalength;//˵�������ȡ��ȫ�����ǲ�����һ��û�������
            			byte[] content = new byte[templength];  		
            			for (int i=0; i<templength; i++){
 	                      	content[i] = input[i+j];                 
 	                     }
 	            	      //ȡ���Ժ�������ݡ�
 	            	    System.out.println(new String(content));
            		}
            		
            		break;
            	}
            	
            	}//end switch ����ѭ��֮�ڣ�����Ϊÿ�δ�ͷ������ȡ�ı�����
            	
            	}//forѭ������������ȡ��ȫ���ֽ�����һ�η��Ϳ��ܺ��ж������Ҳ���в������İ�
            	
            	//
          
                
            } catch (IOException e) {
            
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}