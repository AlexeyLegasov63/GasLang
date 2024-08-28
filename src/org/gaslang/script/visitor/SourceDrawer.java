package org.gaslang.script.visitor;

import org.gaslang.script.Script;
import org.gaslang.script.api.ScriptAPI.StackSpace;
import org.gaslang.script.ast.*;
import org.gaslang.script.parser.lexer.token.TokenType;

import java.util.Arrays;
import java.util.Objects;

public class SourceDrawer extends AbstractVisitor
{
	private final StringBuffer buffer = new StringBuffer();

	private int block = 0;
	
	private void write(Object... objects) {
		for (Object object : objects) {
			buffer.append(Objects.toString(object));
		}
	}
	
	public String result() {
		return buffer.toString();
	}
	
	private void reset() {
		write('\n');
		var tabs = new Object[block];
		Arrays.fill(tabs, "  ");
		write(tabs);
	}

	@Override
	public void visit(BlockStatement blockStatement) {
		write('{');
		block += 1;
		blockStatement.statements.forEach(s -> {
			reset();
			s.accept(this);
		});
		block -= 1;
		reset();
		write("}");
	}

	@Override
	public void visit(PrimaryExpression primaryExpression) {
		if (primaryExpression.token.match(TokenType.STRING)) {
			write('"', primaryExpression.token.getLiteral(), '"');
			return;
		} else if (primaryExpression.token.match(TokenType.CHARACTER)) {
			write('\'', primaryExpression.token.getLiteral(), '\'');
			return;
		}
		write(primaryExpression.token.getLiteral());
	}

	@Override
	public void visit(StackExpression stackExpression) {
		if (stackExpression.space != StackSpace.CURRENT)
			write('(', stackExpression.space, ')');
		write(stackExpression.token.getLiteral());
	}

	@Override
	public void visit(TupleExpression tupleExpression) {
		for (int i = 0, l = tupleExpression.expressions.size(); i < l; i++) {
			tupleExpression.expressions.get(i).accept(this);
			if (i+1 != l) write(", ");
		}
	}

	@Override
	public void visit(ReturnStatement returnStatement) {
		write("return ");
		super.visit(returnStatement);
	}

	@Override
	public void visit(PassStatement passStatement) {
		write("pass ");
	}

	@Override
	public void visit(CallExpression callExpression) {
		callExpression.value.accept(this);
		write('(');
		callExpression.expression.accept(this);
		write(')');
	}

	@Override
	public void visit(EvalStatement evalStatement) {
		super.visit(evalStatement);
	}

	@Override
	public void visit(TupleFieldStatement tupleFieldStatement) {
		write("mut ");
		for (int i = 0, l = tupleFieldStatement.names.length; i < l; i++) {
			tupleFieldStatement.names[i].accept(this);
			if (i+1 != l) write(", ");
		}
		if (tupleFieldStatement.expression == null || tupleFieldStatement.expression instanceof TupleExpression && ((TupleExpression)tupleFieldStatement.expression).expressions.size() == 0) return;
		write(" = ");
		tupleFieldStatement.expression.accept(this);
	}

	@Override
	public void visit(ParamsExpression paramsExpression) {
		write("$ ");
	}

	@Override
	public void visit(WhileStatement whileStatement) {
		write("while ");
		whileStatement.expression.accept(this);
		write(" do ");
		whileStatement.block.accept(this);
	}

	@Override
	public void visit(IfStatement ifStatement) {
		write("if ");
		ifStatement.expression.accept(this);
		write(" then ");
		ifStatement.block.accept(this);
		if (ifStatement.unless == null) return;
		write(" else ");
		ifStatement.unless.accept(this);
	}

	@Override
	public void visit(FunctionStatement functionStatement) {
	}

	@Override
	public void visit(FunctionExpression functionExpression) {
		functionExpression.annotations.accept(this);
		write("function");
		if (functionExpression.isInstanceFunction) write(':');
		write('(');
		functionExpression.arguments.forEach(arg -> write(arg.getName()));
		write(") ");
		functionExpression.statement.accept(this);
	}

	@Override
	public void visit(Nodes nodes) {
		nodes.nodes.forEach((k,v) -> {
			k.accept(this);
			write(" = ");
			v.accept(this);
		});
	}

	@Override
	public void visit(ContinueStatement continueStatement) {
		write("continue");
	}

	@Override
	public void visit(BreakStatement breakStatement) {
		write("break");
	}

	@Override
	public void visit(BinaryExpression binaryExpression) {
		binaryExpression.expr1.accept(this);
		write(' ', binaryExpression.operator.toString(), ' ');
		binaryExpression.expr2.accept(this);
	}

	@Override
	public void visit(UnaryExpression unaryExpression) {
		write(unaryExpression.operator.toString(), ' ');
		unaryExpression.expr.accept(this);
	}

	@Override
	public void visit(ConditionalExpression conditionalExpression) {
		conditionalExpression.expr1.accept(this);
		write(' ', conditionalExpression.operator.toString(), ' ');
		conditionalExpression.expr2.accept(this);
	}

	@Override
	public void visit(DefineFieldStatement defineFieldStatement) {
		defineFieldStatement.expression1.accept(this);
		write(" = ");
		defineFieldStatement.expression2.accept(this);
	}

	@Override
	public void visit(DefineFieldExpression defineFieldExpression) {
		defineFieldExpression.expression1.accept(this);
		write(" = ");
		defineFieldExpression.expression2.accept(this);
	}

	@Override
	public void visit(IndexExpression indexExpression) {
		indexExpression.expression1.accept(this);
		write('.');
		indexExpression.expression2.accept(this);
	}

	@Override
	public void visit(TableExpression tableExpression) {
		write('{');
		tableExpression.values.forEach((k,v) -> {
			k.accept(this);
			write("; ");
			v.accept(this);
		});
		write('}');
	}

	@Override
	public void visit(AnnotationsExpression annotationsExpression) {
		annotationsExpression.nodes.forEach((k,v) -> {
			write('@', k, '(');
			if (v != null) {
				v.accept(this);
			}
			write(") ");
		});
	}

	@Override
	public void visit(ForStatement forStatement) {
		write("for ");
		
		forStatement.par1.accept(this);
		write(" ; ");
		forStatement.expression.accept(this);
		write(" ; ");
		forStatement.par2.accept(this);
		
		write(" do ");
		
		forStatement.block.accept(this);
	}

	@Override
	public void visit(ArrayExpression arrayExpression) {
		write('{');
		super.visit(arrayExpression);
		write('}');
	}

	@Override
	public void visit(StructExpression structExpression) {
		write(structExpression.name, " {");
		structExpression.values.forEach((k,v) -> {
			k.accept(this);
			write("; ");
			v.accept(this);
		});
		write('}');
	}

	@Override
	public void visit(ForeachStatement foreachStatement) {
		write("foreach ", foreachStatement.key, ", ", foreachStatement.value, " in ");
		foreachStatement.collection.accept(this);
		write(" do ");
		foreachStatement.blockStatement.accept(this);
	}

	@Override
	public void visit(AliasStatement aliasStatement) {
		write("alias ");
		super.visit(aliasStatement);
		write(" as ", aliasStatement.aliasName);
	}

	@Override
	public void visit(ImportStatement importStatement) {
		write("import ", importStatement.fileName);
		if (importStatement.importAlias == null) return;
		write(" as ", importStatement.importAlias);
	}

	@Override
	public void visit(ExportStatement exportStatement) {
		write("export ");
		super.visit(exportStatement);
		write(" as ", exportStatement.exportAlias);
	}

	@Override
	public void visit(ObjectExpression objectExpression) {
		objectExpression.annotations.accept(this);
		write("object ", objectExpression.name, '(');
		objectExpression.arguments.forEach(arg -> {
			write(arg.getName(), "; ");
		});
		write(") wears ");
		objectExpression.masks.accept(this);
	}

	@Override
	public void visit(LoopStatement loopStatement) {
		write("loop ");
		super.visit(loopStatement);
	}

	@Override
	public void visit(CycleStatement cycleStatement) {
		write("cycle ");
		super.visit(cycleStatement);
	}
	
	@Override
	public void visit(FakeExpression fakeExpression) {
		write("fake ");
	}

	@Override
	public void visit(MaskStatement maskStatement) {
		maskStatement.annotations.accept(this);
		write("mask ", maskStatement.name);
	}

	@Override
	public void visit(Script script) {
		script.statements.forEach(s -> {
			s.accept(this);
			write("\n\r");
		});
	}
}
