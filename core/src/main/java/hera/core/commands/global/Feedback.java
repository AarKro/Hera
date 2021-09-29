package hera.core.commands.global;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import hera.core.HeraUtil;
import hera.core.messages.MessageHandler;
import hera.core.messages.MessageSpec;
import hera.database.entities.Binding;
import hera.database.entities.Localisation;
import hera.database.types.BindingName;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.util.List;

import static hera.store.DataStore.STORE;

public class Feedback {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {

		String feedback = String.join(" ", params);

		List<Binding> bindings = STORE.bindings().forType(STORE.bindingTypes().forName(BindingName.FEEDBACK).get(0));
		if (!bindings.isEmpty()) {
			Binding binding = bindings.get(0);
			return HeraUtil.getClient().getGuildById(Snowflake.of(binding.getGuild()))
				.flatMap(g -> g.getChannelById(Snowflake.of(binding.getSnowflake()))
					.flatMap(chl -> {
						String userName = member.getUsername();
						Long userId = member.getId().asLong();
						String guildName = guild.getName();
						Long guildId = guild.getId().asLong();
						return MessageHandler.send((MessageChannel) chl, MessageSpec.getDefaultSpec(s ->  {
							Localisation response = HeraUtil.getLocalisation(LocalisationKey.COMMAND_FEEDBACK_RESPONSE, g);
							s.setDescription(String.format(response.getValue(), userName, userId, guildName, guildId, feedback));
						}));
					})
				).flatMap(x -> MessageHandler.send(channel, MessageSpec.getConfirmationSpec(s -> s.setDescription(HeraUtil.getLocalisation(LocalisationKey.COMMAND_FEEDBACK_SUBMIT, guild).getValue())))).then();
		} else {
			return MessageHandler.send(channel, MessageSpec.getDefaultSpec(s -> s.setDescription(HeraUtil.getLocalisation(LocalisationKey.COMMAND_FEEDBACK_ERROR_BINDING, guild).getValue()))).then();
		}
	}
}
