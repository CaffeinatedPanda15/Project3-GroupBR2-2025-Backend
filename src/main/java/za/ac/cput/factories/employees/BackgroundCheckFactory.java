package za.ac.cput.factories.employees;

import za.ac.cput.domain.employees.BackgroundCheck;
import za.ac.cput.domain.employees.Nanny;
import java.util.Date;

public class BackgroundCheckFactory {

    public static BackgroundCheck createBackgroundCheck(String status, Date checkDate, String verifiedBy, Nanny nanny) {
        return new BackgroundCheck.Builder()
                .setStatus(status)
                .setCheckDate(checkDate)
                .setVerifiedBy(verifiedBy)
                .setNanny(nanny)
                .build();
    }
}
