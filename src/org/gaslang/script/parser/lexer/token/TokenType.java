package org.gaslang.script.parser.lexer.token;

public enum TokenType
{
	EOF,
	NUMBER(true),
	STRING(true),
	CHARACTER(true),
	BOOLEAN(true),
	NULL(true),
	WORD,
	
	EQ("="),
	EQEQ("=="),
	NTEQ("!="),
	LT("<"),
	LTEQ("<="),
	RT(">"),
	RTEQ(">="),
	EQRT("=>"),
	MINUS("-"),
	MINUSMINUS("--"),
	PLUS("+"),
	PLUSPLUS("++"),
	PLUSEQ("+="),
	MINUSEQ("-="),
	STAREQ("*="),
	SLASHEQ("/="),
	PERCEQ("%="),
	PERC("%"),
	SLASH("/"),
	STAR("*"),
	DOT("."),
	COMMA(","),
	COLON(":"),
	LED("~"),
	DLR("$"),
	AT("@"),
	LPAREN("("),
	RPAREN(")"), 
	LBRAK("["),
	RBRAK("]"),
	LBRAC("{"),
	RBRAC("}"),	
	QST("?"),
	EX("!"),

	IF("if"),
	ELSE("else"),
	WHILE("while"),
	LOOP("loop"),
	DO("do"),
	AS("as"),
	IN("in"),
	THEN("then"),
	GLOBAL("global"),
	IMPORT("import"),
	EXPORT("export"),
	LOCAL("local"),
	PASS("pass"),
	ALIAS("alias"),
	CYCLE("cycle"),
	RETURN("return"),
	CONTINUE("continue"),
	FOREACH("foreach"),
	BREAK("break"),
	FOR("for"),
	NOT("not"),
	OR("or"),
	AND("and"),
	END("end"),
	FAKE("fake"),
	MASK("mask"),
	WEARS("wears"),
	OBJECT("object"),
	INSTANCEOF("is"),
	FUNCTION("function"),
	MUT("let");
	
	private final Boolean isValue;
	private final String literal;

	private TokenType() {
		this(false, "");
	}

	private TokenType(String literal) {
		this(false, literal);
	}
	
	private TokenType(Boolean isValue) {
		this(isValue, "");
	}
	
	private TokenType(Boolean isValue, String literal) {
		this.literal = literal;
		this.isValue = isValue;
	}
	
	public String getLiteral() {
		return literal;
	}

	public boolean match(TokenType type) {
		return this.equals(type);
	}
	
	public boolean isValue() {
		return isValue;
	}
}
