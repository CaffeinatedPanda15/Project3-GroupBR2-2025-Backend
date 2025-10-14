package za.ac.cput.domain.parent;

import jakarta.persistence.*;

@Entity
public class ChildSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int childSessionId;

    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private ChildSittingSession session;

    protected ChildSession() {}

    private ChildSession(Builder builder) {
        this.childSessionId = builder.childSessionId;
        this.child = builder.child;
        this.session = builder.session;
    }

    public int getChildSessionId() { return childSessionId; }
    public Child getChild() { return child; }
    public ChildSittingSession getSession() { return session; }

    @Override
    public String toString() {
        return "ChildSession{" +
                "childSessionId=" + childSessionId +
                ", child=" + child +
                ", session=" + session +
                '}';
    }

    public static class Builder {
        private int childSessionId;
        private Child child;
        private ChildSittingSession session;

        public Builder setChildSessionId(int childSessionId) { this.childSessionId = childSessionId; return this; }
        public Builder setChild(Child child) { this.child = child; return this; }
        public Builder setSession(ChildSittingSession session) { this.session = session; return this; }

        public ChildSession build() { return new ChildSession(this); }

        public Builder copy(ChildSession childSession) {
            this.childSessionId = childSession.childSessionId;
            this.child = childSession.child;
            this.session = childSession.session;
            return this;
        }
    }// End of Builder class
}// End of class
