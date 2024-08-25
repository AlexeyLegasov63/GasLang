package org.gaslang.script.parser.lexer.token;

public class NumberLiteral extends Literal
{
	private final Number number;
	
	public NumberLiteral(Number number, String value, int lineStart, int lineEnd, int columnStart, int columnEnd) {
		super(TokenType.NUMBER, value, lineStart, lineEnd, columnStart, columnEnd);
		this.number = number;
	}

	public Number getNumber() {
		return number;
	}
}
