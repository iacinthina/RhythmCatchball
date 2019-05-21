package org.rhythmcatchball.service;

import java.net.Socket;

import javax.swing.JOptionPane;

public class Client {
	
	Socket socket;
	int portNum;
	
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
		
		
		
		
	}
}
