package org.gaslang.script.lib.boot;

import org.gaslang.script.FunctionValue;
import org.gaslang.script.TableValue;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

import static org.gaslang.script.api.ScriptAPI.number;
import static org.gaslang.script.api.ScriptAPI.tuple;

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
