package org.gaslang.script.ast;

import org.gaslang.script.TableValue;
import org.gaslang.script.Value;
import org.gaslang.script.parser.lexer.token.Literal;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import static org.gaslang.script.api.ScriptAPI.struct;

public class StructExpression extends TableExpression
{
	public String structName;
	
	public StructExpression(Literal literalStructName, Nodes arguments) {
		super(Position.of(literalStructName), arguments);
		this.structName = literalStructName.getLiteral();
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		var tableValue = super.eval(gasRuntime);
		struct((TableValue)tableValue, structName);
		return tableValue;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
