package org.gaslang.script.parser.lexer.token;

public class BooleanLiteral extends Literal
{
	private final Boolean bool;
	
	public BooleanLiteral(String value, int lineStart, int lineEnd, int columnStart, int columnEnd) {
		super(TokenType.BOOLEAN, value, lineStart, lineEnd, columnStart, columnEnd);
		this.bool = value.equals("true");
	}
	public BooleanLiteral(boolean value, int lineStart, int lineEnd, int columnStart, int columnEnd) {
		super(TokenType.BOOLEAN, String.valueOf(value), lineStart, lineEnd, columnStart, columnEnd);
		this.bool = value;
	}

	public Boolean getBoolean() {
		return bool;
	}
}
