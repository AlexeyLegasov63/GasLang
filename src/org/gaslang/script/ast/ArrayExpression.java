package org.gaslang.script.ast;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

public class ArrayExpression implements Expression
{
	public final Expression value;
	
	public ArrayExpression(Expression value) {
		this.value = value;
	}
	
	@Override
	public Value<?> eval(GasRuntime gr) {
		HashMap<Integer, Value<?>> array = new HashMap<>();
		
		if (value instanceof TupleExpression)  {
			ArrayList<Expression> values = ((TupleExpression)value).expressions;
			for (int i = 0, l = values.size(); i < l; i++) {
				array.put(i, values.get(i).eval(gr));
			}
		} else if (value instanceof ParamsExpression) {
			ArrayList<Value<?>> values = ((Tuple)value.eval(gr)).getValues();
			
			for (int i = 0, l = values.size(); i < l; i++) {
				array.put(i, values.get(i));
			}
			
		} else {
			throw new RuntimeException();
		}
		
		
		return new ArrayValue(array);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
