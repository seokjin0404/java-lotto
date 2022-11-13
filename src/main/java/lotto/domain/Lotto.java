package lotto.domain;

import java.util.List;

public class Lotto {
    private static final int LOTTO_SIZE = 6;
    private static final int MAXIMUM_NUMBER = 45;
    private static final int MINIMUM_NUMBER = 1;
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validateOverSize(numbers);
        validateDuplicate(numbers);
        validateOverNumber(numbers);
        this.numbers = numbers;
    }

    public static Lotto from(List<Integer> lotto) {
        return new Lotto(lotto);
    }

    private void validateOverSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_SIZE) {
            throw new IllegalArgumentException("6개의 숫자를 입력해주세요.");
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        if (numbers.stream().distinct().count() != LOTTO_SIZE) {
            throw new IllegalArgumentException("중복되지 않은 숫자를 입력해주세요");
        }
    }
    private void validateOverNumber(List<Integer> numbers) {
        if (numbers.stream().anyMatch(this::outOfRange)) {
            throw new IllegalArgumentException("1이상 45이하의 숫자를 입력해주세요.");
        }
    }

    boolean outOfRange(int number) {
        return number>MAXIMUM_NUMBER || number<MINIMUM_NUMBER;
    }

    public boolean contains(int number) {
        return numbers.contains(number);
    }

    public Result play(WinningNumber winningNumber) {
        if (count(winningNumber) == 5 && hasSameBonusNumber(winningNumber))
            return Result.FIVE_BONUS;
        return Result.valueOf(numbers.stream()
                .filter(winningNumber::contains)
                .count());
    }
    private boolean hasSameBonusNumber(WinningNumber winningNumber) {
        return numbers.stream()
                .anyMatch(winningNumber::sameBonusNumber);
    }
    private int count(WinningNumber winningNumber) {
        return (int) numbers.stream()
                .filter(winningNumber::contains)
                .count();
    }
}
