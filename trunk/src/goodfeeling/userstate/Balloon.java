package goodfeeling.userstate;

import goodfeeling.util.Pair;

public class Balloon {

	public enum Operator {
		PLUS {
			@Override
			public int getResult(int x, int y) {
				return x + y;
			}
		},
		MINUS {
			@Override
			public int getResult(int x, int y) {
				return x - y;
			}
		},
		TIMES {
			@Override
			public int getResult(int x, int y) {
				return x * y;
			}
		},
		DIVIDE {
			@Override
			public int getResult(int x, int y) {
				return (int)((double)x) / y;
			}
		},
		;

		public abstract int getResult(int x, int y);
	}

	public final Operator operator;
	public final Pair<Integer, Integer> numbers;

	public Balloon(Pair<Integer, Integer> numbers, Operator operator) {
		this.numbers = numbers;
		this.operator = operator;
	}

	public boolean checkAnswer(int answer) {
		return operator.getResult(numbers.getFirst(), numbers.getSecond()) == answer;
	}
}
