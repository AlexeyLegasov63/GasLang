package org.gaslang.script.parser.lexer.token;

public class Operator extends Token
{
	public Operator(TokenType tokenType, int lineStart, int lineEnd, int columnStart, int columnEnd) {
		super(tokenType, lineStart, lineEnd, columnStart, columnEnd);
	}
}
