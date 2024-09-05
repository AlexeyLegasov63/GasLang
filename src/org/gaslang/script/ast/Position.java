package org.gaslang.script.ast;

import org.gaslang.script.parser.lexer.token.Token;

public record Position(String script, int row, int column) {
	public static final Position ZERO = Position.of("", 0, 0);
	public static Position of(String file, int row, int column) {
		return new Position(file, row, column);
	}
	public static Position of(Token token) {
		return new Position(token.getFilename(), token.getLineStart(), token.getColumnStart());
	}
	@Override
	public String toString() {
		return "Position{" +
				"script=" + script +
				", row=" + row +
				", column=" + column +
				'}';
	}
}
