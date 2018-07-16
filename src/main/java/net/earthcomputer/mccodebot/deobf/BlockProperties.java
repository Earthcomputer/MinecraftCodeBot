package net.earthcomputer.mccodebot.deobf;

import net.earthcomputer.mccodebot.jarreader.MinecraftInstance;

public class BlockProperties extends WrapperClass {

	protected BlockProperties(MinecraftInstance mc, Object instance) {
		super(mc, "net.minecraft.block.state.IBlockProperties", instance);
	}

	public boolean isFullCube() {
		return (Boolean) invoke("func_185917_h", new Object[0]);
	}

	public boolean isNormalCube() {
		return (Boolean) invoke("func_185915_l", new Object[0]);
	}

	public boolean isBlockNormalCube() {
		return (Boolean) invoke("func_185898_k", new Object[0]);
	}

	public boolean isOpaqueCube() {
		return (Boolean) invoke("func_185914_p", new Object[0]);
	}

	public boolean isTopSolid() {
		return (Boolean) invoke("func_185896_q", new Object[0]);
	}

	public boolean causesSuffocation() {
		return (Boolean) invoke("func_191058_s", new Object[0]);
	}

	public boolean canProvidePower() {
		return (Boolean) invoke("func_185897_m", new Object[0]);
	}

	public int getOpacity() {
		return (Integer) invoke("func_185891_c", new Object[0]);
	}

	public int getLuminance() {
		return (Integer) invoke("func_185906_d", new Object[0]);
	}

	public Object getMobilityFlag() {
		return invoke("func_185905_o", new Object[0]);
	}

}
