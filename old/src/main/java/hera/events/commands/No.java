package hera.events.commands;

import hera.events.eventSupplements.MessageSender;
import hera.events.eventSupplements.VoteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

public class No extends AbstractCommand {

    private static final Logger LOG = LoggerFactory.getLogger(No.class);

    private MessageSender ms;
    private VoteManager vm;

    // default constructor
    No() {
        super(null, 0, false);
        this.ms = MessageSender.getInstance();
        this.vm = VoteManager.getInstance();
    }

    @Override
    protected void commandBody(String[] params, MessageReceivedEvent e) {
        LOG.debug("Start of: No.execute");
        if (vm.isVoteActive()) {
            if (!vm.hasAlreadyVoted(e.getAuthor())) {
                vm.addUserToAlreadyVoted(e.getAuthor());
                vm.setCountNo(vm.getCountNo() + 1);
                LOG.info(e.getAuthor() + " voted no on the currently active vote");
            } else {
                ms.sendMessage(e.getChannel(), "", "You have already voted!");
                LOG.debug(e.getAuthor() + " tried to vote no on a vote that was already voted on");
            }
        } else {
            ms.sendMessage(e.getChannel(), "There is no active vote to vote on", "Type $vote <topic> to start a vote.");
            LOG.debug(e.getAuthor() + " tried to vote no on a non existing vote");
        }
        LOG.debug("End of: No.execute");
    }
}
