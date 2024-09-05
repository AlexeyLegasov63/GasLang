package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptAPI.BinaryOperator;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class BinaryExpression extends OperatorExpression
{
	public Expression expression1, expression2;
	public BinaryOperator operator;
	
	public BinaryExpression(Expression expression1, Expression expression2, BinaryOperator operator) {
		super(expression1);
		this.expression1 = expression1;
		this.expression2 = expression2;
		this.operator = operator;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		Value<?> value1 = expression1.eval(gasRuntime),
				value2 = expression2.eval(gasRuntime);

		return switch (operator) {
			case ADD -> value1.add(value2);
			case SUB -> value1.sub(value2);
			case MUL -> value1.mul(value2);
			case DIV -> value1.div(value2);
			case MOD -> value1.mod(value2);
			case AND -> value1.and(value2);
			case XOR -> value1.xor(value2);
			case OR -> value1.or(value2);
			case LSHIFT -> value1.lshift(value2);
			case RSHIFT -> value1.rshift(value2);
			case URSHIFT -> value1.urshift(value2);
			default -> throw gasRuntime.error(getPosition(), "Unknown binary operator");
		};

	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
