package org.gaslang.script.ast;

import java.util.ArrayList;

import org.gaslang.script.*;
import org.gaslang.script.Iterable;
import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ForeachStatement implements Statement
{
	public String key, value;
	public Expression collection;
	public Statement blockStatement;
	
	public ForeachStatement(String key, String value, Expression collection, Statement blockStatement) {
		this.key = key;
		this.value = value;
		this.collection = collection;
		this.blockStatement = blockStatement;
	}

	@Override
	public void execute(GasRuntime gr) {
		gr.push();
		Iterable iterable = (Iterable)collection.eval(gr);
		for (Object keyObject : iterable.getMap().keySet()) {
			gr.set(key, ScriptAPI.value(keyObject));
			gr.set(value, (Value<?>) iterable.getMap().get(keyObject));
			try {
				blockStatement.execute(gr);
			} catch(ContinueStatement s) {
				continue;
			} catch(BreakStatement s) {
				break;
			}
		}
		gr.pop();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
