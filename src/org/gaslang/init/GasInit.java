package org.gaslang.init;

import org.gaslang.script.GasScript;
import org.gaslang.script.ValueType;
import org.gaslang.script.api.ScriptAPI;
import static org.gaslang.script.api.ScriptAPI.*;

import java.util.Arrays;
import java.util.HashSet;

public class GasInit
{
	public static void main(String[] args)
	{
		assert args.length >= 1;

		var command = args[0];

		switch(command.toLowerCase()) {
			case "-c": {
				new ScriptAPI();
				var scriptName = args[1];
				var options = new HashSet<String>();
				if (args.length >= 3) {
					options.addAll(Arrays.asList(args).subList(2, args.length));
				}
				var mainScript = GasScript.loadScript(scriptName, options);
				var mainFunction = mainScript.get("main");
				if (mainFunction != null) {
					mainFunction.matchValueTypeOrThrow(ValueType.FUNCTION);
					mainFunction.call(tuple(array(options.toArray(new String[0]))));
				}

				return;
			}
			case "-v": {
				System.out.println("Current GasLang version: " + ScriptAPI.VERSION);
				return;
			}
			default: throw new IllegalArgumentException("Unknown command: " + command);
		}

	}
}
