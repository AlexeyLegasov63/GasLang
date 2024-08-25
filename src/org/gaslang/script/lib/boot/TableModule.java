package org.gaslang.script.lib.boot;

import static org.gaslang.script.api.ScriptAPI.*;

import java.util.Random;

import org.gaslang.script.*;
import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

@GasModule(name = "table")
public class TableModule
{
	@GasFunction
	public Integer size(TableValue table) {
		return table.getSize();
	}
	@GasFunction
	public void clear(TableValue table) {
		table.clear();
	}
	@GasFunction
	public void forEach(TableValue table, FunctionValue iterator) {
		for (int i = 0, l = table.getSize(); i < l; i++) {
			iterator.call(tuple(table.index(number(i))));
		}
	}
}
