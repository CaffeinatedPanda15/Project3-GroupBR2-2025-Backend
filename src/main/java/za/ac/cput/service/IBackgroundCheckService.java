package za.ac.cput.service;

import za.ac.cput.domain.employees.BackgroundCheck;

public interface IBackgroundCheckService extends IService<BackgroundCheck, Integer> {
    void delete(Integer id);

    BackgroundCheck getByNannyId(Integer nannyId);
}