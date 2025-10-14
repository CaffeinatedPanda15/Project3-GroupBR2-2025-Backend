package za.ac.cput.factory;

import za.ac.cput.domain.ChildSittingSession;
import za.ac.cput.domain.Driver;
import za.ac.cput.domain.Nanny;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;

public class ChildSittingSessionFactory {

    public static ChildSittingSession createSession(Date sessionDate, Time startTime, Time endTime, boolean confirmed, Nanny nanny, Driver driver) {
        return new ChildSittingSession.Builder()
                .setSessionDate(sessionDate)
                .setSessionStartTime(startTime)
                .setSessionEndTime(endTime)
                .setSessionConfirmed(confirmed)
                .setNanny(nanny)
                .setDriver(driver)
                .setPayments(new HashSet<>())
                .setChildSessions(new HashSet<>())
                .build();
    }
}
