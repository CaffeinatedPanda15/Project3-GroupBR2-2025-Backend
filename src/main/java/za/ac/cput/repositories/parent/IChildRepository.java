package za.ac.cput.repositories.parent;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.parent.Child;
import za.ac.cput.domain.parent.Parent;

import java.util.List;

public interface IChildRepository extends JpaRepository<Child, Integer> {
    // Find all children by parent
    List<Child> findByParent(Parent parent);
    
    // Find all children by parent ID
    List<Child> findByParent_ParentId(int parentId);
}
