package za.ac.cput.service.employees;

import za.ac.cput.domain.employees.BackgroundCheck;
import za.ac.cput.service.IService;

public interface IBackgroundCheckService extends IService<BackgroundCheck, Integer> {
    void delete(Integer id);
    BackgroundCheck getByNannyId(Integer nannyId);
}