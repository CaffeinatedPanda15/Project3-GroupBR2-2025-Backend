package za.ac.cput.factories;

import za.ac.cput.domain.Payment;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.domain.parent.ChildSittingSession;

public class PaymentFactory {

    public static Payment createPayment(Parent parent, ChildSittingSession session, double amount, int timeStamp) {
        return new Payment.Builder()
                .setParent(parent)
                .setSession(session)
                .setAmount(amount)
                .setTimeStamp(timeStamp)
                .build();
    }
}
