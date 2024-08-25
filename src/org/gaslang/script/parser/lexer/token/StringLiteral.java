package org.gaslang.script.parser.lexer.token;

public class StringLiteral extends Literal
{
	public StringLiteral(String value, int lineStart, int lineEnd, int columnStart, int columnEnd) {
		super(TokenType.STRING, value, lineStart, lineEnd, columnStart, columnEnd);
	}

	public String getString() {
		return value;
	}
}
