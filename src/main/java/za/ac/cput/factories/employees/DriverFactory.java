package za.ac.cput.factories.employees;

import za.ac.cput.domain.employees.Driver;

public class DriverFactory {

    public static Driver createDriver(String driverName, String driverSurname) {
        return new Driver.Builder()
                .setDriverName(driverName)
                .setDriverSurname(driverSurname)
                .build();
    }
}
