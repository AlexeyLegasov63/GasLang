package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.api.ScriptAPI.ConditionalyOperator;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ConditionalExpression implements Expression
{
	public Expression expr1, expr2;
	public ConditionalyOperator operator;
	
	public ConditionalExpression(Expression expr1, Expression expr2, ConditionalyOperator operator) {
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.operator = operator;
	}

	@Override
	public Value<?> eval(GasRuntime gr) {
		Value<?> val1 = expr1.eval(gr), val2;
		
		if (operator.equals(ConditionalyOperator.AND) && !val1.asBoolean()) {
			return ScriptAPI.FALSE;
		} else if (operator.equals(ConditionalyOperator.OR) && val1.asBoolean()) {
			return val1;
		}
	// Don't eval the second value if the first one equals false
		val2 = expr2.eval(gr);
		
		switch(operator) {
			case OR:
				return !val1.asBoolean() ? !val2.asBoolean() ? ScriptAPI.NULL : val2 : val1;
			case AND:
				return val1.asBoolean() && val2.asBoolean() ? val2 : ScriptAPI.NULL;
			case WEARS:
				return val1.wears(val2);
			case INSTANCEOF:
				return val1.instanceOf(val2);
			case EQ:
				return val1.equals(val2);
			case EQLS:
				return val1.equalsOrLess(val2);
			case EQMR:
				return val1.equalsOrMore(val2);
			case LS:
				return val1.lessThan(val2);
			case MR:
				return val1.moreThan(val2);
			case NQ:
				return val1.notEquals(val2);
			default:
				break;
		}
		throw new RuntimeException();
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
