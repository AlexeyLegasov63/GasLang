package org.gaslang.script.lib.boot;

import org.gaslang.script.TableValue;
import org.gaslang.script.Value;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

import static org.gaslang.script.api.ScriptAPI.number;

@GasModule(name = "Instance")
public class InstanceModule
{
	@GasFunction(name = "new")
	public Value newValue(TableValue table, Value value) {
		for (int i = 0, l = table.getSize(); i < l; i++) {
			table.set(number(i), value);
		}
		return table;
	}
}
