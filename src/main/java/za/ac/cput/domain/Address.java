package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.Parent;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    private int houseNo;
    private String streetName;
    private String city;
    private String province;
    private String postalCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "nanny_id")
    private Nanny nanny;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    protected Address() {
    } // Required by JPA

    private Address(Builder builder) {
        this.addressId = builder.addressId;
        this.houseNo = builder.houseNo;
        this.streetName = builder.streetName;
        this.city = builder.city;
        this.province = builder.province;
        this.postalCode = builder.postalCode;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getHouseNo() {
        return houseNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Parent getParent() {
        return parent;
    }

    public Nanny getNanny() {
        return nanny;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public void setNanny(Nanny nanny) {
        this.nanny = nanny;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", houseNo=" + houseNo +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    public static class Builder {
        private int addressId;
        private int houseNo;
        private String streetName;
        private String city;
        private String province;
        private String postalCode;

        public Builder setAddressId(int addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder setHouseNo(int houseNo) {
            this.houseNo = houseNo;
            return this;
        }

        public Builder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }// end of Builder class

}// end of Address class
