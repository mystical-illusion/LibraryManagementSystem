package strategy;

import models.Member;

public class GracePeriodFineStrategy implements FineStrategy {
    private static final int GRACE_DAYS = 3;

    public double calculateFine(Member member, int daysLate) {
        if (daysLate <= GRACE_DAYS) {
            return 0; // within grace period, no fine!
        }
        int chargeableDays = daysLate - GRACE_DAYS;
        return chargeableDays * member.getFineRate();
    }
}