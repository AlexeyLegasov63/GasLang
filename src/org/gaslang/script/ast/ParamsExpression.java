package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

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
