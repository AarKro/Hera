package d4jbot.misc;

import java.util.ArrayList;
import java.util.List;

import sx.blah.discord.handle.obj.IUser;

public class VoteManager {

	private String voteTopic;
	private boolean voteActive;
	private int countYes;
	private int countNo;
	private List<IUser> alreadyVoted;
	private IUser voteOrganiser;

	// default constructor
	public VoteManager() {
		this.voteTopic = "";
		this.voteActive = false;
		this.countYes = 0;
		this.countNo = 0;
		this.alreadyVoted = new ArrayList<IUser>();
		this.voteOrganiser = null;
	}

	// functions
	public void startVote(String voteTopic, IUser voteOrganiser) {
		this.voteTopic = voteTopic;
		this.voteOrganiser = voteOrganiser;
		this.voteActive = true;
	}
	
	public void addUserToAlreadyVoted(IUser user) {
		this.alreadyVoted.add(user);
	}
	
	public boolean hasAlreadyVoted(IUser user) {
		return this.alreadyVoted.contains(user);
	}
	
	public void resetVote() {
		this.voteTopic = "";
		this.voteOrganiser = null;
		this.voteActive = false;
		this.countYes = 0;
		this.countNo = 0;
		this.alreadyVoted.clear();
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
