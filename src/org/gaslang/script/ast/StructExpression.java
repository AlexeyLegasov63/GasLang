package org.gaslang.script.ast;

import org.gaslang.script.TableValue;
import org.gaslang.script.Value;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import static org.gaslang.script.api.ScriptAPI.struct;

public class StructExpression extends TableExpression
{
	public String name;
	
	public StructExpression(Nodes arguments, String name) {
		super(arguments);
		this.name = name;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		Value<?> tableValue = super.eval(gr);
		struct((TableValue)tableValue, name);
		return tableValue;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
