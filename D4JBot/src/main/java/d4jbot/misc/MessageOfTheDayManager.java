package d4jbot.misc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

public class MessageOfTheDayManager {

	private MessageSender ms;
	private ClientManager cm;
	private Random rnd;
	private DateFormat df;
	private ArrayList<String> messagesOfTheDay;
	
	// default constructor
	public MessageOfTheDayManager() { }

	// constructor
	public MessageOfTheDayManager(MessageSender ms, ClientManager cm) {
		this.ms = ms;
		this.cm = cm;
		this.rnd = new Random();
		this.df = new SimpleDateFormat("dd.MM.yyyy");
		
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
					writteMessageOfTheDay();
					try {
						Thread.sleep(3600000);
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
		
		String today = df.format(new Date());
		
		for(IGuild iGuild : cm.getiDiscordClient().getGuilds()) {
			
			List<IChannel> iChannels = iGuild.getChannelsByName("general");
			if(!iChannels.isEmpty()) {
			
				List<IMessage> iMessages = iChannels.get(iChannels.size() - 1).getMessageHistory();
				for(IMessage m : iMessages) {
					System.out.println(m.getContent());
				}
				iMessages = iMessages.stream()
									 .filter(f -> f.getAuthor() != cm.getiDiscordClient().getOurUser())
									 .filter(f -> !f.getContent().startsWith("Message of the day: "))
									 .collect(Collectors.toList());

				if(!iMessages.isEmpty()) {
					System.out.println(iMessages.get(0).getContent());
					if(!iMessages.get(0).getContent().endsWith(today)) {
						ms.sendMessage(iChannels.get(iChannels.size() - 1), true, "Message of the day: \n" + messagesOfTheDay.get(rnd.nextInt(messagesOfTheDay.size())) + "\n" + today);
					}
				} else {
					ms.sendMessage(iChannels.get(iChannels.size() - 1), true, "Message of the day: \n" + messagesOfTheDay.get(rnd.nextInt(messagesOfTheDay.size())) + "\n" + today);
				}
				
			} else System.out.println("No channel \"general\" found.");
		}
	}
	
	public void setMessageOfTheDay(MessageReceivedEvent e, String message) {
		String today = df.format(new Date());

		List<IChannel> iChannels = e.getGuild().getChannelsByName("general");
		if(!iChannels.isEmpty()) {
			ms.sendMessage(iChannels.get(0), true, "Message of the day: \n" + message + "\n" + today);
		} else ms.sendMessage(e.getChannel(), true, "No channel \"general\" found.");
	}
}
