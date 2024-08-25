package org.gaslang.script.parser.lexer.token;

public class CharacterLiteral extends Literal
{
	private final char value;
	
	public CharacterLiteral(char value, int lineStart, int lineEnd, int columnStart, int columnEnd) {
		super(TokenType.CHARACTER, String.valueOf(value), lineStart, lineEnd, columnStart, columnEnd);
		this.value = value;
	}
	
	public Character getCharacter() {
		return value;
	}
}
