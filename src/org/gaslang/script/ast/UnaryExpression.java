package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptAPI.UnaryOperator;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class UnaryExpression implements Expression
{
	public Expression expr;
	public UnaryOperator operator;
	
	public UnaryExpression(Expression expr, UnaryOperator operator) {
		this.expr = expr;
		this.operator = operator;
	}

	@Override
	public Value eval(GasRuntime gr) {
		Value value = expr.eval(gr);
		System.out.println(value);
		switch(operator) {
			case NEG:
				return value.negate();
			case INV:
				return value.inverse();
		default:
			break;
		}
		
		throw new RuntimeException();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
