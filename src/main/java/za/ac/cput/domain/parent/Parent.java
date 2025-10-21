package za.ac.cput.domain.parent;

import jakarta.persistence.*;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.employees.NannyReview;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parentId;

    private String parentName;
    private String parentSurname;
    private String email;
    private String password;

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
        this.email = builder.email;
        this.password = builder.password;
        this.contacts = builder.contacts;
        this.addresses = builder.addresses;
        this.children = builder.children;
        this.nannyReviews = builder.nannyReviews;
    }

    public int getParentId() { return parentId; }
    public String getParentName() { return parentName; }
    public String getParentSurname() { return parentSurname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
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
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
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
        private String email;
        private String password;
        private Set<Contact> contacts = new HashSet<>();
        private Set<Address> addresses = new HashSet<>();
        private Set<Child> children = new HashSet<>();
        private Set<NannyReview> nannyReviews = new HashSet<>();

        public Builder setParentId(int parentId) { this.parentId = parentId; return this; }
        public Builder setParentName(String parentName) { this.parentName = parentName; return this; }
        public Builder setParentSurname(String parentSurname) { this.parentSurname = parentSurname; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setContacts(Set<Contact> contacts) { this.contacts = contacts; return this; }
        public Builder setAddresses(Set<Address> addresses) { this.addresses = addresses; return this; }
        public Builder setChildren(Set<Child> children) { this.children = children; return this; }
        public Builder setNannyReviews(Set<NannyReview> nannyReviews) { this.nannyReviews = nannyReviews; return this; }

        public Parent build() { return new Parent(this); }

        public Builder copy(Parent parent) {
            this.parentId = parent.parentId;
            this.parentName = parent.parentName;
            this.parentSurname = parent.parentSurname;
            this.email = parent.email;
            this.password = parent.password;
            this.contacts = parent.contacts;
            this.addresses = parent.addresses;
            this.children = parent.children;
            this.nannyReviews = parent.nannyReviews;
            return this;
        }
    }// End of Builder class
}// End of class
