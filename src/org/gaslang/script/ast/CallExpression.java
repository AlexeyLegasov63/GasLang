package org.gaslang.script.ast;

import org.gaslang.script.*;
import org.gaslang.script.run.GasRuntime;
import org.gaslang.script.visitor.Visitor;

public class CallExpression extends OperandExpression
{
	public final Expression valueExpressionToCall, callArgumentsExpression;

	public CallExpression(Position position, Expression valueExpressionToCall, Expression callArgumentsExpression) {
		super(position);
		this.valueExpressionToCall = valueExpressionToCall;
		this.callArgumentsExpression = callArgumentsExpression;
	}

	@Override
	public Value<?> eval(GasRuntime gasRuntime) {
		try {
			var value = valueExpressionToCall.eval(gasRuntime);
			if (value.isNull()) {
				throw gasRuntime.error(getPosition(), "Non-valid value for call expression: null");
			}

			gasRuntime.enter(getPosition(), valueExpressionToCall.toString() + "()");

			var tuple = Tuple.valueOf(callArgumentsExpression.eval(gasRuntime));

			if (value instanceof InstanceFunctionValue) {
				tuple.addFirst(((IndexExpression)valueExpressionToCall).indexExpression.eval(gasRuntime));
			}

			var returnValue = value.call(gasRuntime, tuple);

			gasRuntime.exit();

			return returnValue;
		} catch (ElvisExpression.ElvisNonMatched ignored) {

		}
		return NullValue.NIL_VALUE;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return valueExpressionToCall + "(" + callArgumentsExpression + ")";
	}
}
