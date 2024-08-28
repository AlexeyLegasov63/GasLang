package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.TableValue;
import org.gaslang.script.Value;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import java.util.HashMap;

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
