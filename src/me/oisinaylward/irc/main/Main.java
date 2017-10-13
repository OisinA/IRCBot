package me.oisinaylward.irc.main;

import me.oisinaylward.irc.IRC;

public class Main {
	
	public static void main(String[] args) {
		new IRC("irc.freenode.net", 6667).start();
	}
	
}
