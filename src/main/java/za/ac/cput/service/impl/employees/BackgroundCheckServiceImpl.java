package za.ac.cput.service.impl.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.employees.BackgroundCheck;
import za.ac.cput.repositories.employees.IBackgroundCheck;
import za.ac.cput.service.IBackgroundCheckService;
import java.util.List;

@Service
public class BackgroundCheckServiceImpl implements IBackgroundCheckService {

    private final IBackgroundCheck backgroundCheckRepository;

    @Autowired
    public BackgroundCheckServiceImpl(IBackgroundCheck backgroundCheckRepository) {
        this.backgroundCheckRepository = backgroundCheckRepository;
    }

    @Override
    public BackgroundCheck create(BackgroundCheck backgroundCheck) {
        return backgroundCheckRepository.save(backgroundCheck);
    }

    @Override
    public BackgroundCheck read(Integer id) {
        return backgroundCheckRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @Override
    public BackgroundCheck update(BackgroundCheck backgroundCheck) {
        return backgroundCheckRepository.save(backgroundCheck);
    }

    @Override
    public List<BackgroundCheck> getAll() {
        return backgroundCheckRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        backgroundCheckRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public BackgroundCheck getByNannyId(Integer nannyId) {
        return backgroundCheckRepository.findAll().stream()
                .filter(bc -> bc.getNanny() != null && bc.getNanny().getNannyId() == nannyId)
                .findFirst()
                .orElse(null);
    }
}
