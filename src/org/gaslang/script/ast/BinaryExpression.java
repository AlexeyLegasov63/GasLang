package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptAPI.BinaryOperator;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class BinaryExpression implements Expression
{
	public Expression expr1, expr2;
	public BinaryOperator operator;
	
	public BinaryExpression(Expression expr1, Expression expr2, BinaryOperator operator) {
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.operator = operator;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		Value<?> val1 = expr1.eval(gr),
				val2 = expr2.eval(gr);

		return switch (operator) {
			case ADD -> val1.add(val2);
			case SUB -> val1.sub(val2);
			case MUL -> val1.mul(val2);
			case DIV -> val1.div(val2);
			case MOD -> val1.mod(val2);
			case AND -> val1.and(val2);
			case XOR -> val1.xor(val2);
			case OR -> val1.or(val2);
			case LSHIFT -> val1.lshift(val2);
			case RSHIFT -> val1.rshift(val2);
			case URSHIFT -> val1.urshift(val2);
			default -> throw new RuntimeException();
		};

	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
