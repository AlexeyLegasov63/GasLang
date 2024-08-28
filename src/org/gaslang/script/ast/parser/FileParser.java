package org.gaslang.script.ast.parser;

import org.gaslang.script.*;
import org.gaslang.script.api.ScriptAPI.BinaryOperator;
import org.gaslang.script.api.ScriptAPI.ConditionalyOperator;
import org.gaslang.script.api.ScriptAPI.StackSpace;
import org.gaslang.script.api.ScriptAPI.UnaryOperator;
import org.gaslang.script.ast.*;
import org.gaslang.script.parser.lexer.error.ParserError;
import org.gaslang.script.parser.lexer.token.Literal;
import org.gaslang.script.parser.lexer.token.Token;
import org.gaslang.script.parser.lexer.token.TokenType;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.gaslang.script.NullValue.NIL_VALUE;
import static org.gaslang.script.parser.lexer.token.TokenType.*;

public class FileParser
{
	private static final Expression NIL_EXPRESSION = new Expression() {
		@Override
		public Value<?> eval(GasRuntime gr) {
			return NIL_VALUE;
		}
		@Override
		public void accept(Visitor visitor) {
		}
	};
	
	private List<Statement> statements;
	private List<Token> tokens;
	private File file;
	private int position, max, row, col;
	private Script script;
	
	public FileParser(File file, List<Token> tokens) {
		this.file = file;
		this.tokens = tokens;
		this.max = tokens.size();
		
		switchPosition(get(0));
	}

	public Script parse() {
		script = new Script(file);
		
		try {
			while(!match(EOF)) {
				script.add(globalStatement());
			}
		} catch(ParserError err) {
			err.printStackTrace();
		}
		
		return script;
	}
	
	private Statement globalStatement() {
		if (look(ALIAS)) return parseAlias();
		if (look(IMPORT)) return parseImport();
		if (look(EXPORT)) return parseExport();
		if (look(OBJECT)) return parseObject(new AnnotationsExpression());
		if (look(MASK)) return parseMask(new AnnotationsExpression());
		
		return statement();
	}
	
	private Statement statement() {

		if (look(IF)) return parseIf();
		if (look(FOR)) return parseFor();
		if (look(FOREACH)) return parseForeach();
		if (look(WHILE)) return parseWhile();
		if (look(LOOP)) return parseLoop();
		if (look(CYCLE)) return parseCycle();
		if (look(MUT)) return parseField();
		if (look(FUNCTION)) return parseFunction(new AnnotationsExpression());
		if (look(RETURN)) return parseReturn();

		if (match(PASS)) return new PassStatement();
		if (look(CONTINUE)) return parseContinue();
		if (look(BREAK)) return parseBreak();
		if (match(DO)) return parseBlock();

		return statementWithAnnotations();
	}
	
	private Statement statementWithAnnotations() {
		if (look(AT)) {
			AnnotationsExpression expr = parseAnnotations();
			if (look(FUNCTION)) return parseFunction(expr);
			if (look(OBJECT)) return parseObject(expr);
			if (look(MASK)) return parseMask(expr);
			throw new RuntimeException();
		}
		
		Expression expression = assign();
		
		if (!(expression instanceof DefineFieldExpression || expression instanceof CallExpression)) throw report("Uknown statement: " + expression.getClass());
		
		return new EvalStatement(expression);
	}
	
	private Expression expression() {
		return logicalOr();
	}
	
	private Expression logicalOr() {
		Expression expression = logicalAnd();
        while (true) {
            if (match(TokenType.OR)) {
            	expression = new ConditionalExpression(expression, logicalAnd(), ConditionalyOperator.OR);
                continue;
            }
            break;
        }
		return expression;
	}
	
	private Expression logicalAnd() {
		Expression expression = equality();
        while (true) {
            if (match(TokenType.AND)) {
            	expression = new ConditionalExpression(expression, equality(), ConditionalyOperator.AND);
                continue;
            }
            break;
        }
		return expression;
	}

    private Expression equality() {
        Expression expression = conditional();

        if (match(TokenType.WEARS)) {
        	return new ConditionalExpression(expression, conditional(), ConditionalyOperator.WEARS);
        }
        if (match(TokenType.INSTANCEOF)) {
        	return new ConditionalExpression(expression, conditional(), ConditionalyOperator.INSTANCEOF);
        }
        if (match(TokenType.EQEQ)) {
        	return new ConditionalExpression(expression, conditional(), ConditionalyOperator.EQ);
        }
        if (match(TokenType.NTEQ)) {
        	return new ConditionalExpression(expression, conditional(), ConditionalyOperator.NQ);
        }

        return expression;
    }

    private Expression conditional() {
		Expression expression = assign();
        while (true) {
            if (match(TokenType.RT)) {
            	expression = new ConditionalExpression(expression, assign(), ConditionalyOperator.MR);
                continue;
            }
            if (match(TokenType.LT)) {
            	expression = new ConditionalExpression(expression, assign(), ConditionalyOperator.LS);
                continue;
            }
            if (match(TokenType.LTEQ)) {
            	expression = new ConditionalExpression(expression, assign(), ConditionalyOperator.EQLS);
                continue;
            }
            if (match(TokenType.RTEQ)) {
            	expression = new ConditionalExpression(expression, assign(), ConditionalyOperator.EQMR);
                continue;
            }
            break;
        }
		return expression;
    }
    


	private Expression assign() {
		Expression expression = shift();
		
		if (match(EQ)) {
			return new DefineFieldExpression(expression, expression());
		}
		
		return expression;
	}
    
    private Expression shift() {
        Expression expression = additive();
/*
        while (true) {
            if (match(TokenType.LTLT)) {
            	expression = new BinaryExpression(expression, additive(), BINARY_OPETOR_LSH);
                continue;
            }
            if (match(TokenType.RTRT)) {
            	expression = new BinaryExpression(expression, additive(), BINARY_OPETOR_RSH);
                continue;
            }
            if (match(TokenType.RTRTRT)) {
            	expression = new BinaryExpression(expression, additive(), BINARY_OPETOR_URSH);
                continue;
            }
            break;
        }
*/
        return expression;
    }

	private Expression additive() {
		Expression expression = multiplicative();
        while (true) {
            if (match(TokenType.PLUS)) {
            	expression = new BinaryExpression(expression, multiplicative(), BinaryOperator.ADD);
                continue;
            }
            if (match(TokenType.MINUS)) {
            	expression = new BinaryExpression(expression, multiplicative(), BinaryOperator.SUB);
                continue;
            }
            break;
        }
		return expression;
	}
	
	private Expression multiplicative() {
		Expression expression = unary();
        while (true) {
            if (match(TokenType.STAR)) {
            	expression = new BinaryExpression(expression, unary(), BinaryOperator.MUL);
                continue;
            }
            if (match(TokenType.SLASH)) {
            	expression = new BinaryExpression(expression, unary(), BinaryOperator.DIV);
                continue;
            }
            if (match(TokenType.PERC)) {
            	expression = new BinaryExpression(expression, unary(), BinaryOperator.MOD);
                continue;
            }
            break;
        }
		return expression;
	}

	private Expression unary() {
		if (match(MINUS)) {
			return new UnaryExpression(unary(), UnaryOperator.NEG);
		} else if (match(LED)) {
			return new UnaryExpression(unary(), UnaryOperator.NEG);
		} else if (match(NOT)) {
			return new UnaryExpression(unary(), UnaryOperator.INV);
		}
		Expression expression = function(null);
		return expression;
	}
	// a().b().c.d().test
	private Expression function(Expression expression) {
		expression = index(expression);
		Token token = get(0);
		if (match(LPAREN)) {
			Expression callExpression = parseCall(expression);
			if (!look(DOT) && !look(RBRAC)) return callExpression;
			return function(callExpression);
		}
		return expression;
	}
	// a.b.c[123].d["text"][1].test
	private Expression index(Expression expression) {
		if (expression == null)
			expression = primary();
		
		if (match(DOT)) {
			Expression index = parseLiteral();	// WORD
			return new IndexExpression(expression, index);
		}  else if (match(LBRAK)) {
			Expression index = expression();
			consume(RBRAK);
			return new IndexExpression(expression, index);
		}
		return expression;
	}
	private Expression primary() {
		Token token = get(0);
		
		if (token.getTokenType().isValue()) {
			skip();
			return new PrimaryExpression((Literal) token);
		} else if (match(FUNCTION)) {
			Arguments arguments = parseArguments();
			Statement statement = match(EQRT) ? new ReturnStatement(expression()) : parseBlock();
			return new FunctionExpression(statement, arguments, new AnnotationsExpression(), false);
		} else if (look(WORD) && look(LBRAC, 1)) {
			consume(WORD);
			consume(LBRAC);
			Nodes expr;
			if (match(RBRAC)) {
				expr = new Nodes();
			} else {
				expr = parseNodes();
				consume(RBRAC);
			}
			return new StructExpression(expr, token.takeLiteral().getLiteral());
		} else if (look(LPAREN) && (look(GLOBAL, 1) || look(LOCAL, 1))) {
			return parseSpaceVariable();
		} else if (match(DLR)) {
			return new ParamsExpression();
		} else if (match(WORD)) {
			return new StackExpression(token.takeLiteral());
		}
		if (match(LPAREN)) {
			//if (match(RPAREN)) {
			//	return NIL_EXPRESSION; // () == null
			//}
			try {
				return expression();
			} finally {
				consume(RPAREN);
			}
		}
		if (look(LBRAC)) {
			return parseArray();
		}
		throw report("Uknown primary expression");
	}
	private Expression parseSpaceVariable() {
		consume(LPAREN);
		var current = get(0);
		var operator = switch (current.getTokenType()) {
			case GLOBAL: yield StackSpace.GLOBAL;
			case LOCAL: yield StackSpace.LOCAL;
			default:
				throw report("Uknown variable's space");
		};
		skip();
		consume(RPAREN);
		var word = consume(WORD);
		return new StackExpression(word.takeLiteral(), operator); 
	}
	
	private Expression parseArray() {
		consume(LBRAC);
		if (match(RBRAC)) {
			return new TableExpression(new Nodes());
		}
		if (findChild(EQ, RBRAC)) {
			Nodes expr = parseNodes();
			consume(RBRAC);
			return new TableExpression(expr);
		} else {
			Expression expr = parseExpressionOrTuple();
			consume(RBRAC);
			return new ArrayExpression(expr);
		}
	}
	private Expression parseCall(Expression expression) {
		Expression args;
		
		if (match(RPAREN)) {
			args = new TupleExpression();
		} else {
			args = parseTuple();
			consume(RPAREN);
		}
		return new CallExpression(expression, args);
	}
	private Statement parseCycle() {
		consume(CYCLE);
		Statement statement = spear(true, null, null);
		return new CycleStatement(statement);
	}
	private Statement parseLoop() {
		consume(LOOP);
		Statement statement = spear(true, DO, null);
		return new LoopStatement(statement);
	}
	private Statement parseWhile() {
		consume(WHILE);
		Expression expression = expression();
		Statement statement = spear(true, DO, null);
		return new WhileStatement(statement, expression);
	}
	private Statement parseIf() {
		consume(IF);
		Expression expression = expression();
		Statement statement = spear(true, THEN, ELSE);
		Statement unless = match(ELSE) ? spear(true, THEN) : null;
		return new IfStatement(statement, expression, unless);
	}
	private Statement parseFor() {
		consume(FOR);
		boolean closeParent = match(LPAREN);
		Statement statement = statement();
		consume(COMMA);
		Expression expression = expression();
		consume(COMMA);
		Statement statement2 = statement();
		if (closeParent) consume(RPAREN);
		Statement block = parseBlockOrStatement(DO);
		return new ForStatement(block, statement, statement2, expression);
	}
	private Statement parseForeach() {
		consume(FOREACH);
		boolean closeParent = match(LPAREN); // You can use the Foreach cycle with and without parents
		String keyMut, valueMut;
		keyMut = match(MINUS) ? "" : consumeWord();
		valueMut = match(COMMA) ? match(MINUS) ? "" : consumeWord() : "";
		consume(IN);
		Expression expression = expression();
		if (closeParent) consume(RPAREN);
		Statement block = parseBlockOrStatement(DO);
		return new ForeachStatement(keyMut, valueMut, expression, block);
	}
	
	private Expression parseNodeKey() {
		if (look(WORD)) {
			return parseLiteral();
		} else if (match(LBRAK)) {
			try {
				return expression();
			} finally {
				consume(RBRAK);
			}
		}
		throw new RuntimeException();
	}

	private Statement parseMask(AnnotationsExpression annotations) {
		consume(MASK);
		
		String name = consume(WORD).takeLiteral().getLiteral();
		
		return new MaskStatement(name, annotations);
	}

	private Statement parseObject(AnnotationsExpression annotations) {
		consume(OBJECT);
		Expression expr = index(null);
		
		if (!(expr instanceof Accessible)) throw report("Non accessible object's stub: " + expr);
		else if (!((Accessible)expr).isLiteral(null)) throw report("Non literal object's stub: " + expr);
		
		Arguments arguments = parseArguments();
		
		TupleExpression masks = match(WEARS) ? parseTuple() : null;
		
		
		return new DefineFieldStatement(expr, new ObjectExpression(((Accessible)expr).index(null), arguments, masks, annotations));
	}
	
	private Statement parseFunction(AnnotationsExpression annotations) {
		consume(FUNCTION);
		Expression expr = index(null);
		boolean isInstanceFunction = match(COLON);
		if (isInstanceFunction) expr = new IndexExpression(expr, parseLiteral());
		if (!(expr instanceof Accessible)) throw report("Non accessible function's stub: " + expr);
		
		Arguments arguments = parseArguments();
		Statement statement = match(EQRT) ?  new ReturnStatement(expression()) : parseBlock();
		
		return new DefineFieldStatement(expr, new FunctionExpression(statement, arguments, annotations, isInstanceFunction));
	}
	private Statement parseField() {
		consume(MUT);
		ArrayList<Expression> names = new ArrayList<>();
		do {
			if (match(MINUS)) {
				names.add(new FakeExpression());
				continue;
			}
			names.add(index(null));
		} while(match(COMMA, true));
		return new TupleFieldStatement(names.toArray(new Expression[names.size()]), match(EQ) ? parseExpressionOrTuple() : new TupleExpression());
	}
	private Statement parseReturn() {
		consume(RETURN);
		if (look(END)) return new ReturnStatement(null);
		return new ReturnStatement(parseExpressionOrTuple());
	}

	private Statement parseBreak() {
		consume(BREAK);
		var breakStatement = new BreakStatement();
		if (look(IF)) return parseShortIf(breakStatement);
		return breakStatement;
	}
	private Statement parseContinue() {
		consume(CONTINUE);
		var continueStatement = new ContinueStatement();
		if (look(IF)) return parseShortIf(continueStatement);
		return continueStatement;
	}

	private Statement parseShortIf(Statement statement) {
		consume(IF);
		Expression expression = expression();
		return new IfStatement(statement, expression, null);
	}
	private Statement parseImport() {
		consume(IMPORT);
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(consumeWord());
		
		while (match(DOT)) {
			buffer.append(".");
			buffer.append(consumeWord());
		}
		
		String aliasName = match(AS) ? consumeWord() : null;
		
		return new ImportStatement(buffer.toString(), aliasName);
	}
	private Statement parseExport() {
		consume(EXPORT);
		
		Expression expression = expression();
		
		String exportAlias = match(AS) ? consumeWord() : null;
		
		return new ExportStatement(expression, exportAlias);
	}
	private Statement parseAlias() {
		consume(ALIAS);
		Expression expression = expression();
		
		consume(AS);
		String aliasName = consumeWord();
		
		return new AliasStatement(expression, aliasName);
	}
	
	private AnnotationsExpression parseAnnotations() {
		AnnotationsExpression expr = new AnnotationsExpression();
		do {
			consume(AT);
			String name = consumeWord();
			
			Expression annotationArgument = match(LPAREN) ? expression() : NIL_EXPRESSION;
			
			if (annotationArgument != null) consume(RPAREN);
			
			expr.add(name, annotationArgument);
			
			
		} while(match(COMMA));
		return expr;
	}
	
	private TupleExpression parseTuple() {
		TupleExpression expr = new TupleExpression();
		do {
			if (look(WORD) && look(COLON, 1)) {
				String tupleElementMark = consumeWord();
				consume(COLON);
				expr.add(tupleElementMark, expression());
				continue;
			}
			expr.add(expression());
		} while(match(COMMA));
		return expr;
	}
	private Expression parseExpressionOrTuple() {
		Expression expr = expression();
		if (look(COMMA)) {
			TupleExpression tuple = new TupleExpression();
			tuple.add(expr);
			while(match(COMMA)) {
				tuple.add(expression());
			}
			return tuple;
		}
		return expr;
	}
	
	private Nodes parseNodes() {
		Nodes expr = new Nodes();
		do {
			Expression key = parseNodeKey();
			consume(EQ);
			expr.add(key, expression());
		} while(match(COMMA));
		return expr;
	}
	
	private Arguments parseArguments() {
		ArrayList<Argument> args = new ArrayList<>();
		boolean isFree = false;
		consume(LPAREN);
		while(!match(RPAREN)) {
			if (match(DLR)) {
				isFree = true;
				consume(RPAREN);
				break;
			}
			args.add(new Argument(consumeWord()));
			match(COMMA);
		}
		return new Arguments(args, isFree);
	}
	
	private Expression parseLiteral() {
		return new PrimaryExpression(consume(WORD));
	}

	
	private Statement spear(boolean stopAtEnd) {
		return spear(stopAtEnd, null, null);
	}
	
	private Statement spear(boolean stopAtEnd, TokenType stopAt) {
		return spear(stopAtEnd, null, stopAt);
	}

	private Statement spear(boolean stopAtEnd, TokenType gates, TokenType stopAt) {
		
		if (gates != null && match(gates)) return statement();
		
		ArrayList<Statement> statements = new ArrayList<>();
		
		while(!look(END)) {
			if (look(EOF))
				throw report("Unclosed block");
			else if (stopAt != null && look(stopAt))
				return statements.size() == 1 
					? statements.get(0) 
					: new BlockStatement(statements);
			
			statements.add(statement());
		}
		if (stopAtEnd && match(END));
		return statements.size() == 1 ? statements.get(0) : new BlockStatement(statements);
	}
	private boolean findChild(TokenType child, TokenType stop) {
		for (int i = position; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			if (t.match(stop)) {
				return false;
			}
			else if (t.match(child)) {
				return true;
			}
		}
		return false;
	}
	private Statement parseBlockOrStatement(TokenType gates) {
		if (match(gates)) {
			return statement();
		}
		return parseBlock();
	}
	private BlockStatement parseBlock() {
		BlockStatement statement = new BlockStatement();
		while(!match(END)) {
			if (look(EOF))
				throw report("Unclosed block");
			statement.add(statement());
		}
		return statement;
	}
	private <T extends Token> T get(int add) {
		int i = position+add;
		if (i >= max) throw report("Index out of bounds: " + i);
		return (T) tokens.get(i);
	}
	private String consumeWord() {
		return consume(WORD).takeLiteral().getLiteral();
	}
	private <T extends Token> T consume(TokenType type) {
		Token token = get(0);
		if (!token.match(type)) throw report(String.format("Cannot be consume %s because it's %s", type, token));
		position++;
		switchPosition(get(0));
		return (T) token;
	}
	private boolean match(TokenType type) {
		Token token = get(0);
		if (!token.match(type)) return false;
		position++;
		if (type != EOF) switchPosition(get(0));
		return true;
	}
	private boolean match(TokenType type, boolean catchEnd) {
		Token token = get(0);
		if (!token.match(type)) return false;
		position++;
		if (type != EOF) switchPosition(get(0));
		else throw new RuntimeException();
		return true;
	}
	private boolean skip() {
		position++;
		switchPosition(get(0));
		return true;
	}
	private boolean look(TokenType type) {
		return look(type, 0);
	}
	private boolean look(TokenType type, int add) {
		return get(add).match(type);
	}
	private void switchPosition(Token add) {
		row = add.getLineStart();
		col = add.getColumnStart();
	}
	private ParserError report(String s) {
		return new ParserError(String.format("\"%s\" at [row=%s,col=%s] of %s", s, row, col, file.getName()));
	}
}
