package za.ac.cput.service.parent;

import za.ac.cput.domain.parent.Parent;
import za.ac.cput.service.IService;

public interface IParentService extends IService<Parent, Integer> {
    void delete(Integer id);
}