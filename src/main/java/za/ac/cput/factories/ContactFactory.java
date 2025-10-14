package za.ac.cput.factories;

import za.ac.cput.domain.Contact;

public class ContactFactory {

    public static Contact createContact(int phoneNumber1, int phoneNumber2, String email) {

        return new Contact.Builder()
                .setPhoneNumber1(phoneNumber1)
                .setPhoneNumber2(phoneNumber2)
                .setEmail(email)
                .build();
    }
}
