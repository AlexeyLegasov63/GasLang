package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class AliasStatement implements Statement
{
	public Expression expression;
	public String aliasName;
	
	public AliasStatement(Expression expression, String aliasName) {
		this.expression = expression;
		this.aliasName = aliasName;
	}

	@Override
	public void execute(GasRuntime gr) {
		if (gr.get(aliasName) != null) {
			throw new RuntimeException();
		}
		
		Value<?> value = expression.eval(gr);

		if (value instanceof PrimitiveValue && !value.matchValueType(ValueType.FUNCTION)) {
			throw new RuntimeException();
		}
		
		gr.set(aliasName, value);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
