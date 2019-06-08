package org.rhythmcatchball.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Connection {
	Socket socket;
	String data;
	private int portNum;
	InetAddress serverIP;
	Socket opponentSocket;
	Socket mySocket;
	ServerSocket serverSocket;
	BufferedReader bufferReader;
	
	//생성하고 바로 생성한 클래서에서 client, server 둘 중 하나 실행해야
	public Connection() {
		//추후 구현 
	}
	
	public static void main(String[] args) {
		//삭제 예정 
	}
	
	//Client생성 메소드
	//ip, port 입력받아 연결 생성 
	public void client(String ip, String port){
			
			portNum = Integer.parseInt(port);
			
			try
			{
				socket = new Socket(ip, portNum);
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "invalid ip/port", null, JOptionPane.ERROR_MESSAGE, null);
				return;
			}
			
			new Thread(){
				@Override
				public void run()
				{
					BufferedReader bufferedReader = null;
					
					try
					{
						bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
						while ( true )
						{
							data = bufferedReader.readLine();
		
							if( data != null && data.trim().length() > 0 )
							{
								//testing message
								JOptionPane.showMessageDialog(null, data);
							}
						}
					}
					catch (Exception e)
					{
						System.out.println(e+"=>client run");
						e.printStackTrace();
					}
				}
				
			}.start();
		}
	
	//Server생성 메소
	public void server(String portString) {

		//입력받은 port번호 정수형으로 변형 
		portNum = Integer.parseInt(portString);
		
		JOptionPane.showMessageDialog(null, "waiting for opponent...");

		
		
		//로컬 호스트 IP받기 
		try {
			serverIP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//서버 생성 
		try {
			serverSocket = new ServerSocket(portNum);
			JOptionPane.showMessageDialog(null, "ip = " + serverIP);
			System.out.println("서버생성\n");
			
			
			new Thread()
			{
				@Override
				public void run() {
					
					//온라인 플레이어 상대방 연결 
					try {
						opponentSocket = serverSocket.accept();
						
						JOptionPane.showMessageDialog(null, "here's your opponent!!!");

						
						bufferReader = new BufferedReader(new InputStreamReader(opponentSocket.getInputStream()));
						System.out.println("opponent connected...\n");
						while(true) {
							data = null;
							String opponentData = null;
							opponentData = bufferReader.readLine();
							
							if(opponentData.length() > 0) {
								data = opponentData;
								//test
								JOptionPane.showMessageDialog(null, data);
							}
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(String data) {
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write(data);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public String receive(){
		return this.data;
	}
	
}
