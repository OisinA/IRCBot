package me.oisinaylward.irc;

import me.oisinaylward.irc.log.Log;

public class Message {
	
	private String nickname = "";
	private String username = "";
	private String ip = "";
	
	private String command = "";
	private String channel = "";
	private String message = "";
	
	private String input = "";
	
	public Message(String s) {
		input = s;
	}
	
	public void parse() {
		try {
			String[] split = input.split(" :");
		
			message = split[1];
		
			split = split[0].split(" ");
			command = split[1];
			channel = split[2];
		
			if(split[0].startsWith(":")) {
				split = split[0].split("!");
				nickname = split[0];
			
				split = split[1].split("@");
				username = split[0].replaceAll(":", "");
				ip = split[1];
			}
		} catch(Exception e) {
			Log.error(e);
		}
	}
	
	public String getIP() {
		return ip;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public String getChannel() {
		return channel;
	}
	
	public String getCommand() {
		return command;
	}
	
	public String getMessage() {
		return message;
	}
	
}
