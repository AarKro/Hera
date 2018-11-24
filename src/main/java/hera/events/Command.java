package hera.events;

import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

import java.util.EnumSet;
import java.util.List;

abstract public class Command {

	private List<String> permissions;
	private int numberOfParameters;

	protected Command(List<String> permissions, int numberOfParameters) {
		this.permissions = permissions;
		this.numberOfParameters = numberOfParameters;
	}

	public void execute(MessageReceivedEvent e) {
		if(checkUserPermissions(e.getAuthor().getPermissionsForGuild(e.getGuild()), e.getAuthor().getRolesForGuild(e.getGuild()), e.getGuild())) {
			String[] params = extractCommandParameters(e.getMessage().getContent());

			if(params != null) commandBody(params, e);
			else MessageSender.getInstance().sendMessage(e.getChannel(), "Wrong usage", "There weren't enough parameters to execute the command");

		} else MessageSender.getInstance().sendMessage(e.getChannel(), "Permission denied", "You don't possess the rights to execute this command");
	}

	private boolean checkUserPermissions(EnumSet<Permissions> userPermissions, List<IRole> userRoles, IGuild guild) {
		if(permissions == null) return true;

		boolean hasExecutePermission;
		for(String permission : permissions) {
			switch(permission) {
				case "ADMINISTRATOR":
					hasExecutePermission = userPermissions.contains(Permissions.ADMINISTRATOR);
					break;
				default:
					hasExecutePermission = userRoles.contains(guild.getRolesByName(permission).get(0));
			}

			if(hasExecutePermission) return true;
		}

		return false;
	}

	private String[] extractCommandParameters(String message) {
		String[] splitMessage = message.split(" ");

		if(splitMessage.length - 1 < numberOfParameters) {
			return null;
		}

		String[] params = new String[numberOfParameters];

		for(int i = 1; i < numberOfParameters; i++) {
			params[i] = splitMessage[i];
		}

		if(splitMessage.length - 1 > numberOfParameters) {
			String longParam = "";
			for(int i = numberOfParameters; i < splitMessage.length; i++) {
				longParam = longParam + " " + splitMessage[i];
			}
			params[numberOfParameters - 1] = longParam.trim();
		}

		return params;
	}

	abstract protected void commandBody(String[] params, MessageReceivedEvent e);

	protected List<String> getPermissions() {
		return permissions;
	}

	protected void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	protected int getNumberOfParameters() {
		return numberOfParameters;
	}

	protected void setNumberOfParameters(int numberOfParameters) {
		this.numberOfParameters = numberOfParameters;
	}
}
