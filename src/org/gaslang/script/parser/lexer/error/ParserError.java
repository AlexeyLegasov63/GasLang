package org.gaslang.script.parser.lexer.error;

public class ParserError extends RuntimeException
{
	private static final long serialVersionUID = -454888587074307118L;

	public ParserError(String message) {
		super(message);
	}
}
