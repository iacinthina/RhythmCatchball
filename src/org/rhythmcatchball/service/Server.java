package org.rhythmcatchball.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Server {
	
	/*
	 * 속도는 UDP가 더 낫지만 싱크 생각해서 TCP로 구현함
	 * 서버에 클라이언트는 2개만 존재한다는 전제가 있음 
	 * 아직 종료에 대해서 buffer, socket의 close는 깔끔하지 못
	 */
	
	private int PortNum;
	InetAddress serverIP;
	Socket opponentSocket, mySocket;
	ServerSocket serverSocket;
	BufferedReader bufferedReader;
	String data;
	
	//test main
	public static void main(String argsp[]) {
		Scanner nw = new Scanner(System.in);
		String port;
		port = nw.nextLine();
		Server test = new Server(port);
		
	}
	
	
	public Server(String PORTstring) {

		//입력받은 port번호 정수형으로 변형 
		PortNum = Integer.parseInt(PORTstring);
		
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
			serverSocket = new ServerSocket(PortNum);
			JOptionPane.showMessageDialog(null, "ip = " + serverIP);
			System.out.println("서버생성\n");
			
			
			new Thread()
			{
				public void run() {
					
					//온라인 플레이어 상대방 연결 
					try {
						opponentSocket = serverSocket.accept();
						
						JOptionPane.showMessageDialog(null, "here's your opponent!!!");

						
						bufferedReader = new BufferedReader(new InputStreamReader(opponentSocket.getInputStream()));
						System.out.println("opponent connected...\n");
						while(true) {
							data = null;
							String opponentData = null;
							opponentData = bufferedReader.readLine();
							
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
	
	
	
	public void Send(String data){
		BufferedWriter bufferedWriter;
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(opponentSocket.getOutputStream()));
			bufferedWriter.write(data);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public String Receive() {
		return this.data;
	}
	

}
