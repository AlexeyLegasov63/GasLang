package org.gaslang.script.ast;

import org.gaslang.script.Expression;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptAPI;
import org.gaslang.script.api.ScriptAPI.ConditionalyOperator;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class ConditionalExpression extends OperatorExpression
{
	public Expression expression1, expression2;
	public ConditionalyOperator operator;
	
	public ConditionalExpression(Expression expression1, Expression expression2, ConditionalyOperator operator) {
		super(expression1);
		this.expression1 = expression1;
		this.expression2 = expression2;
		this.operator = operator;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		Value<?> value1 = expression1.eval(gasRuntime), value2;
		
		if (operator.equals(ConditionalyOperator.AND) && !value1.asBoolean()) {
			return ScriptAPI.NULL;
		} else if (operator.equals(ConditionalyOperator.OR) && value1.asBoolean()) {
			return value1;
		}
	// Don't eval the second value if the first one equals false
		value2 = expression2.eval(gasRuntime);

		return switch (operator) {
			case OR -> !value1.asBoolean() ? !value2.asBoolean() ? ScriptAPI.NULL : value2 : value1;
			case AND -> value1.asBoolean() && value2.asBoolean() ? value2 : ScriptAPI.NULL;
			case WEARS -> value1.wears(value2);
			case INSTANCEOF -> value1.instanceOf(value2);
			case EQ -> value1.equals(value2);
			case EQLS -> value1.equalsOrLess(value2);
			case EQMR -> value1.equalsOrMore(value2);
			case LS -> value1.lessThan(value2);
			case MR -> value1.moreThan(value2);
			case NQ -> value1.notEquals(value2);
			default -> throw gasRuntime.error(getPosition(), "Unknown conditional operator");
		};
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
