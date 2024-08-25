package org.gaslang.script.lib.boot;

import static org.gaslang.script.api.ScriptAPI.*;

import java.io.File;
import java.util.Random;

import org.gaslang.script.*;
import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

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
