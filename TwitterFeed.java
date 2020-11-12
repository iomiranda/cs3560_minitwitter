package cs3560_twitter;

import java.util.LinkedList;

public class TwitterFeed {

	private static TwitterFeed instance;
	private LinkedList<String> twitterFeed;
		
	private TwitterFeed() {
		twitterFeed = new LinkedList<>();
		twitterFeed.add("TWITTER FEED:");
	}
	
	public static TwitterFeed getInstance() {
		if(instance==null) {
			instance = new TwitterFeed();
		} else {
			System.out.println("Button Panel already has an instance");
		}
		return instance;
	}
	
	public void addTweet(String tweet) {
		twitterFeed.add(tweet);
	}
	
	public LinkedList<String> getTweetList() {
		return twitterFeed;
	}
	
}
