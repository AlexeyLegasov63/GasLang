package org.gaslang.script.parser.lexer.error;

public class LexerError extends RuntimeException
{
	private static final long serialVersionUID = 1466197017699482730L;
	public LexerError(String message) {
		super(message);
	}
}
