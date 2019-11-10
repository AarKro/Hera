package hera.events.eventSupplements;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sx.blah.discord.handle.obj.IUser;

public class VoteManager {
	
	private static final Logger LOG = LoggerFactory.getLogger(VoteManager.class);
	
	private static VoteManager instance;
	
	public static VoteManager getInstance() {
		if (instance == null) instance = new VoteManager();
		return instance;
	}
	
	private String voteTopic;
	private boolean voteActive;
	private int countYes;
	private int countNo;
	private List<IUser> alreadyVoted;
	private IUser voteOrganiser;

	private VoteManager() {
		this.voteTopic = "";
		this.voteActive = false;
		this.countYes = 0;
		this.countNo = 0;
		this.alreadyVoted = new ArrayList<IUser>();
		this.voteOrganiser = null;
	}

	public void startVote(String voteTopic, IUser voteOrganiser) {
		LOG.debug("Start of: VoteManager.startVote");
		this.voteTopic = voteTopic;
		this.voteOrganiser = voteOrganiser;
		this.voteActive = true;
		LOG.debug("End of: VoteManager.startVote");
	}
	
	public void addUserToAlreadyVoted(IUser user) {
		LOG.debug("End of: VoteManager.addUserToAlreadyVoted");
		this.alreadyVoted.add(user);
		LOG.debug("End of: VoteManager.addUserToAlreadyVoted");
	}
	
	public boolean hasAlreadyVoted(IUser user) {
		LOG.debug("End of: VoteManager.hasAlreadyVoted");
		LOG.debug("End of: VoteManager.hasAlreadyVoted");
		return this.alreadyVoted.contains(user);
	}
	
	public void resetVote() {
		LOG.debug("End of: VoteManager.resetVote");
		this.voteTopic = "";
		this.voteOrganiser = null;
		this.voteActive = false;
		this.countYes = 0;
		this.countNo = 0;
		this.alreadyVoted.clear();
		LOG.debug("End of: VoteManager.resetVote");
	}
	
	// getters & setters
	public String getVoteTopic() {
		return voteTopic;
	}

	public void setVoteTopic(String voteTopic) {
		this.voteTopic = voteTopic;
	}

	public boolean isVoteActive() {
		return voteActive;
	}

	public void setVoteActive(boolean voteActive) {
		this.voteActive = voteActive;
	}

	public int getCountYes() {
		return countYes;
	}

	public void setCountYes(int countYes) {
		this.countYes = countYes;
	}

	public int getCountNo() {
		return countNo;
	}

	public void setCountNo(int countNo) {
		this.countNo = countNo;
	}

	public List<IUser> getAlreadyVoted() {
		return alreadyVoted;
	}

	public void setAlreadyVoted(List<IUser> alreadyVoted) {
		this.alreadyVoted = alreadyVoted;
	}
	
	public IUser getVoteOrganiser() {
		return voteOrganiser;
	}

	public void setVoteOrganiser(IUser voteOrganiser) {
		this.voteOrganiser = voteOrganiser;
	}
}
