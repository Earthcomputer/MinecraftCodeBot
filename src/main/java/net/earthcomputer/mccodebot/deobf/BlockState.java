package net.earthcomputer.mccodebot.deobf;

import net.earthcomputer.mccodebot.jarreader.MinecraftInstance;

public class BlockState extends WrapperClass {

	protected BlockState(MinecraftInstance mc, Object instance) {
		super(mc, "net.minecraft.block.state.IBlockState", instance);
	}

	public BlockProperties getProperties() {
		return new BlockProperties(mc, instance);
	}

}
