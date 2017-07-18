package org.itstep.rogulin.client;

import java.io.*;
import java.net.*;

import org.itstep.rogulin.abstractions.Client;

public class ClientImpl implements Client {
	private InetAddress ip;
	private int port;
	private Socket socket;
	private BufferedReader inFromConsole;
	private BufferedWriter outToServer;
	
	public void connectToServer(String ip, int port) {
		try {
			this.ip = InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		this.port = port;
		try {
			this.socket = new Socket(this.ip, this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createSenderToServer() {
		String line;
		this.inFromConsole = new BufferedReader(new InputStreamReader(System.in));
		try {
			this.outToServer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			while(true) {
				line = inFromConsole.readLine();
				outToServer.write(line);
				outToServer.flush();
				if(socket.isClosed() || socket.isConnected()) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				inFromConsole.close();
				outToServer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
