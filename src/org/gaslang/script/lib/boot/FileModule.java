package org.gaslang.script.lib.boot;

import org.gaslang.script.TableValue;
import org.gaslang.script.Value;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

import java.io.File;

import static org.gaslang.script.api.ScriptAPI.bool;
import static org.gaslang.script.api.ScriptAPI.number;

@GasModule(name = "file")
public class FileModule
{
	private File file;
	
	@GasFunction
	public synchronized Value openfile(Value type) {
		try {
			
		} catch(Exception ex) {
			
		}
		
		return bool(true);
	}
	@GasFunction
	public Value clear(TableValue table) {
		table.clear();
		return bool(true);
	}
	@GasFunction
	public Value fill(TableValue table, Value value) {
		for (int i = 0, l = table.getSize(); i < l; i++) {
			table.set(number(i), value);
		}
		return table;
	}
}
