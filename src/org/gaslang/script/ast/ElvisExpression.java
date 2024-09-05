package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.Literal;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ElvisExpression extends OperatorExpression implements Accessible
{
	public Expression expression;

	public ElvisExpression(Expression expression) {
		super(expression);
		this.expression = expression;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		var value = expression.eval(gr);

		if (value.isNull()) {
			throw new ElvisNonMatched();
		}

		return value;
	}

	@Override
	public Value<?> get(GasRuntime gr) {
		if (expression instanceof Accessible ac) {
			var value = expression.eval(gr);

			if (value.isNull()) {
				throw new ElvisNonMatched();
			}

			return ac.get(gr);
		}

		throw gr.error(getPosition(), "Non-accessible expression");
	}

	@Override
	public Value<?> set(GasRuntime gr, Value<?> v) {
		if (expression instanceof Accessible ac) {
			var value = expression.eval(gr);

			if (value.isNull()) {
				throw new ElvisNonMatched();
			}

			return ac.set(gr, v);
		}
		throw gr.error(getPosition(), "Non-accessible expression");
	}
	@Override
	public Value<?> insert(GasRuntime gr, Value<?> v) {

		if (expression instanceof Accessible ac) {
			var value = expression.eval(gr);

			if (value.isNull()) {
				throw new ElvisNonMatched();
			}

			return ac.insert(gr, v);
		}
		throw gr.error(getPosition(), "Non-accessible expression");
	}

	@Override
	public boolean isEmpty(GasRuntime gr) {
		if (expression instanceof Accessible ac) {
			var value = expression.eval(gr);

			if (value.isNull()) {
				throw new ElvisNonMatched();
			}

			return ac.isEmpty(gr);
		}
		throw gr.error(getPosition(), "Non-accessible expression");
	}

	@Override
	public Literal index(GasRuntime gr) {
		if (expression instanceof Accessible accessible) {
			var value = expression.eval(gr);

			if (value.isNull()) {
				throw new ElvisNonMatched();
			}

			return accessible.index(gr);
		}
		throw gr.error(getPosition(), "Non-accessible expression");
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	static class ElvisNonMatched extends RuntimeException {
	}
}
