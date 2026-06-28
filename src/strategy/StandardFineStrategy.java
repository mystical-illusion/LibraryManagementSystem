package strategy;

import models.Member;

public class StandardFineStrategy implements FineStrategy {
    public double calculateFine(Member member, int daysLate) {
        if (daysLate <= 0) {
            return 0;
        }
        return daysLate * member.getFineRate();
    }
}