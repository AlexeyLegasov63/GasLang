package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class FakeExpression implements Expression
{
	@Override
	public Value<?> eval(GasRuntime gr) {
		return ScriptAPI.NULL;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
