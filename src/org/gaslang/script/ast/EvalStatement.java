package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class EvalStatement implements Statement
{
	public Expression expression;
	
	public EvalStatement(Expression expression) {
		this.expression = expression;
	}

	@Override
	public void execute(GasRuntime gr) {
		expression.eval(gr);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
