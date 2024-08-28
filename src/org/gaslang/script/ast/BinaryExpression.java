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
		
		switch(operator) {
			case ADD:
				return val1.add(val2);
			case SUB:
				return val1.sub(val2);
			case MUL:
				return val1.mul(val2);
			case DIV:
				return val1.div(val2);
			case MOD:
				return val1.mod(val2);
		}
		
		throw new RuntimeException();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
