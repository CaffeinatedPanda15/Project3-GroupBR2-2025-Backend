package za.ac.cput.factories;

import za.ac.cput.domain.Address;

public class AddressFactory {

    public static Address createAddress(int houseNo, String streetName, String city,String province, String postalCode) {

        return new Address.Builder()
                .setHouseNo(houseNo)
                .setStreetName(streetName)
                .setCity(city)
                .setProvince(province)
                .setPostalCode(postalCode)
                .build();
    }
}
