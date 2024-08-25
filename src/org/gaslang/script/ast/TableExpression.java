package org.gaslang.script.ast;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class TableExpression implements Expression
{
	public final HashMap<Expression, Expression> values;
	
	public TableExpression(Nodes values) {
		this.values = new HashMap<>();
		this.values.putAll(values.nodes);
	}
	
	@Override
	public Value<?> eval(GasRuntime gr) {
		HashMap<Value<?>, Value<?>> array = new HashMap<>();
		for (Expression key : values.keySet()) {
			array.put(key.eval(gr), values.get(key).eval(gr));
		}
		return new TableValue(array);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
