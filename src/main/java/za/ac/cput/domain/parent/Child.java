package za.ac.cput.domain.parent;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int childId;
    private String childName;
    private String childSurname;
    private int childAge;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChildSession> childSessions = new HashSet<>();

    protected Child() {}

    private Child(Builder builder) {
        this.childId = builder.childId;
        this.childName = builder.childName;
        this.childSurname = builder.childSurname;
        this.childAge = builder.childAge;
        this.parent = builder.parent;
        this.childSessions = builder.childSessions;
    }

    public int getChildId() { return childId; }
    public String getChildName() { return childName; }
    public String getChildSurname() { return childSurname; }
    public int getChildAge() { return childAge; }
    public Parent getParent() { return parent; }
    public Set<ChildSession> getChildSessions() { return childSessions; }

    @Override
    public String toString() {
        return "Child{" +
                "childId=" + childId +
                ", childName='" + childName + '\'' +
                ", childSurname='" + childSurname + '\'' +
                ", childAge=" + childAge +
                ", parent=" + parent +
                '}';
    }

    public static class Builder {
        private int childId;
        private String childName;
        private String childSurname;
        private int childAge;
        private Parent parent;
        private Set<ChildSession> childSessions = new HashSet<>();

        public Builder setChildId(int childId) { this.childId = childId; return this; }
        public Builder setChildName(String childName) { this.childName = childName; return this; }
        public Builder setChildSurname(String childSurname) { this.childSurname = childSurname; return this; }
        public Builder setChildAge(int childAge) { this.childAge = childAge; return this; }
        public Builder setParent(Parent parent) { this.parent = parent; return this; }
        public Builder setChildSessions(Set<ChildSession> childSessions) { this.childSessions = childSessions; return this; }

        public Child build() { return new Child(this); }

        public Builder copy(Child child) {
            this.childId = child.childId;
            this.childName = child.childName;
            this.childSurname = child.childSurname;
            this.childAge = child.childAge;
            this.parent = child.parent;
            this.childSessions = child.childSessions;
            return this;
        }
    }
}
