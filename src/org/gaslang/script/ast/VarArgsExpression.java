package org.gaslang.script.ast;

import org.gaslang.script.Value;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class VarArgsExpression extends OperandExpression
{
	public VarArgsExpression(Position position) {
		super(position);
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		var functionVarArgs = gasRuntime.get("<VarArgs>");
		
		if (functionVarArgs == null) {
			throw gasRuntime.error(getPosition(), "There's no VarArgs");
		}

		return functionVarArgs;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
