package net.earthcomputer.mccodebot.commands;

import net.earthcomputer.mccodebot.MinecraftCodeBot;
import net.earthcomputer.mccodebot.jarreader.JarReader;
import sx.blah.discord.handle.obj.IChannel;

public class MinecraftVersionsCommand implements ICommand {

	@Override
	public void execute(String[] args, JarReader reader, IChannel channel) {
		String response = "The supported Minecraft versions are:";
		for (String version : JarReader.listMinecraftVersions()) {
			response += "\n- " + version;
		}
		channel.sendMessage(response);
	}
	
	@Override
	public String getName() {
		return "mcversions";
	}

	@Override
	public String getHelp() {
		return MinecraftCodeBot.CMD_PREFIX + "mcversions: list the supported Minecraft versions";
	}
	
	@Override
	public boolean requireReader() {
		return false;
	}

}
