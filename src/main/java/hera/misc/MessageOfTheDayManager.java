package hera.misc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BotSettings;
import hera.enums.BoundChannel;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

public class MessageOfTheDayManager {

	private static final Logger LOG = LoggerFactory.getLogger(MessageOfTheDayManager.class);
	
	private static MessageOfTheDayManager instance;
	
	public static MessageOfTheDayManager getInstance() {
		if (instance == null) instance = new MessageOfTheDayManager(MessageSender.getInstance(), ClientManager.getInstance());
		return instance;
	}

	private MessageSender ms;
	private ClientManager cm;
	private Random rnd;
	private DateTimeFormatter dtf;
	private ArrayList<String> messagesOfTheDay;
	private String lastPosted;
	
	// default constructor
	public MessageOfTheDayManager() { }

	// constructor
	private MessageOfTheDayManager(MessageSender ms, ClientManager cm) {
		this.ms = ms;
		this.cm = cm;
		this.rnd = new Random();
		this.dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		this.lastPosted = BotSettings.LAST_MOTD.getPropertyValue();
		
		LOG.debug("Loading list containing messages of the day");
		ArrayList<String> messagesOfTheDay = new ArrayList<String>();
		messagesOfTheDay.add("Banging your head against a wall burns 150 calories an hour.");
		messagesOfTheDay.add("Approximately 40,000 Americans are injured by toilets each year.");
		messagesOfTheDay.add("\"Almost\" is the longest word in English with all the letters in alphabetical order.");
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
						LOG.debug("Start of: MessageOfTheDayManager.Thread");
						while(BoundChannel.ANNOUNCEMENTS.getBoundChannel() == null) {
							Thread.sleep(5000);
						}
						
						LOG.info("Writing Message of the day");
						writteMessageOfTheDay();
					
						LOG.info("Putting MessageOfTheDayManager.Thread to sleep");
						Thread.sleep(3600000);	
						LOG.info("Waking MessageOfTheDayManager.Thread up from sleep");
					} catch (Exception e) {
						LOG.error("Exception in MessageOfTheDayManager.Thread");
						LOG.error(e.getMessage() + " : " + e.getCause());
					} finally {
						LOG.debug("End of: MessageOfTheDayManager.Thread");
					}
				}
			}
			
		};

		Thread thread = new Thread(runnable);
		LOG.info("MessageOfTheDayManager.Thread created");
		thread.start();
		LOG.info("MessageOfTheDayManager.Thread started");
	}

	public void writteMessageOfTheDay() {
		LOG.debug("Start of: MessageOfTheDayManager.writteMessageOfTheDay");
		String today = LocalDateTime.now().format(dtf);
		if(!lastPosted.equals(today)){
			
			LOG.debug("Deleting last message of the day e.g. message of yesterday");
			deleteLastMessageOfTheDay();
			
			String motd = messagesOfTheDay.get(rnd.nextInt(messagesOfTheDay.size()));
			ms.sendMessage(BoundChannel.ANNOUNCEMENTS.getBoundChannel(), "Message of the day:", motd);
			LOG.info("New message of the day: " + motd);
			
			this.lastPosted = today;
			BotSettings.LAST_MOTD.setPropertyValue(today);
		}
		LOG.debug("End of: MessageOfTheDayManager.writteMessageOfTheDay");
	}

	public void setMessageOfTheDay(MessageReceivedEvent e, String message) {
		LOG.debug("Start of: MessageOfTheDayManager.setMessageOfTheDay");
		
		LOG.debug("Deleting last message of the day e.g. message of yesterday");
		deleteLastMessageOfTheDay();
		ms.sendMessage(BoundChannel.ANNOUNCEMENTS.getBoundChannel(), "Message of the day:", message);

		LOG.debug("End of: MessageOfTheDayManager.setMessageOfTheDay");
	}
	
	public void deleteLastMessageOfTheDay() {
		LOG.debug("Start of: MessageOfTheDayManager.deleteLastMessageOfTheDay");
		if(!BoundChannel.ANNOUNCEMENTS.getBoundChannel().getFullMessageHistory().isEmpty()) {
			List<IMessage> messages = BoundChannel.ANNOUNCEMENTS.getBoundChannel().getFullMessageHistory().stream()
																				  .filter(f -> f.getAuthor() == cm.getiDiscordClient().getOurUser())
																				  .collect(Collectors.toList());
			if(!messages.isEmpty()) {
				messages.get(0).delete();
				LOG.info("Message of the day deleted");
			} else {
				LOG.info("There is no message of the day to delete");
			}
		}
		LOG.debug("End of: MessageOfTheDayManager.deleteLastMessageOfTheDay");
	}
}
