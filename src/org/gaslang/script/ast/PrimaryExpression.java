package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.BooleanLiteral;
import org.gaslang.script.parser.lexer.token.CharacterLiteral;
import org.gaslang.script.parser.lexer.token.Literal;
import org.gaslang.script.parser.lexer.token.NumberLiteral;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class PrimaryExpression implements Expression
{
	public Literal token;

	public PrimaryExpression(Literal token) {
		this.token = token;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		switch (token.getTokenType()) {
			case CHARACTER: return new CharacterValue(((CharacterLiteral)token).getCharacter());
			case BOOLEAN: return new BooleanValue(((BooleanLiteral)token).getBoolean());
			case NUMBER: return new NumberValue(((NumberLiteral)token).getNumber());
			case WORD:
			case STRING: return new StringValue(token.getLiteral());
			case NULL: return NullValue.NIL_VALUE;
			default: break;
		}
		throw new RuntimeException();
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
