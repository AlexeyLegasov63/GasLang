package org.gaslang.script.parser.lexer.token;

public class EndOfFile extends Token
{
	public EndOfFile(int lineEnd, int columnEnd) {
		super(TokenType.EOF, 0, lineEnd, 0, columnEnd);
	}
}
