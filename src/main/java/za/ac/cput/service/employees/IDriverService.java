package za.ac.cput.service.employees;

import za.ac.cput.domain.employees.Driver;
import za.ac.cput.service.IService;

public interface IDriverService extends IService<Driver, Integer> {
    void delete(Integer id);
}