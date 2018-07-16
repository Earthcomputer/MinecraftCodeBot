package net.earthcomputer.mccodebot.jarreader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import net.earthcomputer.mccodebot.deobf.Mappings;

public class JarReader {

	public static List<String> listMinecraftVersions() {
		List<String> versions = new ArrayList<>();

		File file = new File("versions");
		if (file.isDirectory()) {
			for (File jar : file.listFiles()) {
				if (jar.isFile() && jar.getName().endsWith(".jar")) {
					String mcVersion = jar.getName().substring(0, jar.getName().length() - 4);
					if (new File("versions", mcVersion + ".srg").isFile())
						versions.add(mcVersion);
				}
			}
		}

		return versions;
	}

	public static JarReader forMinecraftVersion(String mcVersion) {
		File jarFile = new File("versions", mcVersion + ".jar");
		if (!jarFile.isFile())
			return null;
		File mappingsFile = new File("versions", mcVersion + ".srg");
		if (!mappingsFile.isFile())
			return null;

		try {
			JarReader reader = new JarReader(jarFile);
			reader.readMappings(mappingsFile);
			return reader;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private File jarFile;
	private Mappings mappings;

	public JarReader(File file) {
		this.jarFile = file;
	}

	public void readMappings(File mappingsFile) throws IOException {
		this.mappings = Mappings.parse(mappingsFile);
	}

	public MinecraftInstance createMinecraftInstance() {
		String cp = System.getProperty("java.class.path");
		String[] elements = cp.split(File.pathSeparator);
		if (elements.length == 0)
			elements = new String[] { "" };
		URL[] urls = new URL[elements.length + 1];
		for (int i = 0; i < elements.length; i++) {
			try {
				urls[i] = new File(elements[i]).toURI().toURL();
			} catch (MalformedURLException ignore) {
			}
		}
		try {
			urls[elements.length] = jarFile.toURI().toURL();
		} catch (MalformedURLException ignore) {
		}
		URLClassLoader ucl = new URLClassLoader(urls);

		MinecraftInstance mc = new MinecraftInstance(ucl, mappings);
		mc.invokeMethod("net.minecraft.init.Bootstrap", "func_151354_b", new String[0], null);
		return mc;
	}

}
