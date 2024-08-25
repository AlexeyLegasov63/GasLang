package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class DefineFieldExpression implements Expression
{
	public Expression expression1, expression2;
	
	public DefineFieldExpression(Expression expression1, Expression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		Accessible accessible = (Accessible)expression1;
		
		if (accessible.isEmpty(gr)) {
			throw new RuntimeException(accessible.getClass().toGenericString());
		}
		
		return accessible.set(gr, expression2.eval(gr));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
