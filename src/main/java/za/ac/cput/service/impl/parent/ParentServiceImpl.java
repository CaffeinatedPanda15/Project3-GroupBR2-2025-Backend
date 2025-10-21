package za.ac.cput.service.impl.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.repositories.parent.IParentRepository;
import za.ac.cput.service.parent.IParentService;
import java.util.List;

@Service
public class ParentServiceImpl implements IParentService {

    private final IParentRepository parentRepository;

    @Autowired
    public ParentServiceImpl(IParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    @Override
    public Parent create(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public Parent read(Integer id) {
        return parentRepository.findById(Long.valueOf(id)).orElse(null);
    }

    @Override
    public Parent update(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public List<Parent> getAll() {
        return parentRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        parentRepository.deleteById(Long.valueOf(id));
    }
}