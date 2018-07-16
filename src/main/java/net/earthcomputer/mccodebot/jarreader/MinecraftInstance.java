package net.earthcomputer.mccodebot.jarreader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import net.earthcomputer.mccodebot.deobf.Mappings;

public class MinecraftInstance {

	private ClassLoader classLoader;
	private Mappings mappings;

	public MinecraftInstance(ClassLoader classLoader, Mappings mappings) {
		this.classLoader = classLoader;
		this.mappings = mappings;
	}

	public Mappings getMappings() {
		return mappings;
	}

	public Class<?> getClass(String clazz) {
		clazz = clazz.replace('.', '/');
		try {
			return classLoader.loadClass(mappings.getClass(clazz));
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found: " + clazz + " (obf = " + mappings.getClass(clazz) + ")");
			return null;
		}
	}

	public Object getField(String clazz, String field, Object instance) {
		Field f;
		try {
			f = getClass(clazz).getDeclaredField(mappings.getField(field));
		} catch (Exception e) {
			System.err.println("Field not found: " + clazz + "." + field + " (obf = " + mappings.getField(field) + ")");
			return null;
		}
		f.setAccessible(true);
		try {
			return f.get(instance);
		} catch (Exception e) {
			System.err.println("Exception getting field " + clazz + "." + field);
			e.printStackTrace();
			return null;
		}
	}

	public Object invokeMethod(String clazz, String method, Object[] argClasses, Object instance, Object... args) {
		Method m;
		try {
			m = getClass(clazz).getDeclaredMethod(mappings.getMethod(method), Arrays.stream(argClasses)
					.map(c -> c instanceof Class ? (Class<?>) c : getClass((String) c)).toArray(Class[]::new));
		} catch (Exception e) {
			System.err.println("Method not found: " + clazz + "." + method + "(obf = " + mappings.getMethod(method));
			return null;
		}
		m.setAccessible(true);
		try {
			return m.invoke(instance, args);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getCause());
		} catch (Exception e) {
			System.err.println("Exception invoking method " + clazz + "." + method);
			e.printStackTrace();
			return null;
		}
	}

}
