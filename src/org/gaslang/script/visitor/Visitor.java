package org.gaslang.script.visitor;

import org.gaslang.script.Script;
import org.gaslang.script.ast.*;

public interface Visitor
{

	void visit(BlockStatement blockStatement);

	void visit(PrimaryExpression primaryExpression);

	void visit(StackExpression stackExpression);

	void visit(TupleExpression tupleExpression);

	void visit(ReturnStatement returnStatement);

	void visit(PassStatement passStatement);

	void visit(CallExpression callExpression);

	void visit(EvalStatement evalStatement);

	void visit(TupleFieldStatement tupleFieldStatement);

	void visit(ParamsExpression paramsExpression);

	void visit(WhileStatement whileStatement);

	void visit(IfStatement ifStatement);

	void visit(FunctionStatement functionStatement);

	void visit(FunctionExpression functionExpression);

	void visit(Nodes nodesExpression);

	void visit(ContinueStatement continueStatement);

	void visit(BreakStatement breakStatement);

	void visit(BinaryExpression binaryExpression);

	void visit(UnaryExpression unaryExpression);

	void visit(ConditionalExpression conditionalExpression);

	void visit(DefineFieldStatement defineFieldStatement);

	void visit(DefineFieldExpression defineFieldExpression);

	void visit(IndexExpression indexExpression);

	void visit(TableExpression tableExpression);

	void visit(AnnotationsExpression annotationsExpression);

	void visit(ForStatement forStatement);

	void visit(ArrayExpression arrayExpression);

	void visit(StructExpression structExpression);

	void visit(ForeachStatement foreachStatement);

	void visit(AliasStatement aliasStatement);

	void visit(ImportStatement importStatement);

	void visit(ExportStatement exportStatement);

	void visit(ObjectExpression objectExpression);

	void visit(LoopStatement loopStatement);

	void visit(FakeExpression fakeExpression);

	void visit(MaskStatement maskStatement);

	void visit(Script script);

	void visit(CycleStatement cycleStatement);

	void visit(ElvisExpression elvisExpression);
}
