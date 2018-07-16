package net.earthcomputer.mccodebot.deobf;

import net.earthcomputer.mccodebot.jarreader.MinecraftInstance;

public class Block extends WrapperClass {

	protected Block(MinecraftInstance mc, Object instance) {
		super(mc, "net.minecraft.block.Block", instance);
	}

	public static Block getBlockByName(MinecraftInstance mc, String name) {
		Object block = mc.invokeMethod("net.minecraft.block.Block", "func_149684_b", new Object[] { String.class },
				null, name);
		return block == null ? null : new Block(mc, block);
	}

	public static int getIdFromBlock(MinecraftInstance mc, Block block) {
		return (Integer) mc.invokeMethod("net.minecraft.block.Block", "func_149682_b",
				new Object[] { "net.minecraft.block.Block" }, null, block.instance);
	}

	public BlockState getDefaultState() {
		return new BlockState(mc, invoke("func_176223_P", new Object[0]));
	}

	public float getHardness() {
		return (Float) get("field_149782_v");
	}

	public float getBlastResistance() {
		return (Float) invoke("func_149638_a", new Object[] { "net.minecraft.entity.Entity" }, new Object[] { null })
				* 5;
	}

}
