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
           //客户端发送一次请求
            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Client(" + getName() + ") come in...");
            start();
        }

        public void run() {
            try {
            	            	
            	//获取客户端的请求
            	byte[] input =bufferedReader.readLine().getBytes();   
            	int totalength = input.length;
            	
            	for (int j=0 ;j<totalength; j++){
            		
            	//拆分成多个包
            	
            	//获取的前3个字节是记录的是消息长度
            	int number0 = input[j];
            	int number1 = input[j+1];
            	int number2 = input[j+2];
            	
            	//判断包头是否符合规范
            	if(number0<58&&number0>47&&number1<58&&number1>47&&number2<58&&number2>47){
            		
            	length = (number0-48)*100+(number1-48)*10+(number2-48);
            	System.out.println("the length is"+length);
            	   
            	statecode = (char)input[j+5];
            	
            		switch(statecode){
            		
            		case 49:{
            		
	                      //从距离包头开始的第7个字符起，才是内容 ，但是如何包头的长度
	            	      //余下的数据不够，那么应该向客户端发送请求，让客户端继续输入数据
	            		  if (length <=totalength-j-7){
	            		  byte[] content = new byte[length];  		
	            	      for (int i=0; i<=length; i--){
	                      	content[i] = input[7+i+j];                 
	                      }
	            	      //取完以后，输出内容。
	            	      System.out.println(new String(content));
	            		  }else{
	            			  //如果还没发送完，说明还需要客户端继续发送数据，并记录下这次已经发送的长度
	            			  dataOutputStream.writeBytes("continue");         			  
	            			  templength = totalength -j -7;
	            		  }
	            		  
	            	      break;
            		}
            		case '2':{
            			  //状态码2表示这个条消息，是前一条消息的补充，如果新消息作为补充，这一次
            			 //还没发送完，那么就继续，已经能发送完，那么就不用了。
            			  if (length-templength<=totalength-j-7){
                    		  byte[] content = new byte[length];  		
                    	      for (int i=0; i<=length; i--){
                              	content[i] = input[7+i+j];                 
                              }
                    	      //取完以后，输出内容。
                    	      System.out.println(new String(content));
            			  }else{
                    			  //如果还没发送完，说明还需要客户端继续发送数据，并记录下这次已经发送的长度
            				  dataOutputStream.writeBytes("continue");         			  
                    		  templength = totalength -j -7;
                    	  }
                    		  
                    	      break;
            			
            		}
            		default:{
            			//读完第一个包，就开始下一个包
            			j = j+length+7;
            			System.out.println("j is"+j);
            			
            		}      		
            		}//switch
            		
       	
            	}//end if 保证头上的三个字节都是0-9的数字，如果发送的包有误就关闭连接
            	
            	}//for循环，解析到获取的全部字节流
            	dataOutputStream.writeBytes("bye");
                //读取结束以后，关闭连接
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