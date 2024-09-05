package org.gaslang.script.lib.boot;

import org.gaslang.script.FunctionValue;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;

@GasModule(name = "threads", shared = true)
public class ThreadModule
{
	@GasFunction
	public Value<?> newThread(FunctionValue function, Tuple tuple) {
		return ScriptAPI.instance(GasThread.class, function, tuple);
	}
	@GasFunction
	public Boolean sleep(Long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
