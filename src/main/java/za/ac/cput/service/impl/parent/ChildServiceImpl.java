package za.ac.cput.service.impl.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.parent.Child;
import za.ac.cput.repositories.parent.IChildRepository;
import za.ac.cput.service.parent.IChildService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildServiceImpl implements IChildService {

    private final IChildRepository childRepository;

    @Autowired
    public ChildServiceImpl(IChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    @Override
    public Child create(Child child) {
        return childRepository.save(child);
    }

    @Override
    public Child read(Integer id) {
        return childRepository.findById(id).orElse(null);
    }

    @Override
    public Child update(Child child) {
        return childRepository.save(child);
    }

    @Override
    public List<Child> getAll() {
        return childRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        childRepository.deleteById(id);
    }

    @Override
    public List<Child> getChildrenByParent(Integer parentId) {
        return childRepository.findAll().stream()
                .filter(c -> c.getParent() != null && c.getParent().getParentId() == parentId)
                .collect(Collectors.toList());
    }
}