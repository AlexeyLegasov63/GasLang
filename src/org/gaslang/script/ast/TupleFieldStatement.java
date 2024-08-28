package org.gaslang.script.ast;

import org.gaslang.script.Accessible;
import org.gaslang.script.Expression;
import org.gaslang.script.Statement;
import org.gaslang.script.Tuple;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class TupleFieldStatement implements Statement
{
	public Expression[] names;
	public Expression expression;
	
	public TupleFieldStatement(Expression[] names, Expression expression) {
		this.expression = expression;
		this.names = names;
	}

	@Override
	public void execute(GasRuntime gr) {
		Tuple tuple = Tuple.valueOf(expression.eval(gr));
		
		if (tuple.getSize() > names.length)
			throw new RuntimeException();
		
		for (int i = 0, l = names.length; i < l; i++) {
			if (names[i] instanceof FakeExpression)
				continue;
			((Accessible)names[i]).insert(gr, tuple.getValue(i));
		}
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
