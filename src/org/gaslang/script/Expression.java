package org.gaslang.script;

import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.ast.Position;

public interface Expression extends Visitable
{
	Value<?> eval(GasRuntime gr);

	Position getPosition();
}
