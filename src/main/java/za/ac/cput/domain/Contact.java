package za.ac.cput.domain;

import jakarta.persistence.*;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.Parent;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;
    private int phoneNumber1;
    private int phoneNumber2;
    private String email;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "nanny_id")
    private Nanny nanny;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Contact() {
    }

    public Contact(Builder builder) {
        this.contactId = builder.contactId;
        this.phoneNumber1 = builder.phoneNumber1;
        this.phoneNumber2 = builder.phoneNumber2;
        this.email = builder.email;
    }

    public int getContactId() {
        return contactId;
    }

    public int getPhoneNumber1() {
        return phoneNumber1;
    }

    public int getPhoneNumber2() {
        return phoneNumber2;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", phoneNumber1=" + phoneNumber1 +
                ", phoneNumber2=" + phoneNumber2 +
                ", email='" + email + '\'' +
                '}';
    }

    public static class Builder {
        private int contactId;
        private int phoneNumber1;
        private int phoneNumber2;
        private String email;

        public Builder setContactId(int contactId) {
            this.contactId = contactId;
            return this;
        }

        public Builder setPhoneNumber1(int phoneNumber1) {
            this.phoneNumber1 = phoneNumber1;
            return this;
        }

        public Builder setPhoneNumber2(int phoneNumber2) {
            this.phoneNumber2 = phoneNumber2;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }

        public Builder copy(Contact contact) {
            this.contactId = contact.contactId;
            this.phoneNumber1 = contact.phoneNumber1;
            this.phoneNumber2 = contact.phoneNumber2;
            this.email = contact.email;
            return this;
        }

    }// End of Builder class


}// End of Contact class
