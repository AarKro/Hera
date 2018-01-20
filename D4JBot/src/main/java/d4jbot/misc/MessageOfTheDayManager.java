package d4jbot.misc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import d4jbot.enums.BotSettings;
import d4jbot.enums.BoundChannel;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class MessageOfTheDayManager {

	private MessageSender ms;
	private ClientManager cm;
	private Random rnd;
	private DateTimeFormatter dtf;
	private ArrayList<String> messagesOfTheDay;
	private String lastPosted;
	
	// default constructor
	public MessageOfTheDayManager() { }

	// constructor
	public MessageOfTheDayManager(MessageSender ms, ClientManager cm) {
		this.ms = ms;
		this.cm = cm;
		this.rnd = new Random();
		this.dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		this.lastPosted = BotSettings.LAST_MOTD.getPropertyValue();
		
		ArrayList<String> messagesOfTheDay = new ArrayList<String>();
		messagesOfTheDay.add("Banging your head against a wall burns 150 calories an hour.");
		messagesOfTheDay.add("Approximately 40,000 Americans are injured by toilets each year.");
		messagesOfTheDay.add("Almost” is the longest word in English with all the letters in alphabetical order.");
		messagesOfTheDay.add("There is enough sperm in one single man to impregnate every woman on earth.");
		messagesOfTheDay.add("It is impossible to sneeze with your eyes open.");
		messagesOfTheDay.add("All swans in England belong to the queen.");
		messagesOfTheDay.add("The “hashtag” key on your keyboard (#) is called an octotroph.");
		messagesOfTheDay.add("At any one time about 0.7% of the world’s population is drunk.");
		messagesOfTheDay.add("For every non-porn webpage, there are five porn pages.");
		messagesOfTheDay.add("The Falkland Isles has over 700000 sheep (350 per person).");
		messagesOfTheDay.add("M&M’s actually stands for “Mars & Murrie’s,” the last names of the candy’s founders.");
		messagesOfTheDay.add("It is estimated that millions of trees are planted by forgetful squirrels that buried their nuts.");
		messagesOfTheDay.add("Grapes explode when you put them in the microwave.");
		messagesOfTheDay.add("You can’t say happiness without saying penis.");
		messagesOfTheDay.add("To travel from North Korea to Norway, you only have to go through one country.");
		messagesOfTheDay.add("From a botanical standpoint, strawberries and raspberries aren't berries, but bananas and avocados are.");
		messagesOfTheDay.add("Bush did 9/11.");
		messagesOfTheDay.add("A single cloud can weight more than 1 million pounds.");
		messagesOfTheDay.add("Every year more than 2500 left-handed people are killed from using right-handed products.");
		messagesOfTheDay.add("Cows kill more people than sharks do.");
		messagesOfTheDay.add("You can’t kill yourself by holding your breath.");
		
		this.messagesOfTheDay = messagesOfTheDay;
		
		Runnable runnable = new Runnable() {

			public void run() {
				while (true) {
					try {
						while(BoundChannel.ANNOUNCEMENTS.getBoundChannel() == null) {
							Thread.sleep(5000);
						}
						writteMessageOfTheDay();
					
						Thread.sleep(3600000);	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		};

		Thread thread = new Thread(runnable);
		thread.start();
	}

	public void writteMessageOfTheDay() {
		String today = LocalDateTime.now().format(dtf);
		if(!lastPosted.equals(today)){
			
			deleteLastMessageOfTheDay();
			
			ms.sendMessage(BoundChannel.ANNOUNCEMENTS.getBoundChannel(), "Message of the day: \n" + messagesOfTheDay.get(rnd.nextInt(messagesOfTheDay.size())));
			this.lastPosted = today;
			BotSettings.LAST_MOTD.setPropertyValue(today);
		}
	}

	public void setMessageOfTheDay(MessageReceivedEvent e, String message) {
		deleteLastMessageOfTheDay();
		ms.sendMessage(BoundChannel.ANNOUNCEMENTS.getBoundChannel(), "Message of the day: \n" + message);
	}
	
	public void deleteLastMessageOfTheDay() {
		if(!BoundChannel.ANNOUNCEMENTS.getBoundChannel().getFullMessageHistory().isEmpty()) {
			List<IMessage> messages = BoundChannel.ANNOUNCEMENTS.getBoundChannel().getFullMessageHistory().stream()
																				  .filter(f -> f.getAuthor() == cm.getiDiscordClient().getOurUser())
																				  .collect(Collectors.toList());
			if(!messages.isEmpty()) messages.get(0).delete();
		}
	}
}
