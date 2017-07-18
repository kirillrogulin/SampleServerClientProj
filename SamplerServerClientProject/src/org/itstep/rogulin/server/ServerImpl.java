package org.itstep.rogulin.server;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import org.itstep.rogulin.abstractions.Server;

public class ServerImpl implements Runnable, Server {
	private final static Logger lg;
	private boolean isStopped = false;;
	private ServerSocket ss = null;
	private int port;
	
	static {
		lg = Logger.getLogger(ServerImpl.class.getName());
		lg.setLevel(Level.INFO);
	}
	
	private ServerImpl() {
		this(8889);
	}
	private ServerImpl(int port) {
		this.port = port;
	}
	
	public void run() {
		Socket clientSocket = null;
		try {
			while(!isStopped) {
				try {
					lg.log(Level.INFO, "Waiting for client connections...");
					clientSocket = ss.accept();
					lg.log(Level.INFO, "Client connected!");
					BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					String line = br.readLine();
					if(line.toUpperCase().equals("Q")) {
						lg.log(Level.INFO, "Client session ended!");
						br.close();
						clientSocket.close();
						return;
					} else {
						System.out.println("Message: " + line);
						br.close();
					}
				} catch (IOException ioex) {
					if(isStopped()) {
						lg.log(Level.INFO, "Server is stopped!");
						return;
					}
					throw new RuntimeException("Cannot accept client connection", ioex);
				}
			}
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public boolean isStopped() { return this.isStopped; }
	
	public synchronized void stop() {
		this.isStopped = true;
		try {
			
			this.ss.close();
		} catch(IOException ioe) {
			lg.log(Level.SEVERE, "Cannot stop server!\n" + ioe.getMessage());
		}
	}
	
	public static ServerImpl createServer(int port) {
		ServerSocket ss = null;
		ServerImpl si = null;
		try {
			if(port < 0 || port > 65535) {
				lg.log(Level.WARNING, "Server port should be between 0 and 65535 (both inclusive)!\n");
				lg.log(Level.WARNING, "Now will assign server port automatically (");
				si = new ServerImpl(0);
			} else {
				si = new ServerImpl(port);
			}
			ss = new ServerSocket(si.port);
			si.ss = ss;
		} catch (IOException ioe) {
			lg.log(Level.SEVERE, "Cannot start server!\n" + ioe.getMessage());
		}
		lg.log(Level.INFO, "Server started");
		return si;
	}
}
