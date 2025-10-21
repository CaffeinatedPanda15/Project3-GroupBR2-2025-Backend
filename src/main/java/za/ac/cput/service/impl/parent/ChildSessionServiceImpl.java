package za.ac.cput.service.impl.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.parent.ChildSession;
import za.ac.cput.repositories.parent.IChildSessionRepository;
import za.ac.cput.service.parent.IChildSessionService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildSessionServiceImpl implements IChildSessionService {

    private final IChildSessionRepository sessionRepository;

    @Autowired
    public ChildSessionServiceImpl(IChildSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public ChildSession create(ChildSession session) {
        return sessionRepository.save(session);
    }

    @Override
    public ChildSession read(Integer id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Override
    public ChildSession update(ChildSession session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<ChildSession> getAll() {
        return sessionRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public List<ChildSession> getSessionsByChild(Integer childId) {
        return sessionRepository.findAll().stream()
                .filter(s -> s.getChild() != null && s.getChild().getChildId() == childId)
                .collect(Collectors.toList());
    }
}
