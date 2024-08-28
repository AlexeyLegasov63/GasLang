package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

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
