package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptAPI.UnaryOperator;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class UnaryExpression extends OperatorExpression
{
	public Expression expression;
	public UnaryOperator operator;
	
	public UnaryExpression(Expression expression, UnaryOperator operator) {
		super(expression);
		this.expression = expression;
		this.operator = operator;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		Value<?> value = expression.eval(gasRuntime);
		if (value.isNull()) {
			throw gasRuntime.error(getPosition(), "Non-valid value for unary expression: null");
		}
		return switch (operator) {
			case NEG -> value.negate();
			case INV -> value.inverse();
			default -> throw gasRuntime.error(getPosition(), "Unknown unary operator");
		};
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
