package org.gaslang.script;

import java.util.*;

import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.ast.*;
import org.gaslang.script.run.*;

public class FunctionValue extends Value<Statement>
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
			var argumentName = functionArguments.getArguments().get(par).getName();

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
			// Tail of values which are out of bounds
			localStack.setLocal("$", args.subTuple(arg));
		}
	}
	
	@Override
	public Value<?> call(Tuple args) {
		localStack.push();
		
		loadArguments(args);
		
		try {
			functionStatement.execute(localStack);
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
	
	public String getName() {
		return functionName;
	}

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
