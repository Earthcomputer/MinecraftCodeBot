package net.earthcomputer.mccodebot.deobf;

import net.earthcomputer.mccodebot.jarreader.MinecraftInstance;

public abstract class WrapperClass {

	protected final MinecraftInstance mc;
	protected final String className;
	protected final Object instance;

	protected WrapperClass(MinecraftInstance mc, String className, Object instance) {
		this.mc = mc;
		this.className = className;
		this.instance = instance;
	}

	protected Object get(String fieldName) {
		return mc.getField(className, fieldName, instance);
	}

	protected Object invoke(String methodName, Object[] params, Object... args) {
		return mc.invokeMethod(className, methodName, params, instance, args);
	}

}
