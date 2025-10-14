package za.ac.cput.domain;

import jakarta.persistence.*;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    private double amount;
    private int timeStamp;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private ChildSittingSession session;

    protected Payment() {}

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.amount = builder.amount;
        this.timeStamp = builder.timeStamp;
        this.parent = builder.parent;
        this.session = builder.session;
    }

    public int getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public int getTimeStamp() { return timeStamp; }
    public Parent getParent() { return parent; }
    public ChildSittingSession getSession() { return session; }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", timeStamp=" + timeStamp +
                ", parent=" + parent +
                ", session=" + session +
                '}';
    }

    public static class Builder {
        private int paymentId;
        private double amount;
        private int timeStamp;
        private Parent parent;
        private ChildSittingSession session;

        public Builder setPaymentId(int paymentId) { this.paymentId = paymentId; return this; }
        public Builder setAmount(double amount) { this.amount = amount; return this; }
        public Builder setTimeStamp(int timeStamp) { this.timeStamp = timeStamp; return this; }
        public Builder setParent(Parent parent) { this.parent = parent; return this; }
        public Builder setSession(ChildSittingSession session) { this.session = session; return this; }

        public Payment build() { return new Payment(this); }

        public Builder copy(Payment payment) {
            this.paymentId = payment.paymentId;
            this.amount = payment.amount;
            this.timeStamp = payment.timeStamp;
            this.parent = payment.parent;
            this.session = payment.session;
            return this;
        }
    }//end of Builder class
}//end of class
