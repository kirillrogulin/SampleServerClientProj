package org.itstep.rogulin.server;

public class ServerProgram {

	public static void main(String[] args) {
		ServerImpl server = ServerImpl.createServer(9999);
		new Thread(server).start();
	}

}
