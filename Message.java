package cs3560_twitter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

public class Message {
	
	private int positiveWords;
	private static Message instance;
		
	private Message() {
		positiveWords = 0;
	}
	
	public static Message getInstance() {
		if(instance==null) {
			instance = new Message();
		} else {
			System.out.println("Message already has an instance");
		}
		return instance;
	}

	public int getPositiveCount() {
		return positiveWords;
	}
	
	public void pushTweet(String tweet) {
		String[] token = tweet.split(" ");
		for(String t : token) {
			analyze(t);
		}
	}
	
	public void analyze(String token) {
	    File file = new File("positive-words.txt"); 
	    try {
			Scanner sc = new Scanner(file);
			
		    while (sc.hasNextLine()) {
		    	String temp = token.toLowerCase();
		        if(temp.equals(sc.nextLine())) {
		        	positiveWords++;
		        }
		    } 

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
			
	}
	
}
