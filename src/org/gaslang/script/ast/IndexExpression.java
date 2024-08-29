package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class IndexExpression implements Expression, Accessible
{
	public Expression expression1, expression2;

	public IndexExpression(Expression expression1, Expression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		return get(gr);
	}

	@Override
	public Value<?> get(GasRuntime gr) {
		try {
			var value1 = expression1.eval(gr);
			var value2 = expression2.eval(gr);
			return value1.index(value2);
		} catch (ElvisExpression.ElvisNonMatched m) {
			return NullValue.NIL_VALUE;
		}
	}

	@Override
	public Value<?> set(GasRuntime gr, Value<?> v) {
		try {
			var value1 = expression1.eval(gr);
			var value2 = expression2.eval(gr);
			return value1.set(value2, v);
		} catch (ElvisExpression.ElvisNonMatched m) {
			return NullValue.NIL_VALUE;
		}
	}
	@Override
	public Value<?> insert(GasRuntime gr, Value<?> v) {
		return set(gr, v);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String index(GasRuntime gr) {
		Value<?> value = expression2.eval(gr);
		return value.matchValueType(ValueType.STRING) ? value.asString() : null;
	}

	@Override
	public String toString() {
		return expression1 + "[" + expression2 + "]";
	}
}
