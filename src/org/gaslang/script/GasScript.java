package org.gaslang.script;

import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.ast.parser.FileParser;
import org.gaslang.script.exception.RunError;
import org.gaslang.script.parser.lexer.FileLexer;
import org.gaslang.script.parser.lexer.io.SourceReader;
import org.gaslang.script.visitor.SourceDrawer;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

public class GasScript
{
	private static final HashMap<String, Script> CACHED_SCRIPTS = new HashMap<>();

	public static Script loadScript(String fileName) {
		return loadScript(fileName, new HashSet<>());
	}

	public synchronized static Script loadScript(String fileName, HashSet<String> options) {
		if (CACHED_SCRIPTS.containsKey(fileName)) return CACHED_SCRIPTS.get(fileName);

		var fileDirectory = String.format("%s.gs", fileName);
		var file = new File(fileDirectory);
		
		if (!file.exists()) throw new RuntimeException();

		var scriptParser = new FileLexer(file, new SourceReader().getInput(file)).toParser();
		var script = scriptParser.parse();

		if (options.contains("-s")) {
			var drawer = new SourceDrawer();
			script.accept(drawer);
			System.out.println(drawer.result());
		}

		try {
			script.execute();
		} catch (Exception runError) {
			System.err.println(runError.getMessage());
			ScriptAPI.printStackTrace(script.getRuntime(), Optional.empty());
			if (!(runError instanceof RunError)) {
				return script;
			}
			throw new RuntimeException(runError);
		}
		
		CACHED_SCRIPTS.put(fileName, script);
		
		return script;
	}
}
