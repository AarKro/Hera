package hera.events;

import hera.events.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

abstract public class Command {

	private static Map<String, Command> instances;

	public static Command getInstance(String className) {
		if (!instances.containsKey(className)) {
			try {
				instances.put(className, (Command) ((Object) Class.forName(className)));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return instances.get(className);
	}

	private List<String> permissions;
	private int numberOfParameters;
	private boolean hasMessageParameter;

	protected Command(List<String> permissions, int numberOfParameters, boolean hasMessageParameter) {
		this.permissions = permissions;
		this.numberOfParameters = numberOfParameters;
		this.hasMessageParameter = hasMessageParameter;
	}

	public void execute(MessageReceivedEvent e) {
		if(checkUserPermissions(e.getAuthor().getPermissionsForGuild(e.getGuild()), e.getAuthor().getRolesForGuild(e.getGuild()), e.getGuild())) {
			String[] params = extractCommandParameters(e.getMessage().getContent());

			if(params != null) commandBody(params, e);
			else MessageSender.getInstance().sendMessage(e.getChannel(), "Invalid usage", "Expected " + numberOfParameters + " parameters");

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

		if(numberOfParameters == 999) numberOfParameters = splitMessage.length - 1;

		if(splitMessage.length - 1 < numberOfParameters || (splitMessage.length - 1 > numberOfParameters && !hasMessageParameter)) {
			return null;
		}

		String[] params = new String[numberOfParameters];

		for(int i = 1; i < numberOfParameters; i++) {
			params[i] = splitMessage[i];
		}

		if(hasMessageParameter) {
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

	protected boolean hasMessageParameter() {
		return hasMessageParameter;
	}

	protected void setHasMessageParameter(boolean hasMessageParameter) {
		this.hasMessageParameter = hasMessageParameter;
	}

}
