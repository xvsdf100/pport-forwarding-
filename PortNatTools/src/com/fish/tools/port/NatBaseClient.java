/**
 * 
 */
package com.fish.tools.port;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 基类方便实现方法
 * @author fish
 *
 */
public abstract class NatBaseClient {
	
	int m_listen_port;
	String m_dst_address;
	int m_dst_port;
	
	public void init(int listen_port, String dst_address, int dst_port) {
		m_listen_port = listen_port;
		m_dst_address = dst_address;
		m_dst_port = dst_port; 
	}
	
	abstract boolean start();
	
	
	static BufferedReader getSocketBufferReader(final Socket socket) {
		InputStream is;
		try {
			is = socket.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        return br;
	}
	//这里根据自己需要增加抽象方法，这样不管用哪种IO实现都可以进行
	
}
