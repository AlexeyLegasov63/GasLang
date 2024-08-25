package org.gaslang.script.ast;

import static org.gaslang.script.api.ScriptAPI.*;

import java.util.*;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.*;
import org.gaslang.script.run.*;
import org.gaslang.script.visitor.*;

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
