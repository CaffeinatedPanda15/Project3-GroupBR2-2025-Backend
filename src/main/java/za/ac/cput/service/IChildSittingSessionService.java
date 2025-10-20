package za.ac.cput.service.parent;

import za.ac.cput.domain.parent.ChildSittingSession;
import za.ac.cput.service.IService;
import java.util.List;

public interface IChildSittingSessionService extends IService<ChildSittingSession, Integer> {
    void delete(Integer id);
    List<ChildSittingSession> getConfirmedSessions();
    List<ChildSittingSession> getPendingSessions();
}