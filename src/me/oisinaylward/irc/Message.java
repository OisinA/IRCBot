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
//		
//		String[] split = s.split(":");
//		nickname = split[0];
//		
//		split = s.split("!");
//		username = split[1];
//		
//		split = s.split("@");
//		ip = split[1];
//		
//		split = s.split(" ");
//		command = split[1];
//		
//		if(command.equalsIgnoreCase("privmsg")) {
//			channel = split[2];
//			
//			for(String s1 : split) {
//				if(!s1.equals(channel) && !s1.equals(command)) {
//					message = message + " " + s1;
//				}
//			}
//		}
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
