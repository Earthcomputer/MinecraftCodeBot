package net.earthcomputer.mccodebot.commands;

import net.earthcomputer.mccodebot.jarreader.JarReader;
import sx.blah.discord.handle.obj.IChannel;

public interface ICommand {

	void execute(String[] args, JarReader reader, IChannel channel);
	
	String getName();
	
	String getHelp();
	
	default boolean requireReader() {
		return true;
	}
	
}
