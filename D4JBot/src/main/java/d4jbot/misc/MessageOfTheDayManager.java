package d4jbot.misc;

import java.util.Timer;

public class MessageOfTheDayManager {

	private MessageSender ms;
	
	// default constructor
	public MessageOfTheDayManager() { }

	// constructor
	public MessageOfTheDayManager(MessageSender ms) {
		this.ms = ms;
		Runnable runnable = new Runnable() {

			public void run() {
				while (true) {
					writteMessageOfTheDay();
					try {
						Thread.sleep(3600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}

	public void writteMessageOfTheDay() {
		// - read messages of channel general-#general
		// - filter for messages of Hera and for messages starting with "Message of the day:"
		// - check post date of found messages
		// - if post date is older then current date, post new message
		// - else sleep thread for 1 hour
	}
}
