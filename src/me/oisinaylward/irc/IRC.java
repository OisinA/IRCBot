package me.oisinaylward.irc;

import me.oisinaylward.irc.log.Log;

public class IRC {
	
	String serverIP = "";
	int serverPort = 0;
	
	public IRC(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}
	
	public void start() {
		Log.log("Client", "Client starting up...");
		new IRCConnection(serverIP, serverPort).start();
	}
	
}
