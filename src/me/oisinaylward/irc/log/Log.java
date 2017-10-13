package me.oisinaylward.irc.log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class Log {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
	
	public static void log(String title, String... args) {
		for(String s : args) {
			String date = dateFormat.format(Time.from(Instant.now()));
			System.out.println(date + " " + title.toUpperCase() + ": " + s);
		}
	}
	
	public static void error(Exception e) {
		String date = dateFormat.format(Time.from(Instant.now()));
		System.out.println(date + ": " + e.getMessage());
	}
	
}
