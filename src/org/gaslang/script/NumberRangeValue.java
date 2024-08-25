package org.gaslang.script;

import java.util.Objects;

public class NumberRangeValue extends Value<NumberRangeValue.Entry>
{
	public NumberRangeValue(Number arg0, Number arg1) {
		super(new Entry(arg0, arg1));
	}

	@Override
	public Number asNumber() {
		return jValue().max.doubleValue() - jValue().min.doubleValue();
	}

	@Override
	public String asString() {
		return String.format("%s to %s", jValue().min, jValue().max);
	}
	
	@Override
	public Value<?> negate() {
		return new NumberRangeValue(-jValue().min.doubleValue(), -jValue().max.doubleValue());
	}
	
	
	public static class Entry
	{
		private final Number min, max;

		public Entry(Number min, Number max) {
			this.min = min;
			this.max = max;
		}
		
		public Number getMin() {
			return min;
		}

		public Number getMax() {
			return max;
		}

		@Override
		public int hashCode() {
			return Objects.hash(max, min);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			return max == other.max && min == other.min;
		}
	}
}
