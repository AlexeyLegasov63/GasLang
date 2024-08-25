package org.gaslang.script.ast;

import java.util.HashSet;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class CallExpression implements Expression
{
	private static final HashSet<Expression> CALLBACKS_SET = new HashSet<>();
	
	public Expression value;
	public Expression expression;

	public CallExpression(Expression value, Expression expression) {
		this.value = value;
		this.expression = expression;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		Value<?> value = this.value.eval(gr);
		Tuple tuple = Tuple.valueOf(expression.eval(gr));
		
		if (value instanceof InstanceFunctionValue) {
			tuple.getValues().add(0, ((IndexExpression)this.value).expression1.eval(gr));
		}
		
		try {
			CALLBACKS_SET.add(this.value);
			return value.call(tuple);
		} catch (Exception e) {
			System.err.println(CALLBACKS_SET);
			e.printStackTrace();
		} finally {
			 CALLBACKS_SET.remove(this.value);
		}
		return NullValue.NIL_VALUE;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
