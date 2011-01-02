package goodfeeling.userstate;

import goodfeeling.util.Pair;

public class Balloon {

	public enum Operator {
		PLUS,
		MINUS,
		TIMES,
		DIVIDE,
		;

		public int getResult(int x, int y) {
			switch (this) {
			case PLUS:
				return x + y;
			case MINUS:
				return x - y;
			case TIMES:
				return x * y;
			case DIVIDE:
				return x / y;
			}
			return 0;
		}
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
