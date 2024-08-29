package org.gaslang.script.parser.lexer;

import org.gaslang.script.ast.parser.FileParser;
import org.gaslang.script.parser.lexer.error.LexerError;
import org.gaslang.script.parser.lexer.token.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class FileLexer {
	private static final HashSet<Character> OPERATOR_CHARACTERS;
	private static final HashMap<String, TokenType> KEYWORDS;
	private final File context;
	private final StringBuffer buffer;
	private final char[] input;
	private final ArrayList<Token> output;
	private final ArrayList<LexerError> errors;
	private final int length;

	private int index, row = 1, column = 0;

	public FileLexer(File context, char[] input) {
		this.buffer = new StringBuffer();
		this.context = context;
		this.input = input;
		this.length = input.length;
		this.errors = new ArrayList<>();
		this.output = new ArrayList<>();
	}

	public StringBuffer getBuffer() {
		return buffer;
	}

	public FileParser toParser() {
		return new FileParser(context, read());
	}

	public ArrayList<Token> read() {
		char current = get(0);
		while (index < length) {
			try {
				// Skip spaces and tabs
				if (current <= 0x20) {
					current = next();
					continue;
				} else if (isDigit(current)) {
					readNumber();
				} else if (matchOperator(current)) {
					readOperator();
				} else if (current == '\'') {
					readChar();
				} else if (current == '"') {
					readText();
				} else if (isLetter(current) || current == '_') {
					readWord();
				} else {
					throw new RuntimeException(((int) current) + " " + current);
				}
				
				current = get(0);
				
			} catch (LexerError error) {
				current = next();
				errors.add(error);
			}
		}
		addToken(new EndOfFile(row, column));
		
		errors.forEach(error -> System.err.println(error.getMessage()));
		
		//System.out.println(output);
		return output;
	}

	private void readWord() {
		int startLine = row, startColumn = column;
		char current = get(0);
		while (true) {
			if (!(isDigit(current) || isLetter(current) || current == '_'))
				break;
			buffer.append(current);
			current = next();
		}
		String word = buffer.toString();
		if (word.equals("true")) {
			buffer.setLength(0);
			output.add(new BooleanLiteral(true, startLine, row, startColumn, column));
			return;
		} else if (word.equals("false")) {
			buffer.setLength(0);
			output.add(new BooleanLiteral(false, startLine, row, startColumn, column));
			return;
		}
		TokenType type = KEYWORDS.getOrDefault(word, TokenType.WORD);
		addToken(new Literal(type, word, startLine, row, startColumn, column));
		clearBuffer();
	}

	private void readOperator() {
		int startLine = row, startColumn = column;
		char current = get(0);
		
		if (current == '.' && isDigit(get(1))) {
			readDecimal(startLine, startColumn); // .123
			return;
		}
		
		if (current == '<') {
			if (get(1) == '-') {
				while (true) {
					if (current == '!' && get(1) == '>') {
						next();
						next();
						return;
					}
					if (current == '\0')
						return;
					current = next();
				}
			} else if (get(1) == '!') {
			  label_1:
				while (true) {
					if (current == '!' && get(1) == '>') {
						next();
						next();
						continue label_1;
					}
					if (current == '\0' || current == '\n' || current == '\r')
						return;
					current = next();
				}
			}
		}
		/*while (true) {
			if (!matchOperator(current))
				break;
			if (KEYWORDS.get(buffer.toString() + current) == null)
				break;
			buffer.append(current);
			current = next();
		}
		if (buffer.isEmpty())
			throw error("Uknown lexer operator");
		addToken(new Operator(KEYWORDS.get(buffer.toString()), startLine, row, startColumn, column));
		buffer.setLength(0);*/

		while (true) {
			final String text = buffer.toString();
			if (!KEYWORDS.containsKey(text + current) && !text.isEmpty()) {
				addToken(new Operator(KEYWORDS.get(buffer.toString()), startLine, row, startColumn, column));
				buffer.setLength(0);
				return;
			}
			buffer.append(current);
			current = next();
		}
	}

	private void readNumber() {
		int startLine = row, startColumn = column;
		if (get(0) == '0' && (get(1) == 'x' || get(1) == 'X')) {
			next();
			next();
			readHex(startLine, startColumn);
			return;
		}
		readDecimal(startLine, startColumn);
	}

	private void readDecimal(int startLine, int startColumn) {
		char current = get(0);
		boolean isFloat = false, isLan = false;
		while (true) {
			if ((current == 'E' || current == 'e') && !isLan) {
				buffer.append(current);
				current = next();
				buffer.append(current);
				current = next();
				buffer.append(current);
				current = next();
				isLan = true;
			}
			if (!(isDigit(current) || (!isFloat && current == '.')))
				break;
			if (current == '.')
				isFloat = true;
			buffer.append(current);
			current = next();
		}
		addToken(new NumberLiteral(isFloat ? Double.parseDouble(buffer.toString()) : Long.parseLong(buffer.toString()), buffer.toString(), startLine, row, startColumn, column));
		buffer.setLength(0);
	}

	private void readHex(int startLine, int startColumn) {
		char current = get(0);
		while (true) {
			if (!(isDigit(current) || (current >= 'a' && current <= 'f')
					|| (current >= 'A' && current <= 'F')))
				break;
			buffer.append(current);
			current = next();
		}
		addToken(new NumberLiteral(Long.parseLong(buffer.toString(), 16), buffer.toString(), startLine, row, startColumn, column));
		buffer.setLength(0);
	}

	private void readChar() {
		int startLine = row, startColumn = column;
		char current = next();
		if (current == '\0')
			throw error("Unclosed Character");
		if (current == '\\') {
			switch(current) {
				case 'b': buffer.append('\b'); break;
				case 't': buffer.append('\t'); break;
				case 'r': buffer.append('\r'); break;
				case 'n': buffer.append('\n'); break;
				case 'f': buffer.append('\f'); break;
				case '\\': buffer.append('\\'); break;
				default:
					throw error("Uknown regular text expression");
			}
		}
		consume('\'');
		next();
		addToken(new CharacterLiteral(current, startLine, row, startColumn, column));
	}

	private void readText() {
		int startLine = row, startColumn = column;
		char current = next();
		while (current != '"') {
			if (current == '\0') {
				throw error("Unclosed text");
			} else if (current == '\\') {
				current = next();
				switch(current) {
					case 'b': buffer.append('\b'); break;
					case 't': buffer.append('\t'); break;
					case 'r': buffer.append('\r'); break;
					case 'n': buffer.append('\n'); break;
					case 'f': buffer.append('\f'); break;
					case '\\': buffer.append('\\'); break;
					default:
						throw error("Uknown regular text expression");
				}
				current = next();
				continue;
			} else if (current == '\n' || current == '\r') {
				current = next();
				continue;
			}
			buffer.append(current);
			current = next();
		}
		addToken(new StringLiteral(buffer.toString(), startLine, row, startColumn, column));
		buffer.setLength(0);
		next();
	}

	private void addToken(Token token) {
		token.setFileName(context.getName());
		output.add(token);
	}

	private char get(int arg0) {
		int at = index + arg0;
		if (at >= length)
			return '\0';
		return input[at];
	}

	private char consume(char arg0) {
		char current = next();
		if (current != arg0)
			throw error(String.format("Character '%s' cannot be consume because is not similar to '%s'", current, arg0));
		return arg0;
	}

	private char next() {
		index++;
		char c = get(0);
		if (c == '\n') {
			row++;
			column = 0;
		} else if (c == '\t') {
			column += 4;
		} else
			column++;
		return c;
	}

	static {
		OPERATOR_CHARACTERS = new HashSet<>(Arrays.asList('?', '!', '%', '^', '&', '|', '(', ')', '-', '=', '$', '+', '/', '<', '>', ',', '.', '{', '}', '[', ']', ':', ';', '*', '~', '@'));
		
		KEYWORDS = new HashMap<>();
		
		for (TokenType tokenType : TokenType.values()) {
			if (tokenType.getLiteral() == null) continue;
			KEYWORDS.put(tokenType.getLiteral(), tokenType);
		}
	}
	
	private LexerError error(String message) {
		return new LexerError(String.format("[%s:%s] Error: %s", message, row, column));
	}
	private void warning(String message, Object... args) {
		errors.add(new LexerError(String.format("[%s:%s] Warn: %s", String.format(message, args), row, column)));
	}
	private void clearBuffer() {
		buffer.setLength(0);
	}
	private boolean matchOperator(char arg0) {
		return OPERATOR_CHARACTERS.contains(arg0);
	}
}
