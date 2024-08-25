package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptAPI.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

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
