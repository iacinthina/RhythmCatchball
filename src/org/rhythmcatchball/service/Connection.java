package org.rhythmcatchball.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection {
	private static final int PORTMAX = 65535;
	
	Socket socket;
	String data;
	private int portNum;
	ServerSocket serverSocket;

	private BufferedWriter bufferedWriter;
	private BufferedReader bufferReader;
	
	private String errMsg;
	
	//생성하고 바로 생성한 클래서에서 client, server 둘 중 하나 실행해야
	public Connection() {
		//추후 구현 
		errMsg = "creation";
		socket = null;
		data = null;
		portNum = -1;
		serverSocket = null;
	}
	
	public void send(String data) {
		try {
			if (!isConnected()) {
				socketClear();
				if (bufferedWriter != null) bufferedWriter.close();
				if (bufferReader != null) bufferReader.close();
				bufferedWriter = null;
				bufferReader = null;
				errMsg = "Connection Lost";
				return;
			}
			if (bufferedWriter == null)
				bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write(data);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		
		} catch (SocketException s) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String recv() {
		String data = null;
		try {
			if (!isConnected()) {
				socketClear();
				if (bufferedWriter != null) bufferedWriter.close();
				if (bufferReader != null) bufferReader.close();
				bufferedWriter = null;
				bufferReader = null;
				errMsg = "Connection Lost";
				return data;
			}
			if (bufferReader == null)
				bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if (bufferReader.ready())
				data = bufferReader.readLine();
		
		} catch (SocketException s) {
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public boolean asClient(String ip, String port) {
		if (!parsePortNum(port)) return false;
		
		try {
			serverSocketClear();
			socketClear();
			socket = new Socket(ip, portNum);
		}
		catch (Exception e) {
			errMsg = "cannot find host";
			return false;
		}
		
		boolean connected = socket.isConnected();
		if (!connected) 
			errMsg = "invalid address";
		return connected;
	}
	
	public boolean asServer(String port) {
		if (!parsePortNum(port)) return false;
		
		try {
			serverSocketClear();
			serverSocket = new ServerSocket(portNum);
		} catch (Exception e) {
			errMsg = "cannot create server";
			return false;
		}

		boolean bounded = serverSocket.isBound();
		if (!bounded) 
			errMsg = "cannot bound host";
		return bounded;
	}
	
	public boolean asServerWait() {
		Socket opponentSocket = null;

		if (socket != null) {
			if (socket.isConnected())
				return true;
		}
		
		try {
			socketClear();
			opponentSocket = serverSocket.accept();
			socket = opponentSocket;
		} catch (Exception e) {
			errMsg = "accept failed";
		}
		
		if (socket == null) return false;
		boolean connected = socket.isConnected();
		if (!connected) 
			errMsg = "invalid address";
		return connected;
	}
	
	public boolean isServer() {
		return (serverSocket != null && serverSocket.isBound());
	}
	
	public boolean isClient() {
		return (serverSocket == null && socket != null && socket.isConnected());
	}

	public boolean isConnected() {
		return (socket != null && socket.isConnected());
	}
	
	public void closeServer() {
		try {
			serverSocketClear();
			serverSocket = null;
		} catch (IOException e) {
			errMsg = "Cannot close server";
		}
	}
	
	public void closeClient() {
		try {
			socketClear();
			socket = null;
		} catch (IOException e) {
			errMsg = "Cannot close client";
		}
	}
	
	public String receive(){
		return this.data;
	}
	
	public String checkMyIP() {
		try {
			errMsg = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			errMsg = "Unknown Host";
		}
		return errMsg;
	}
	
	private void socketClear() throws IOException {
		if (socket != null) {
			if (!socket.isClosed()) 
				socket.close();
		}
	}

	private void serverSocketClear() throws IOException {
		if (serverSocket != null) {
			if (!serverSocket.isClosed()) 
				serverSocket.close();
		}
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	private boolean parsePortNum(String port) {
		try {
			portNum = Integer.parseInt(port);
		} catch(NumberFormatException e) {
			portNum = -1;
			errMsg = "Failed to get port number";
			return false;
		}
		if (portNum <= 0 || portNum > PORTMAX) {
			errMsg = "Invaild port number range (0 ~ "+PORTMAX+")";
			return false;
		}
		return true;
	}
}
