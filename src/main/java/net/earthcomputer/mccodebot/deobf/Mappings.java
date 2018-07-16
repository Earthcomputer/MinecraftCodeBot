package net.earthcomputer.mccodebot.deobf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Mappings {

	private Map<String, String> classes = new HashMap<>();
	private Map<String, String> fields = new HashMap<>();
	private Map<String, String> methods = new HashMap<>();

	private Mappings() {
	}

	public static Mappings parse(File mappingsFile) throws IOException {
		Mappings mappings = new Mappings();
		BufferedReader reader = new BufferedReader(new FileReader(mappingsFile));
		reader.lines().map(l -> l.split(" ")).forEach(line -> {
			switch (line[0]) {
			case "CL:":
				mappings.classes.put(line[2], line[1]);
				break;
			case "FD:":
				mappings.fields.put(dropPrefix(line[2]), dropPrefix(line[1]));
				break;
			case "MD:":
				mappings.methods.put(dropPrefix(line[3]), dropPrefix(line[1]));
				break;
			}
		});
		reader.close();
		return mappings;
	}

	private static String dropPrefix(String memberName) {
		int slashIndex = memberName.lastIndexOf('/');
		return memberName.substring(slashIndex + 1);
	}

	public String getClass(String srgName) {
		return classes.get(srgName);
	}

	public String getField(String srgName) {
		return fields.get(srgName);
	}

	public String getMethod(String srgName) {
		return methods.get(srgName);
	}

}
