package za.ac.cput.domain.employees;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Nanny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nannyId;

    private String nannyName;
    private String nannySurname;
    
    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "nanny", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Contact> contacts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "nanny", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "nanny", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<NannyReview> reviews = new HashSet<>();

    @JsonIgnore
    @OneToOne(mappedBy = "nanny", cascade = CascadeType.ALL)
    private BackgroundCheck backgroundCheck;

    protected Nanny() {}

    private Nanny(Builder builder) {
        this.nannyId = builder.nannyId;
        this.nannyName = builder.nannyName;
        this.nannySurname = builder.nannySurname;
        this.email = builder.email;
        this.password = builder.password;
        this.contacts = builder.contacts;
        this.addresses = builder.addresses;
        this.reviews = builder.reviews;
        this.backgroundCheck = builder.backgroundCheck;
    }

    public int getNannyId() { return nannyId; }
    public String getNannyName() { return nannyName; }
    public String getNannySurname() { return nannySurname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Set<Contact> getContacts() { return contacts; }
    public Set<Address> getAddresses() { return addresses; }
    public Set<NannyReview> getReviews() { return reviews; }
    public BackgroundCheck getBackgroundCheck() { return backgroundCheck; }

    @Override
    public String toString() {
        return "Nanny{" +
                "nannyId=" + nannyId +
                ", nannyName='" + nannyName + '\'' +
                ", nannySurname='" + nannySurname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contacts=" + contacts +
                ", addresses=" + addresses +
                ", reviews=" + reviews +
                ", backgroundCheck=" + backgroundCheck +
                '}';
    }

    public static class Builder {
        private int nannyId;
        private String nannyName;
        private String nannySurname;
        private String email;
        private String password;
        private Set<Contact> contacts = new HashSet<>();
        private Set<Address> addresses = new HashSet<>();
        private Set<NannyReview> reviews = new HashSet<>();
        private BackgroundCheck backgroundCheck;

        public Builder setNannyId(int nannyId) { this.nannyId = nannyId; return this; }
        public Builder setNannyName(String nannyName) { this.nannyName = nannyName; return this; }
        public Builder setNannySurname(String nannySurname) { this.nannySurname = nannySurname; return this; }
        public Builder setEmail(String email) { this.email = email; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setContacts(Set<Contact> contacts) { this.contacts = contacts; return this; }
        public Builder setAddresses(Set<Address> addresses) { this.addresses = addresses; return this; }
        public Builder setReviews(Set<NannyReview> reviews) { this.reviews = reviews; return this; }
        public Builder setBackgroundCheck(BackgroundCheck backgroundCheck) { this.backgroundCheck = backgroundCheck; return this; }

        public Nanny build() { return new Nanny(this); }

        public Builder copy(Nanny nanny) {
            this.nannyId = nanny.nannyId;
            this.nannyName = nanny.nannyName;
            this.nannySurname = nanny.nannySurname;
            this.email = nanny.email;
            this.password = nanny.password;
            this.contacts = nanny.contacts;
            this.addresses = nanny.addresses;
            this.reviews = nanny.reviews;
            this.backgroundCheck = nanny.backgroundCheck;
            return this;
        }
    }// End of Builder class
}// End of class
