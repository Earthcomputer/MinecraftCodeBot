package net.earthcomputer.mccodebot.commands;

import net.earthcomputer.mccodebot.MinecraftCodeBot;
import net.earthcomputer.mccodebot.jarreader.JarReader;
import sx.blah.discord.handle.obj.IChannel;

public class LoadMinecraftVersionCommand implements ICommand {

	@Override
	public void execute(String[] args, JarReader reader, IChannel channel) {
		if (args.length < 2) {
			MinecraftCodeBot.printHelp(channel);
			return;
		}
		JarReader jr = JarReader.forMinecraftVersion(args[1]);
		if (jr == null) {
			channel.sendMessage("Couldn't load that Minecraft version. Run `" + MinecraftCodeBot.CMD_PREFIX
					+ "mcversions` for a list of supported versions.");
			return;
		}
		MinecraftCodeBot.jarReaderByGuild.put(channel.getGuild().getLongID(), jr);
		channel.sendMessage("Loaded Minecraft version " + args[1]);
	}

	@Override
	public String getName() {
		return "loadmc";
	}

	@Override
	public String getHelp() {
		return MinecraftCodeBot.CMD_PREFIX
				+ "loadmc <version>: load a Minecraft version. Subsequent operations will use that Minecraft version";
	}

	@Override
	public boolean requireReader() {
		return false;
	}

}
