package za.ac.cput.service.employees;

import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.service.IService;

public interface INannyService extends IService<Nanny, Integer> {
    void delete(Integer id);
}