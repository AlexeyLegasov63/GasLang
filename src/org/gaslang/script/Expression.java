package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;

public interface Expression extends Visitable
{
	Value<?> eval(GasRuntime gr);
}
