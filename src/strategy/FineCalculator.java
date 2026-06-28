package strategy;

import models.Member;

public class FineCalculator {
    private FineStrategy strategy;

    public FineCalculator(FineStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(FineStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(Member member, int daysLate) {
        return strategy.calculateFine(member, daysLate);
    }
}