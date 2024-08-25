package org.gaslang.script.parser.lexer.token;

public class Token
{
	private final int lineStart, lineEnd, columnStart, columnEnd;
	private final TokenType tokenType;
	private String filename;
	
	public Token(TokenType tokenType, int lineStart, int lineEnd, int columnStart, int columnEnd) {
		this.lineStart = lineStart;
		this.lineEnd = lineEnd;
		this.columnStart = columnStart;
		this.columnEnd = columnEnd;
		this.tokenType = tokenType;
	}
	public void setFileName(String file) {
		this.filename = file;
	}
	public String getFilename() {
		return filename;
	}
	public Literal takeLiteral() {
		return (Literal)this;
	}
	public boolean isLiteral() {
		return this instanceof Literal;
	}
	public int getLineStart() {
		return lineStart;
	}
	public int getLineEnd() {
		return lineEnd;
	}
	public int getColumnStart() {
		return columnStart;
	}
	public int getColumnEnd() {
		return columnEnd;
	}
	public TokenType getTokenType() {
		return tokenType;
	}
	public boolean match(TokenType tokenType) {
		return this.tokenType == tokenType;
	}
	@Override
	public String toString() {
		return String.format("%s[(%s:%s)(%s:%s):%s]", tokenType, lineStart, columnStart, lineEnd, columnEnd, filename);
	}
}
