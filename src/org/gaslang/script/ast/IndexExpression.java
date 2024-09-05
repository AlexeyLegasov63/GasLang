package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.Literal;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class IndexExpression extends OperatorExpression implements Accessible
{
	public Expression parentExpression, indexExpression;

	public IndexExpression(Expression parentExpression, Expression indexExpression) {
		super(indexExpression);
		this.parentExpression = parentExpression;
		this.indexExpression = indexExpression;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		return get(gasRuntime);
	}

	@Override
	public Value<?> get(GasRuntime gasRuntime) {
		try {
			var parentValue = parentExpression.eval(gasRuntime);
			var indexValue = indexExpression.eval(gasRuntime);
			return parentValue.index(indexValue);
		} catch (ElvisExpression.ElvisNonMatched m) {
			return NullValue.NIL_VALUE;
		}
	}

	@Override
	public Value<?> set(GasRuntime gasRuntime, Value<?> valueToSet) {
		try {
			var parentValue = parentExpression.eval(gasRuntime);
			var indexValue = indexExpression.eval(gasRuntime);
			return parentValue.set(indexValue, valueToSet);
		} catch (ElvisExpression.ElvisNonMatched m) {
			return NullValue.NIL_VALUE;
		}
	}
	@Override
	public Value<?> insert(GasRuntime gasRuntime, Value<?> valueToInsert) {
		return set(gasRuntime, valueToInsert);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Literal index(GasRuntime gr) {
		if (indexExpression instanceof Accessible accessible) {
			return accessible.index(gr);
		} else if (indexExpression instanceof PrimaryExpression primary) {
			return primary.literalValue;
		}
		throw gr.error(getPosition(), "Unknown literal index");
	}

	@Override
	public String toString() {
		return parentExpression + "[" + indexExpression + "]";
	}
}
