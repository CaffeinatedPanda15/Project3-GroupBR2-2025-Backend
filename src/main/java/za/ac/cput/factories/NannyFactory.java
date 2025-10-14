package za.ac.cput.factories;

import za.ac.cput.domain.Nanny;
import java.util.Set;

public class NannyFactory {

    public static Nanny createNanny(String name, String surname, Set contacts, Set addresses) {
        return new Nanny.Builder()
                .setNannyName(name)
                .setNannySurname(surname)
                .setContacts(contacts)
                .setAddresses(addresses)
                .build();
    }
}
