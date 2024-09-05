package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;

public interface FunctionBlock extends Named
{
	Value<?> execute(GasRuntime gasRuntime, Tuple args);

	default String desiredArgs() {
		return "";
	}
}
