package za.ac.cput.service.impl.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.repositories.employees.IDriverRepository;
import za.ac.cput.service.employees.IDriverService;
import java.util.List;

@Service
public class DriverServiceImpl implements IDriverService {

    private final IDriverRepository driverRepository;

    @Autowired
    public DriverServiceImpl(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver create(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver read(Integer id) {
        return driverRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @Override
    public Driver update(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public List<Driver> getAll() {
        return driverRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        driverRepository.deleteById(Long.valueOf(id));
    }
}
