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
        int length=0;//包头里面取到的希望的数据长度
       char statecode;
        int templength = 0;
        int hopelength = 0;
        char state = '1';
        int j;//用来对当前的获取记录的偏移

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
            	//根据客户端请求的数据计算出的长度
            	int totalength = input.length;
            	
            	for (j=0 ;j<totalength; ){
            		
            	//拆分成多个包
            	switch(state){	//处在state=1的时候，进去就是解析包头
            	//获取的前3个字节是记录的是消息长度
	            	case '1':{
	            		
		            	int number0 = input[j];
		            	int number1 = input[j+1];
		            	int number2 = input[j+2];
		            	
		            	//判断包头是否符合规范，如果下一次是前一次的补充的话，可能根本没有包头
		            	if(number0<58&&number0>47&&number1<58&&number1>47&&number2<58&&number2>47){
		            		
			            	length = (number0-48)*100+(number1-48)*10+(number2-48);
			            	System.out.println("the length is"+length);
		            	   
			            		  //statecode = (char)input[j+4];
			                      //从距离包头开始的第7个字符起，才是内容 ，但是如何包头的长度
		            			  int leavingslength = totalength-j-7;
		            			  if (length <=leavingslength){
		            				  
				            		  byte[] content = new byte[length];  		
				            	      for (int i=0; i<length; i++){
				                      	content[i] = input[6+i+j];                 
				                      }
				            	      //取完以后，输出内容。
				            	      System.out.println(new String(content));
				            	      
			            	      
			            		  }else{            			  
			            			
			            			  //如果还没发送完，说明还需要客户端继续发送数据，并记录下这次已经发送的长度
			            			  templength = totalength -j -6;
			            			  //发现要获取新的串。需要归0，并且记录下当前的数据          			  
			            			  byte[] content = new byte[templength];  		
				            	      for (int i=0; i<templength; i++){
				                      	content[i] = input[6+i+j];                 
				                      }
				            	      //取完以后，输出内容。
				            	      System.out.println(new String(content));
				            	      state = '2';
			            		  }
		            			  j = j+length+6; 
			            		
		            	}//end if 保证头上的三个字节都是0-9的数字，如果发送的包有误就关闭连接
		            	else{
		            		
			            		//包头有误，回到起点并且关闭连接
			            		dataOutputStream.writeBytes("bye");
			                    //读取结束以后，关闭连接
			                    System.out.println("Client(" + getName() + ") exit!");
			                    dataInputStream.close();
			                    dataOutputStream.close();
			                    client.close();
		            	}
	            	
		            	break;//从包头起的数据分析结束
	            	
	            	}//end case 1，开始时包头的情况
	            	
            	case '2':{//获取的不是包头的情况，只是对前一次数据的补充
            		
            		//希望的长度，就是全长度减去已经获取到的长度
            		length = length - templength;
            		if (totalength > length){
	            		byte[] content = new byte[length];  		
	            		for (int i=0; i<length; i++){
	                     	content[i] = input[i+j];                 
	                     }
	           	      //取完以后，输出内容。
	            		System.out.println(new String(content));
	            		j = j+hopelength+6;
	           	      	state = '1';
            		}
            		else{//如果这一次依旧没有获取完希望的数据，那么还是要在2状态继续获取
            			templength = totalength;//说明这个获取的全部都是补充上一次没有收完的
            			byte[] content = new byte[templength];  		
            			for (int i=0; i<templength; i++){
 	                      	content[i] = input[i+j];                 
 	                     }
 	            	      //取完以后，输出内容。
 	            	    System.out.println(new String(content));
            		}
            		
            		break;
            	}
            	
            	}//end switch ，在循环之内，是因为每次从头解析获取的比特流
            	
            	}//for循环，解析到获取的全部字节流，一次发送可能含有多个包，也能有不完整的包
            	
            	//
          
                
            } catch (IOException e) {
            
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}