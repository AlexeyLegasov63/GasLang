package org.gaslang.script.parser.lexer.token;

public enum TokenType
{
	EOF,
	NUMBER(true),
	STRING(true),
	CHARACTER(true),
	BOOLEAN(true),
	NULL(true, "null"),
	WORD,
	
	EQ("="),
	EQEQ("=="),
	NTEQ("!="),
	LT("<"),
	LTEQ("<="),
	RT(">"),
	RTEQ(">="),
	EQRT("=>"),
	MINUS("-", true),
	MINUSMINUS("--"),
	PLUS("+", true),
	PLUSPLUS("++"),
	PERC("%", true),
	SLASH("/", true),
	STAR("*", true),
	DOT("."),
	COMMA(","),
	COLON(":"),
	SEMICOLON(";"),
	DLR("..."),
	RAN(".."),
	AT("@"),
	LTLT("<<", true),
	GTGT(">>", true),
	GTGTGT(">>>", true),
	TILDE("~"),
	CARET("^", true),
	BAR("|", true),
	AMP("&", true),

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
	
	private final Boolean isValue, isArithmetical;
	private final String literal;

	private TokenType() {
		this(false, "", false);
	}

	private TokenType(String literal) {
		this(false, literal, false);
	}

	private TokenType(String literal, Boolean isArithmetical) {
		this(false, literal, isArithmetical);
	}
	
	private TokenType(Boolean isValue) {
		this(isValue, "", false);
	}

	private TokenType(Boolean isValue, String literal) {
		this(isValue, literal, false);
	}

	private TokenType(Boolean isValue, String literal, Boolean isArithmetical) {
		this.literal = literal;
		this.isValue = isValue;
		this.isArithmetical = isArithmetical;
	}
	
	public String getLiteral() {
		return literal;
	}

	public boolean match(TokenType type) {
		return this.equals(type);
	}

	public boolean isArithmetical() {
		return isArithmetical;
	}

	public boolean isValue() {
		return isValue;
	}
}
