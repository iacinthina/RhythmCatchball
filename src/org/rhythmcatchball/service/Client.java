package org.rhythmcatchball.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client {
	
	Socket socket;
	int portNum;
	String data;
	
	public static void main(String args[]) {
		
		Scanner nw = new Scanner(System.in);
		String port, IP;
		System.out.println("input port num");
		port = nw.nextLine();
		System.out.println("input IP num");
		IP = nw.nextLine();
		Client test = new Client(IP , port);
	}
	
	public Client(String IP, String Port){
		
		portNum = Integer.parseInt(Port);
		
		try
		{
			socket = new Socket(IP, portNum);
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

	public void Send(String data) {
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

