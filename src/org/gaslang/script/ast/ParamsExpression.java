package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ParamsExpression implements Expression
{
	@Override
	public Value<?> eval(GasRuntime gr) {
		Value<?> paramsOffcut = gr.get("$");
		
		if (paramsOffcut == null) {
			throw new RuntimeException();
		}
		
		return paramsOffcut;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
