package za.ac.cput.service.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.repositories.parent.IChildSittingSessionRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChildSittingSessionServiceImpl implements IChildSittingSessionService {

    private final IChildSittingSessionRepository sessionRepository;

    @Autowired
    public ChildSittingSessionServiceImpl(IChildSittingSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public ChildSittingSession create(ChildSittingSession session) {
        return sessionRepository.save(session);
    }

    @Override
    public ChildSittingSession read(Integer id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Override
    public ChildSittingSession update(ChildSittingSession session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<ChildSittingSession> getAll() {
        return sessionRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public List<ChildSittingSession> getConfirmedSessions() {
        return sessionRepository.findAll().stream()
                .filter(ChildSittingSession::isSessionConfirmed)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChildSittingSession> getPendingSessions() {
        return sessionRepository.findAll().stream()
                .filter(s -> !s.isSessionConfirmed())
                .collect(Collectors.toList());
    }
}
