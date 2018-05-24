/**
 * 
 */
package com.fish.tools.port;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * ����ຯ��
 * @author fish
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("����1: ���ط�����, ����2:Ŀ�������  ����3:Ŀ�Ķ˿�");
		
		int listen_port = Integer.parseInt(args[0]);
		String address = args[1];
		int server_port = Integer.parseInt(args[2]);
		NatBaseClient client = new BlockNatClient();
		client.init(listen_port, address, server_port);
		client.start();
		System.out.println("ת���������: localhost:" + client.m_listen_port + 
				" ------>" + " " + client.m_dst_address + ":" + client.m_dst_port);
	}

}
