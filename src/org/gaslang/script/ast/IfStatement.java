package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class IfStatement implements Statement
{
	public Statement block, unless;
	public Expression expression;
	
	public IfStatement(Statement block, Expression expression, Statement unless) {
		this.expression = expression;
		this.block = block;
		this.unless = unless;
	}

	@Override
	public void execute(GasRuntime gr) {
		if (expression.eval(gr).asBoolean()) {
			block.execute(gr);
		} else if (unless != null) {
			unless.execute(gr);
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
