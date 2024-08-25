package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public interface Expression extends Visitable
{
	Value<?> eval(GasRuntime gr);
}
