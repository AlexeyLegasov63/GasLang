package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ElvisExpression implements Expression, Accessible
{
	public Expression expression;

	public ElvisExpression(Expression expression) {
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
		throw new RuntimeException();
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
		throw new RuntimeException();
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
		throw new RuntimeException();
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
		throw new RuntimeException();
	}

	@Override
	public String index(GasRuntime gr) {
		if (expression instanceof Accessible ac) {
			var value = expression.eval(gr);

			if (value.isNull()) {
				throw new ElvisNonMatched();
			}

			return ac.index(gr);
		}
		throw new RuntimeException();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	static class ElvisNonMatched extends RuntimeException {
	}
}
