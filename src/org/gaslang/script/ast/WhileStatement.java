package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class WhileStatement implements Statement
{
	public Statement block;
	public Expression expression;
	
	public WhileStatement(Statement block, Expression expression) {
		this.expression = expression;
		this.block = block;
	}

	@Override
	public void execute(GasRuntime gr) {
		while (expression.eval(gr).asBoolean()) {
			try {
				block.execute(gr);
			} catch(ContinueStatement s) {
				continue;
			} catch(BreakStatement s) {
				break;
			}
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
