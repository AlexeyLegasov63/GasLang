package org.gaslang.script.parser.lexer.token;

public class WordLiteral extends Literal
{
	public WordLiteral(TokenType tokenType, String value, int lineStart, int lineEnd, int columnStart, int columnEnd) {
		super(tokenType, value, lineStart, lineEnd, columnStart, columnEnd);
	}

	public String getWord() {
		return value;
	}
}
