package za.ac.cput.factories;

import za.ac.cput.domain.Parent;

import java.util.Set;

public class ParentFactory {

    public static Parent createParent(String name, String surname, Set contacts, Set addresses) {
        return new Parent.Builder()
                .setParentName(name)
                .setParentSurname(surname)
                .setContacts(contacts)
                .setAddresses(addresses)
                .build();
    }
}
