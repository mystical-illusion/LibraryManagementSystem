package strategy;

import models.Member;

public interface FineStrategy {
    double calculateFine(Member member, int daysLate);
}