package za.ac.cput.service.parent;

import za.ac.cput.domain.parent.Child;
import za.ac.cput.service.IService;
import java.util.List;

public interface IChildService extends IService<Child, Integer> {
    void delete(Integer id);
    List<Child> getChildrenByParent(Integer parentId);
}