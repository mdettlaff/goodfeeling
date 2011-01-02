package goodfeeling.userstate;

import java.util.Random;

public class FakeRandom extends Random {

	private int operator;
	private int currentNumberIndex = 0;
	private int[] numbers = {4, 4, 3, 2, 4, 4};

	@Override
	public int nextInt(int n) {
		if (n == Balloon.Operator.values().length) {
			return operator++ % n;
		}
		return numbers[currentNumberIndex++] % n;
	}
}
