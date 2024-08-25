package org.gaslang.script.ast;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class TupleExpression implements Expression
{
	private final HashMap<String, Expression> named = new HashMap<>();
	public final ArrayList<Expression> expressions = new ArrayList<>();

	public void add(Expression expression) {
		expressions.add(expression);
	}

	public void add(String mark, Expression expression) {
		named.put(mark, expression);
	}
	
	@Override
	public Value<?> eval(GasRuntime gr) {
		var length = expressions.size();
		var values = new Value<?>[length];
		
		for (int i = 0; i < length; i++) {
			values[i] = expressions.get(i).eval(gr);
		}
		
		HashMap<String, Value<?>> named = new HashMap<>();
		
		this.named.forEach((k,v) -> named.put(k, v.eval(gr)));
		
		return Tuple.valueOf(named, values);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}