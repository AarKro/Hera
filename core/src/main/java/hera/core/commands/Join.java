package hera.core.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.MessageChannel;
import hera.core.HeraUtil;
import hera.core.music.HeraAudioManager;
import hera.database.entities.mapped.Localisation;
import hera.database.types.LocalisationKey;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.List;

public class Join {
	public static Mono<Void> execute(MessageCreateEvent event, Guild guild, Member member, MessageChannel channel, List<String> params) {
		Localisation local = HeraUtil.getLocalisation(LocalisationKey.COMMAND_JOIN, guild);
		return member.getVoiceState()
				.flatMap(VoiceState::getChannel)
				.flatMap(vChannel -> vChannel.join(spec -> spec.setProvider(HeraAudioManager.getProvider(guild))))
				.doOnNext(vc -> HeraAudioManager.addVC(guild, vc))
				.switchIfEmpty(channel.createMessage(spec -> spec.setEmbed(embed -> {
					embed.setColor(Color.ORANGE);
					embed.setDescription(local.getValue());
				})).then(Mono.empty()))
				.then();
	}
}
