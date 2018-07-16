package net.earthcomputer.mccodebot.commands;

import net.earthcomputer.mccodebot.MinecraftCodeBot;
import net.earthcomputer.mccodebot.deobf.Block;
import net.earthcomputer.mccodebot.deobf.BlockProperties;
import net.earthcomputer.mccodebot.jarreader.JarReader;
import net.earthcomputer.mccodebot.jarreader.MinecraftInstance;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;

public class BlockCommand implements ICommand {

	private static void printBlockHelp(IChannel channel) {
		// @formatter:off
		String help = "```\n"
				+ "mcb/block <blockName> [attribute]\n"
				+ "If attribute is omitted, a few useful attributes are given.\n"
				+ "```";
		// @formatter:on
		channel.sendMessage(help);
	}

	@Override
	public void execute(String[] args, JarReader reader, IChannel channel) {
		if (args.length < 2) {
			printBlockHelp(channel);
			return;
		}

		IMessage message = channel.sendMessage("Starting Minecraft...");
		MinecraftInstance mc = reader.createMinecraftInstance();
		message.edit("Getting data...");

		Block block = Block.getBlockByName(mc, args[1]);
		if (block == null) {
			message.edit("No such block");
			return;
		}
		BlockProperties properties = block.getDefaultState().getProperties();

		String info = "";
		info += "Block ID: " + Block.getIdFromBlock(mc, block) + "\n";
		info += "Hardness: " + block.getHardness() + "\n";
		info += "Blast resistance: " + block.getBlastResistance() + "\n";
		info += "Full cube: " + properties.isFullCube() + "\n";
		info += "Normal cube: " + properties.isNormalCube() + "\n";
		info += "Block normal cube: " + properties.isBlockNormalCube() + "\n";
		info += "Opaque cube: " + properties.isOpaqueCube() + "\n";
		info += "Top solid: " + properties.isTopSolid() + "\n";
		info += "Causes suffocation: " + properties.causesSuffocation() + "\n";
		info += "Can provide power: " + properties.canProvidePower() + "\n";
		info += "Opacity: " + properties.getOpacity() + "\n";
		info += "Luminance: " + properties.getLuminance() + "\n";
		info += "Pushability: " + properties.getMobilityFlag() + "\n";
		message.edit(info);
	}

	@Override
	public String getName() {
		return "block";
	}

	@Override
	public String getHelp() {
		return MinecraftCodeBot.CMD_PREFIX + "block <help|blockName> <...>";
	}

}
