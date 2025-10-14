package za.ac.cput.factories.parent;

import za.ac.cput.domain.parent.Child;
import za.ac.cput.domain.parent.Parent;

public class ChildFactory {

    public static Child createChild(String childName,
                                    String childSurname,
                                    int childAge,
                                    Parent parent) {
        return new Child.Builder()
                .setChildName(childName)
                .setChildSurname(childSurname)
                .setChildAge(childAge)
                .setParent(parent)
                .build();
    }
}
