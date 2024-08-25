package org.gaslang.script;

import java.io.File;
import java.util.HashMap;

import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.ast.parser.FileParser;
import org.gaslang.script.parser.lexer.FileLexer;
import org.gaslang.script.parser.lexer.io.SourceReader;
import org.gaslang.script.visitor.AsmDrawer;
import org.gaslang.script.visitor.SourceDrawer;

public class GasScript
{
	private static final HashMap<String, Script> CACHED_SCRIPTS = new HashMap<>();

	private static String ROOT_DIRECTORY;
	
	public static void main(String... strings) {
		System.out.println(String.format("Loading GasScript v%s...\n\r", ScriptAPI.VERSION));

		ROOT_DIRECTORY = "";
		
		long start = System.currentTimeMillis();
		
		loadScript("test");
		
		System.out.println(String.format("\n\rFile compiled in %s ms", System.currentTimeMillis()-start));
	}
	
	public static Script loadScript(String fileName) {
		if (CACHED_SCRIPTS.containsKey(fileName)) return CACHED_SCRIPTS.get(fileName);

		long start = System.currentTimeMillis();
		
		String fileDirectory = String.format("%s%s.gs", ROOT_DIRECTORY, fileName);
		
		File file = new File(fileDirectory);
		
		if (file == null || !file.exists()) throw new RuntimeException();
		
		//System.out.println(String.format("\n\rFile found in %s ms", System.currentTimeMillis()-start));

		
		FileParser parser = new FileLexer(file, new SourceReader().getInput(file)).toParser();
		
		//System.out.println(String.format("\n\rParser created in %s ms", System.currentTimeMillis()-start));

		Script script = parser.parse();
		
		//System.out.println(String.format("\n\rFile parsed in %s ms", System.currentTimeMillis()-start));

		
		SourceDrawer drawer = new SourceDrawer();
		
		script.accept(drawer);
		
		System.out.println(drawer.result());
		
		try {
			script.execute();
			
			//System.out.println(String.format("\n\rFile executed in %s ms", System.currentTimeMillis()-start));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		CACHED_SCRIPTS.put(fileName, script);
		
		return script;
	}
}
