package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;

public interface Statement extends Visitable
{
	void execute(GasRuntime gr);
}
