package org.gaslang.script.ast;

import org.gaslang.script.Accessible;
import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class DefineFieldExpression extends OperatorExpression
{
	public final Expression expressionToDefine, newValueExpression;
	
	public DefineFieldExpression(Expression expressionToDefine, Expression newValueExpression) {
		super(expressionToDefine);
		this.expressionToDefine = expressionToDefine;
		this.newValueExpression = newValueExpression;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		var accessible = (Accessible)expressionToDefine;
		
		if (accessible.isEmpty(gasRuntime)) {
			throw gasRuntime.error(getPosition(), "Empty accessible");
		}
		
		return accessible.set(gasRuntime, newValueExpression.eval(gasRuntime));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
