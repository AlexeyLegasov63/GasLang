package org.gaslang.script.lib.boot;

import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;
import org.gaslang.script.run.GasRuntime;

import java.util.Optional;

@GasModule(name = "debug")
public class DebugModule
{
	@GasFunction
	public void printStackTrace(GasRuntime gasRuntime, Optional<Integer> level) {
		ScriptAPI.printStackTrace(gasRuntime, level);
	}
}
