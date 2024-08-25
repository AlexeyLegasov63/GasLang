package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class IndexExpression implements Expression, Accessible
{
	public Expression expression1, expression2;

	public IndexExpression(Expression expression1, Expression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		Value<?> value1 = expression1.eval(gr), value2 = expression2.eval(gr);
		return value1.index(value2);
	}

	@Override
	public Value<?> get(GasRuntime gr) {
		Value<?> value1 = expression1.eval(gr), value2 = expression2.eval(gr);
		return value1.index(value2);
	}

	@Override
	public Value<?> set(GasRuntime gr, Value<?> v) {
		Value<?> value1 = expression1.eval(gr), value2 = expression2.eval(gr);
		return value1.set(value2, v);
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
}
