package za.ac.cput.service.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.repositories.employees.INannyRepository;
import java.util.List;

@Service
public class NannyServiceImpl implements INannyService {

    private final INannyRepository nannyRepository;

    @Autowired
    public NannyServiceImpl(INannyRepository nannyRepository) {
        this.nannyRepository = nannyRepository;
    }

    @Override
    public Nanny create(Nanny nanny) {
        return nannyRepository.save(nanny);
    }

    @Override
    public Nanny read(Integer id) {
        return nannyRepository.findById(id).orElse(null);
    }

    @Override
    public Nanny update(Nanny nanny) {
        return nannyRepository.save(nanny);
    }

    @Override
    public List<Nanny> getAll() {
        return nannyRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        nannyRepository.deleteById(id);
    }
}
