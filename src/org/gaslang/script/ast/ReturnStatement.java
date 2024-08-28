package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Statement;
import org.gaslang.script.Value;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ReturnStatement extends RuntimeException implements Statement
{
	private static final long serialVersionUID = 1L;
	public Expression expression;
	public Value<?> value;
	
	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public void execute(GasRuntime runtime) {
		
		value = expression == null 
				? null 
				: expression.eval(runtime);
		
		throw this;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
