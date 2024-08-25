package org.gaslang.script.lib.boot;

import static org.gaslang.script.api.ScriptAPI.*;

import java.io.File;
import java.util.Random;

import org.gaslang.script.*;
import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

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
