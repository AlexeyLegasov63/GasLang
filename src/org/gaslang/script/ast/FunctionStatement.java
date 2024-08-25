package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class FunctionStatement implements Statement
{
	public Statement statement;
	public Arguments arguments;
	public Annotations annotations;
	public String type;
	public Expression source;

	public FunctionStatement(Statement statement, Arguments arguments, Annotations annotations, Expression source) {
		this(statement, arguments, annotations, source, "any");
	}
	
	public FunctionStatement(Statement statement, Arguments arguments, Annotations annotations, Expression source, String type) {
		this.statement = statement;
		this.arguments = arguments;
		this.annotations = annotations;
		this.source = source;
		this.type = type;
	}

	@Override
	public void execute(GasRuntime gr) {
		
		//FunctionValue value = new FunctionValue(name, type, annotations, arguments, statement);
		//gr.set(name, value);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
