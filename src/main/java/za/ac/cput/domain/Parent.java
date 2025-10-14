package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parentId;

    private String parentName;
    private String parentSurname;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> contacts = new HashSet<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Child> children = new HashSet<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NannyReview> nannyReviews = new HashSet<>();

    protected Parent() {}

    private Parent(Builder builder) {
        this.parentId = builder.parentId;
        this.parentName = builder.parentName;
        this.parentSurname = builder.parentSurname;
        this.contacts = builder.contacts;
        this.addresses = builder.addresses;
        this.children = builder.children;
        this.nannyReviews = builder.nannyReviews;
    }

    public int getParentId() { return parentId; }
    public String getParentName() { return parentName; }
    public String getParentSurname() { return parentSurname; }
    public Set<Contact> getContacts() { return contacts; }
    public Set<Address> getAddresses() { return addresses; }
    public Set<Child> getChildren() { return children; }
    public Set<NannyReview> getNannyReviews() { return nannyReviews; }

    @Override
    public String toString() {
        return "Parent{" +
                "parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", parentSurname='" + parentSurname + '\'' +
                ", contacts=" + contacts +
                ", addresses=" + addresses +
                ", children=" + children +
                ", nannyReviews=" + nannyReviews +
                '}';
    }

    public static class Builder {
        private int parentId;
        private String parentName;
        private String parentSurname;
        private Set<Contact> contacts = new HashSet<>();
        private Set<Address> addresses = new HashSet<>();
        private Set<Child> children = new HashSet<>();
        private Set<NannyReview> nannyReviews = new HashSet<>();

        public Builder setParentId(int parentId) { this.parentId = parentId; return this; }
        public Builder setParentName(String parentName) { this.parentName = parentName; return this; }
        public Builder setParentSurname(String parentSurname) { this.parentSurname = parentSurname; return this; }
        public Builder setContacts(Set<Contact> contacts) { this.contacts = contacts; return this; }
        public Builder setAddresses(Set<Address> addresses) { this.addresses = addresses; return this; }
        public Builder setChildren(Set<Child> children) { this.children = children; return this; }
        public Builder setNannyReviews(Set<NannyReview> nannyReviews) { this.nannyReviews = nannyReviews; return this; }

        public Parent build() { return new Parent(this); }

        public Builder copy(Parent parent) {
            this.parentId = parent.parentId;
            this.parentName = parent.parentName;
            this.parentSurname = parent.parentSurname;
            this.contacts = parent.contacts;
            this.addresses = parent.addresses;
            this.children = parent.children;
            this.nannyReviews = parent.nannyReviews;
            return this;
        }
    }// End of Builder class
}// End of class
