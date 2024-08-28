package org.gaslang.script.ast;

import org.gaslang.script.Accessible;
import org.gaslang.script.Expression;
import org.gaslang.script.Statement;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class DefineFieldStatement implements Statement
{
	public Expression expression1, expression2;
	
	public DefineFieldStatement(Expression expression1, Expression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public void execute(GasRuntime gr) {
		Accessible accessible = (Accessible)expression1;

		//if (accessible.isEmpty(gr)) {
		//	throw new RuntimeException(accessible.toString());
		//}
		
		accessible.set(gr, expression2.eval(gr));
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
