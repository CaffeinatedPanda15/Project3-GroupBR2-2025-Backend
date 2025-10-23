package za.ac.cput.service.admin;

import za.ac.cput.domain.admin.Admin;
import za.ac.cput.service.IService;

public interface IAdminService extends IService<Admin, Integer> {
    Admin authenticate(String username, String password);

    Admin findByUsername(String username);

    boolean createDefaultAdmin();

    void delete(Integer id);
}