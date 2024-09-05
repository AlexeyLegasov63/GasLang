package org.gaslang.script.ast;

import org.gaslang.script.Expression;

public abstract class OperandExpression implements Expression {

	private final Position position;

	public OperandExpression(Position position) {
		this.position = position;
	}

	@Override
	public Position getPosition() {
		return position;
	}

}
