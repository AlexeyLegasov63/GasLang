package org.gaslang.script.lib.boot;

import org.gaslang.script.FunctionValue;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

import static org.gaslang.script.api.ScriptAPI.tuple;

@GasType(name = "thread", shared = false)
public class GasThread extends Thread
{
	private FunctionValue value;

	@GasFunction
	public void constructor(FunctionValue value) {
		this.value = value;
	}

	@Override
	@GasFunction
	public synchronized void start() {
		super.start();
	}

	@Override
	@GasFunction
	public void run() {
		value.call(tuple());
	}

	@Override
	@GasFunction
	public void interrupt() {
		super.interrupt();
	}

	@Override
	@GasFunction
	public boolean isInterrupted() {
		return super.isInterrupted();
	}
}
