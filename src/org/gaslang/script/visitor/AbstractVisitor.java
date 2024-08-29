package org.gaslang.script.visitor;

import org.gaslang.script.Expression;
import org.gaslang.script.Statement;
import org.gaslang.script.ast.*;

public abstract class AbstractVisitor implements Visitor
{

	@Override
	public void visit(BlockStatement blockStatement) {
		for (Statement statement : blockStatement.statements) {
			statement.accept(this);
		}
	}

	@Override
	public void visit(PrimaryExpression primaryExpression) {
	}

	@Override
	public void visit(StackExpression stackExpression) {
	}

	@Override
	public void visit(TupleExpression tupleExpression) {
		for (Expression expression : tupleExpression.expressions) {
			expression.accept(this);
		}
	}

	@Override
	public void visit(ReturnStatement returnStatement) {
		if (returnStatement.expression == null) return;
		returnStatement.expression.accept(this);
	}

	@Override
	public void visit(PassStatement passStatement) {
	}

	@Override
	public void visit(CallExpression callExpression) {
		callExpression.value.accept(this);
		callExpression.expression.accept(this);
	}

	@Override
	public void visit(EvalStatement evalStatement) {
		evalStatement.expression.accept(this);
	}

	@Override
	public void visit(TupleFieldStatement tupleFieldStatement) {
		for (int i = 0, l = tupleFieldStatement.names.length; i < l; i++) {
			tupleFieldStatement.names[i].accept(this);
		}
		if (tupleFieldStatement.expression == null) return;
		tupleFieldStatement.expression.accept(this);
	}

	@Override
	public void visit(ParamsExpression paramsExpression) {
	}

	@Override
	public void visit(WhileStatement whileStatement) {
		whileStatement.expression.accept(this);
		whileStatement.block.accept(this);
	}

	@Override
	public void visit(IfStatement ifStatement) {
		ifStatement.expression.accept(this);
		ifStatement.block.accept(this);
		if (ifStatement.unless == null) return;
		ifStatement.unless.accept(this);
	}

	@Override
	public void visit(FunctionStatement functionStatement) {
	}

	@Override
	public void visit(FunctionExpression functionExpression) {
		functionExpression.annotations.accept(this);
		functionExpression.statement.accept(this);
	}

	@Override
	public void visit(Nodes nodesExpression) {
		for (Expression expression : nodesExpression.nodes.keySet()) {
			expression.accept(this);
			nodesExpression.nodes.get(expression).accept(this);
		}
	}

	@Override
	public void visit(ContinueStatement continueStatement) {
	}

	@Override
	public void visit(BreakStatement breakStatement) {
	}

	@Override
	public void visit(BinaryExpression binaryExpression) {
		binaryExpression.expr1.accept(this);
		binaryExpression.expr2.accept(this);
	}

	@Override
	public void visit(UnaryExpression unaryExpression) {
		unaryExpression.expr.accept(this);
	}

	@Override
	public void visit(ConditionalExpression conditionalExpression) {
		conditionalExpression.expr1.accept(this);
		conditionalExpression.expr2.accept(this);
	}

	@Override
	public void visit(DefineFieldStatement defineFieldStatement) {
		defineFieldStatement.expression1.accept(this);
		defineFieldStatement.expression2.accept(this);
	}

	@Override
	public void visit(DefineFieldExpression defineFieldExpression) {
		defineFieldExpression.expression1.accept(this);
		defineFieldExpression.expression2.accept(this);
	}

	@Override
	public void visit(IndexExpression indexExpression) {
		indexExpression.expression1.accept(this);
		indexExpression.expression2.accept(this);
	}

	@Override
	public void visit(TableExpression tableExpression) {
		for (Expression expression : tableExpression.values.keySet()) {
			expression.accept(this);
			tableExpression.values.get(expression).accept(this);
		}
	}

	@Override
	public void visit(AnnotationsExpression annotationsExpression) {
		for (Expression expression : annotationsExpression.nodes.values()) {
			expression.accept(this);
		}
	}

	@Override
	public void visit(ForStatement forStatement) {
		forStatement.block.accept(this);
		forStatement.expression.accept(this);
		forStatement.par1.accept(this);
		forStatement.par2.accept(this);
	}

	@Override
	public void visit(ArrayExpression arrayExpression) {
		arrayExpression.value.accept(this);
	}

	@Override
	public void visit(StructExpression structExpression) {
		for (Expression expression : structExpression.values.keySet()) {
			expression.accept(this);
			structExpression.values.get(expression).accept(this);
		}
	}

	@Override
	public void visit(ForeachStatement foreachStatement) {
		foreachStatement.blockStatement.accept(this);
		foreachStatement.collection.accept(this);
	}

	@Override
	public void visit(AliasStatement aliasStatement) {
		aliasStatement.expression.accept(this);
	}

	@Override
	public void visit(ImportStatement importStatement) {
		importStatement.accept(this);
	}

	@Override
	public void visit(ExportStatement exportStatement) {
		exportStatement.expression.accept(this);
	}

	@Override
	public void visit(ObjectExpression objectExpression) {
		objectExpression.masks.accept(this);
		objectExpression.annotations.accept(this);
	}

	@Override
	public void visit(LoopStatement loopStatement) {
		loopStatement.block.accept(this);
	}
	
	@Override
	public void visit(CycleStatement cycleStatement) {
		cycleStatement.block.accept(this);
	}

	@Override
	public void visit(FakeExpression fakeExpression) {
	}

	@Override
	public void visit(MaskStatement maskStatement) {
		maskStatement.annotations.accept(this);
	}

	@Override
	public void visit(ElvisExpression elvisExpression) {
		elvisExpression.expression.accept(this);
	}
}
