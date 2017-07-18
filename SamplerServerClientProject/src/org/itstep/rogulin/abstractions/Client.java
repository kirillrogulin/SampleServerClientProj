package org.itstep.rogulin.abstractions;

public interface Client {
	void connectToServer(String ip, int port);
	void createSenderToServer();
}
