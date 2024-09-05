package org.gaslang.script.ast;

import org.gaslang.script.Expression;

public abstract class OperatorExpression implements Expression {

	private final Position position;

	public OperatorExpression(Expression expression) {
		this.position = expression.getPosition();
	}

	@Override
	public Position getPosition() {
		return position;
	}

}
