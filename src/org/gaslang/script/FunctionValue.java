package org.gaslang.script;

import org.gaslang.script.ast.ReturnStatement;
import org.gaslang.script.run.GasRuntime;

public class FunctionValue extends Value<Statement> implements Annotated, PrimitiveValue, Named
{
	public final GasRuntime localStack;
	private final String functionName;
	private final Arguments functionArguments;
	private final Annotations functionAnnotations;
	private final Statement functionStatement;
	
	public FunctionValue(GasRuntime localStack, String name, Annotations annotations, Arguments arguments, Statement statement) {
		super(statement, ValueType.FUNCTION);
		this.localStack = localStack;
		this.functionName = name;
		this.functionArguments = arguments;
		this.functionStatement = statement;
		this.functionAnnotations = annotations;
	}

	protected void loadArguments(Tuple args) {
		int arg = 0, arguments = args.getSize();
		
		for (int par = 0, params = functionArguments.getLength(); par < params; par++) {
			var argumentName = functionArguments.getArguments().get(par).name();

			Value<?> argumentValue;
			
			if (args.hasValue(argumentName)) {
				argumentValue = args.getValue(argumentName);
			} else if (arg < arguments) {
				argumentValue = args.getValue(arg++);
			} else {
				argumentValue = NullValue.NIL_VALUE;
			}
			
			localStack.setLocal(argumentName, argumentValue);
		}

		if (arg < arguments) {
			localStack.setLocal("<VarArgs>", args.subTuple(arg));
		}
	}
	
	@Override
	public synchronized Value<?> call(GasRuntime gasRuntime, Tuple args) {
		localStack.append(gasRuntime);

		localStack.push();
		
		loadArguments(args);
		
		try {
			functionStatement.execute(new GasRuntime(localStack));
			localStack.pop();
		} catch(ReturnStatement t) {
			localStack.pop();
			return t.value == null ? NullValue.NIL_VALUE : t.value;
		} catch(Throwable t) {
			localStack.pop();
			throw t;
		}
		return NullValue.NIL_VALUE;
	}

	@Override
	public String asString() {
		return "function";
	}

	@Override
	public String getName() {
		return functionName;
	}

	@Override
	public Annotations getAnnotations() {
		return functionAnnotations;
	}
	
	public Arguments getArguments() {
		return functionArguments;
	}

	public Statement getStatement() {
		return functionStatement;
	}
}
