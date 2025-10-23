package za.ac.cput.service.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.SessionStatus;
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

    @Override
    public List<ChildSittingSession> getSessionsByNannyAndStatus(int nannyId, SessionStatus status) {
        return sessionRepository.findByNanny_NannyIdAndStatus(nannyId, status);
    }

    @Override
    public List<ChildSittingSession> getSessionsByParentId(int parentId) {
        // Use the more efficient repository method with proper JPA query
        return sessionRepository.findByParentId(parentId);
    }

    @Override
    @Transactional
    public ChildSittingSession activateSession(int sessionId) {
        ChildSittingSession session = sessionRepository.findById(sessionId).orElse(null);
        if (session != null) {
            session.setStatus(SessionStatus.ACTIVE);
            return sessionRepository.save(session);
        }
        return null;
    }

    @Override
    @Transactional
    public ChildSittingSession completeSession(int sessionId) {
        ChildSittingSession session = sessionRepository.findById(sessionId).orElse(null);
        if (session != null) {
            session.setStatus(SessionStatus.COMPLETED);
            return sessionRepository.save(session);
        }
        return null;
    }
}
