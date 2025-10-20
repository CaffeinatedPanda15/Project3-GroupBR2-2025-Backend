package za.ac.cput.service.parent;

import za.ac.cput.domain.parent.ChildSession;
import za.ac.cput.service.IService;
import java.util.List;

public interface IChildSessionService extends IService<ChildSession, Integer> {
    void delete(Integer id);
    List<ChildSession> getSessionsByChild(Integer childId);
}
