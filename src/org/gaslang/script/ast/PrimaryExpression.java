package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.parser.lexer.token.BooleanLiteral;
import org.gaslang.script.parser.lexer.token.CharacterLiteral;
import org.gaslang.script.parser.lexer.token.Literal;
import org.gaslang.script.parser.lexer.token.NumberLiteral;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class PrimaryExpression extends OperandExpression
{
	public final Literal literalValue;

	public PrimaryExpression(Literal literalValue) {
		super(Position.of(literalValue));
		this.literalValue = literalValue;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		return switch (literalValue.getTokenType()) {
			case CHARACTER -> new CharacterValue(((CharacterLiteral) literalValue).getCharacter());
			case BOOLEAN -> new BooleanValue(((BooleanLiteral) literalValue).getBoolean());
			case NUMBER -> new NumberValue(((NumberLiteral) literalValue).getNumber());
			case WORD, STRING -> new StringValue(literalValue.getLiteral());
			case NULL -> NullValue.NIL_VALUE;
			default -> throw gasRuntime.error(getPosition(), "Unknown literal value: " + literalValue);
		};
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return literalValue.getLiteral();
	}
}
