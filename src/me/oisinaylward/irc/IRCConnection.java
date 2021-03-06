package me.oisinaylward.irc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import me.oisinaylward.irc.log.Log;

public class IRCConnection {
	
	public static String USERNAME = "Temp";
	public static String CHANNEL = "#haskell";
	
	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;
	
	private String ip;
	private int port;
	
	public IRCConnection(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public void start() {
		try {
			setupConnection();
			
			sendUserInfo();
			
			String line = "";
			boolean cont = false;
			while(!cont) {
				line = reader.readLine();
				if(line.indexOf("004") >= 0) {
					Log.log("Server", "Request accepted, joined server.");
					cont = true;
					break;
				} else if(line.indexOf("433") >= 0) {
					Log.log("Server", "Username already in use.", "Terminating...");
					return;
				} else {
					Log.log("Server", line);
				}
			}
			
			joinChannel(CHANNEL);
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
			    @Override
			    public void run() {
			    	try {
			    		writer.write("QUIT");
			    		Log.log("Client", "Sent quit protocol.");
			    		writer.flush();
			    	} catch(Exception e) {
			    		Log.error(e);
			    	}
			    }
			});
			
			cont = false;
			while(!cont) {
				line = reader.readLine();
				if(line.toLowerCase().startsWith("ping")) {
					Log.log("Server", "Received ping request.");
					writer.write("PONG " + line.substring(5) + FormatUtil.LINE_DOWN + FormatUtil.NEW_LINE);
					writer.flush();
				} else {
					Message m = new Message(line);
					m.parse();
					if(m.getCommand().equals("PRIVMSG"))
						Log.log("Server", m.getNickname() + " > " + m.getMessage());
					else
						Log.log("Server", line);
				}
			}
		} catch(IOException e) {
			Log.error(e);
		}
	}
	
	private void setupConnection() {
		try {
			socket = new Socket(ip, port);
		
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch(Exception e) {
			Log.error(e);
		}
	}
	
	private void sendUserInfo() {
		try {
			Log.log("Client", "Sending username information.");
			writer.write("NICK " + USERNAME + FormatUtil.LINE_DOWN + FormatUtil.NEW_LINE);
			writer.write("USER " + USERNAME + " 8 * : Testing..." + FormatUtil.LINE_DOWN + FormatUtil.NEW_LINE);
			writer.flush();
		} catch(Exception e) {
			Log.error(e);
		}
	}
	
	private void joinChannel(String channel) {
		try {
			writer.write("JOIN " + channel + FormatUtil.LINE_DOWN + FormatUtil.NEW_LINE);
			Log.log("Server", "Joining channel " + CHANNEL + ".");
			writer.flush();
		} catch(Exception e) {
			Log.error(e);
		}
	}
	
}
