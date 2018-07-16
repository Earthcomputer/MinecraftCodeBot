package net.earthcomputer.mccodebot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import net.earthcomputer.mccodebot.commands.BlockCommand;
import net.earthcomputer.mccodebot.commands.ICommand;
import net.earthcomputer.mccodebot.commands.LoadMinecraftVersionCommand;
import net.earthcomputer.mccodebot.commands.MinecraftVersionsCommand;
import net.earthcomputer.mccodebot.jarreader.JarReader;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.StatusType;

public class MinecraftCodeBot {

	private static final Logger LOGGER = Logger.getLogger("Minecraft Code Bot");
	public static final String CMD_PREFIX = "mcb/";
	private static List<ICommand> commands = new ArrayList<>();

	static {
		commands.add(new MinecraftVersionsCommand());
		commands.add(new LoadMinecraftVersionCommand());
		commands.add(new BlockCommand());
	}

	public static Map<Long, JarReader> jarReaderByGuild = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		ClientBuilder cb = new ClientBuilder();
		try {
			String token = "";
			FileInputStream in = new FileInputStream(new File("discord_access_token.txt"));
			int c;
			while ((c = in.read()) != -1) {
				token += (char) c;
			}
			cb.withToken(token);
			in.close();
		} catch (IOException e) {
			System.err.println("Discord access token not found");
			return;
		}
		cb.setPresence(StatusType.DND, ActivityType.LISTENING, CMD_PREFIX + "help");
		IDiscordClient client = cb.login();
		EventDispatcher ed = client.getDispatcher();
		ed.registerListener(MinecraftCodeBot.class);

		Scanner scan = new Scanner(System.in);
		while (true) {
			if (scan.nextLine().equalsIgnoreCase("stop"))
				break;
		}
		scan.close();
		client.logout();
	}

	public static void printHelp(IChannel channel) {
		// @formatter:off
		String help = "```\n"
				+ CMD_PREFIX + "help: print this help message.\n";
		// @formatter:on
		for (ICommand command : commands) {
			help += command.getHelp() + "\n";
		}
		help += "```";
		channel.sendMessage(help);
	}

	@EventSubscriber
	public static void onMessage(MessageReceivedEvent event) {
		if (event.getAuthor().isBot())
			return;

		String msg = event.getMessage().getContent();
		String[] args = msg.split(" ");
		if (args[0].startsWith(CMD_PREFIX)) {
			LOGGER.info("Running command: " + msg);
			LOGGER.info("In guild: " + event.getGuild().getName());
			LOGGER.info("By: " + event.getMessage().getAuthor().getName() + "#"
					+ event.getMessage().getAuthor().getDiscriminator());

			boolean matchedCommand = false;
			String commandName = args[0].substring(CMD_PREFIX.length());
			for (ICommand command : commands) {
				if (command.getName().equals(commandName)) {
					matchedCommand = true;
					JarReader reader = jarReaderByGuild.get(event.getGuild().getLongID());
					if (reader == null && command.requireReader()) {
						event.getChannel().sendMessage("You haven't loaded a Minecraft version");
						return;
					}
					command.execute(args, reader, event.getChannel());
				}
			}
			if (!matchedCommand) {
				printHelp(event.getChannel());
			}
		}
	}

}
