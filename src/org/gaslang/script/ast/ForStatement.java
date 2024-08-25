package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ForStatement implements Statement
{
	public Statement block, par1, par2;
	public Expression expression;
	
	public ForStatement(Statement block, Statement par1, Statement par2, Expression expression) {
		this.expression = expression;
		this.block = block;
		this.par1 = par1;
		this.par2 = par2;
	}

	@Override
	public void execute(GasRuntime gr) {
		gr.push();
		for (par1.execute(gr); expression.eval(gr).asBoolean(); par2.execute(gr)) {
			try {
				block.execute(gr);
			} catch(ContinueStatement s) {
				continue;
			} catch(BreakStatement s) {
				break;
			}
		}
		gr.pop();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
