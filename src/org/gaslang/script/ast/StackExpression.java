package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.api.ScriptAPI.StackSpace;
import org.gaslang.script.parser.lexer.token.Literal;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class StackExpression extends OperandExpression implements Accessible
{
	public StackSpace space;
	public Literal token;

	public StackExpression(Literal token) {
		this(token, StackSpace.CURRENT);
	}
	public StackExpression(Literal token, StackSpace space) {
		super(Position.of(token));
		this.token = token;
		this.space = space;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		return get(gasRuntime);
	}

	@Override
	public Value<?> get(GasRuntime gasRuntime) {
		String literalName = token.getLiteral();

		Value<?> value = switch (space) {
			case CURRENT: yield gasRuntime.get(literalName);
			case LOCAL: yield gasRuntime.getLocal(literalName);
			case GLOBAL: yield gasRuntime.getGlobal(literalName);
		};

		if (value != null) {
			if (value instanceof AliasValue aliasValue) {
				value = gasRuntime.get(aliasValue.jValue());
				if (value == null)
					throw gasRuntime.error(getPosition(), String.format("There's no such variable: %s", literalName));
				return value;
			}
			return value;
		}

		throw gasRuntime.error(getPosition(), String.format("There's no such variable: %s", literalName));
	}

	@Override
	public Value<?> set(GasRuntime gr, Value<?> v) {
		Value<?> value = gr.getLocal(token.getLiteral());
		// Except an Objects,Enums,Modules,Functions, "Native Variables" Redefine
		if (value != null && !(value instanceof PrimitiveValue)) {
			throw new RuntimeException(value.getClass().getName() + " " + gr.toString());
		}
		gr.set(token.getLiteral(), v);
		return v;
	}
	@Override
	public Value<?> insert(GasRuntime gr, Value<?> v) {
		var literal = token.getLiteral();
		
		if (gr.containsLocal(literal)) {
			throw new RuntimeException("Duplicate");
		}
		
		gr.setLocal(literal, v);
		return v;
	}
	
	@Override
	public boolean isEmpty(GasRuntime gr) {
		return gr.get(token.getLiteral()) == null;
	}

	@Override
	public Literal index(GasRuntime gr) {
		return token;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return token.getLiteral();
	}
}
