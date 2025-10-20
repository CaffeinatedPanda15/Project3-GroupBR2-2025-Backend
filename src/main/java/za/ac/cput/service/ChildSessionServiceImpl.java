package za.ac.cput.service.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.parent.ChildSession;
import za.ac.cput.repositories.parent.IChildSessionRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildSessionServiceImpl implements IChildSessionService {

    private final IChildSessionRepository childSessionRepository;

    @Autowired
    public ChildSessionServiceImpl(IChildSessionRepository childSessionRepository) {
        this.childSessionRepository = childSessionRepository;
    }

    @Override
    public ChildSession create(ChildSession childSession) {
        return childSessionRepository.save(childSession);
    }

    @Override
    public ChildSession read(Integer id) {
        return childSessionRepository.findById(id).orElse(null);
    }

    @Override
    public ChildSession update(ChildSession childSession) {
        return childSessionRepository.save(childSession);
    }

    @Override
    public List<ChildSession> getAll() {
        return childSessionRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        childSessionRepository.deleteById(id);
    }

    @Override
    public List<ChildSession> getSessionsByChild(Integer childId) {
        return childSessionRepository.findAll().stream()
                .filter(cs -> cs.getChild() != null && cs.getChild().getChildId() == childId)
                .collect(Collectors.toList());
    }
}
