package org.gaslang.script;

import org.gaslang.script.ast.parser.FileParser;
import org.gaslang.script.parser.lexer.FileLexer;
import org.gaslang.script.parser.lexer.io.SourceReader;
import org.gaslang.script.visitor.SourceDrawer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class GasScript
{
	private static final HashMap<String, Script> CACHED_SCRIPTS = new HashMap<>();

	public static Script loadScript(String fileName) {
		return loadScript(fileName, new HashSet<>());
	}

	public static Script loadScript(String fileName, HashSet<String> options) {
		if (CACHED_SCRIPTS.containsKey(fileName)) return CACHED_SCRIPTS.get(fileName);

		String fileDirectory = String.format("%s.gs", fileName);
		
		File file = new File(fileDirectory);
		
		if (!file.exists()) throw new RuntimeException();

		FileParser parser = new FileLexer(file, new SourceReader().getInput(file)).toParser();

		Script script = parser.parse();

		if (options.contains("-s")) {
			SourceDrawer drawer = new SourceDrawer();
			script.accept(drawer);
			System.out.println(drawer.result());
		}

		try {
			script.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		CACHED_SCRIPTS.put(fileName, script);
		
		return script;
	}
}
