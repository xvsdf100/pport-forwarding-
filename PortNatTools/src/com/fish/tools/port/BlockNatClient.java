/**
 * 
 */
package com.fish.tools.port;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阻塞实现
 * 详细见文档
 * @author fish
 *
 */
public class BlockNatClient extends NatBaseClient {

	ServerSocket server;
	ExecutorService cachedThread = Executors.newCachedThreadPool();  

	public boolean start() {
		
		try {
			listen();
			startAcceptThread();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private void listen() throws IOException {
		server = new ServerSocket( m_listen_port);
	}
	
	private void startAcceptThread() {
		
		cachedThread.execute(new Runnable() {
			
			@Override
			public void run() {
			
				try {
					do {
						Socket client1 = server.accept();
						startClientThread(client1);
					} while (true);
				}catch (Exception e) {
					
				}
			}
		});

	}
	
	private void startClientThread(final Socket client1) {
		final Socket client2;
		try {
			client2 = new Socket(m_dst_address, m_dst_port);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		
		cachedThread.execute(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					do {
						
						String info = socketRead(client1);
						
						if (info == null) {
							break;
						}
						
						if(!info.isEmpty()) {
							System.out.println("1: 读取客户端数据:" + info);
						}
						
						if(info.isEmpty()) {
							Thread.sleep(10);
						}

						if(!info.isEmpty()) {
							System.out.println("2: 发送数据到服务器:" + info);
						}
						
						socketWrite(client2, info);
						
						info = socketRead(client2);
						
						if(info == null) {
							break;
						}
						
						if(!info.isEmpty()) {
							System.out.println("3: 读取服务器响应数据:" + info);
						}
							
						socketWrite(client1, info);
						
					} while (true);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						client1.close();
						client2.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	/**
	 * 默认阻塞读
	 * @param s
	 * @return
	 * @throws IOException 
	 */
	private String socketRead(final Socket s) throws IOException {
		return socketRead(s, false);
	}
	
	private String socketRead(final Socket s, boolean block) throws IOException {
		
		
		BufferedReader reader = getSocketBufferReader(s);
		
		//防止阻塞
		if(!block && !reader.ready()) {
			return "";
		}
			
		String info = "";
		CharBuffer charBuffer = CharBuffer.allocate(1024);

		if (reader.read(charBuffer) >= 0) {
			charBuffer.flip();
			info = charBuffer.toString();
			System.out.println("读取去:" + info);
		} else {
			System.out.println("读取socket断开");
		}

		//reader close就会断开了
		//reader.close();
		
		return info;
		
	}
	
	static void socketWrite(Socket socket, String buffer) throws IOException {
		OutputStream os;

		os = socket.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
		bw.write(buffer);
		bw.flush();
	}

}
