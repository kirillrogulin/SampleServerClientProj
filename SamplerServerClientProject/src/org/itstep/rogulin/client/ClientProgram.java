package org.itstep.rogulin.client;

public class ClientProgram {

	public static void main(String[] args) {
		ClientImpl ci = new ClientImpl();
		ci.connectToServer("127.0.0.1", 9999);
		ci.createSenderToServer();
	}

}
