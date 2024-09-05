package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.TableValue;
import org.gaslang.script.Value;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import java.util.HashMap;

public class TableExpression extends OperandExpression
{
	public final HashMap<Expression, Expression> values;
	
	public TableExpression(Position position, Nodes values) {
		super(position);
		this.values = new HashMap<>();
		this.values.putAll(values.nodes);
	}
	
	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		HashMap<Value<?>, Value<?>> array = new HashMap<>();
		for (Expression key : values.keySet()) {
			array.put(key.eval(gasRuntime), values.get(key).eval(gasRuntime));
		}
		return new TableValue(array);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
