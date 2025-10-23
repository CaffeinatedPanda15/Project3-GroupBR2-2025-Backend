package za.ac.cput.service.parent;

import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.domain.parent.SessionStatus;
import za.ac.cput.service.IService;
import java.util.List;

public interface IChildSittingSessionService extends IService<ChildSittingSession, Integer> {
    void delete(Integer id);

    List<ChildSittingSession> getConfirmedSessions();

    List<ChildSittingSession> getPendingSessions();

    List<ChildSittingSession> getSessionsByNannyAndStatus(int nannyId, SessionStatus status);

    List<ChildSittingSession> getSessionsByParentId(int parentId);

    ChildSittingSession activateSession(int sessionId);

    ChildSittingSession completeSession(int sessionId);
}