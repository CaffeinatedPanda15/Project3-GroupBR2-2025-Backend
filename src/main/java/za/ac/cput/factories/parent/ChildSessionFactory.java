package za.ac.cput.factories.parent;

import za.ac.cput.domain.parent.Child;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.ChildSession;

public class ChildSessionFactory {

    public static ChildSession createChildSession(Child child,
                                                  ChildSittingSession session) {
        return new ChildSession.Builder()
                .setChild(child)
                .setSession(session)
                .build();
    }
}
