package za.ac.cput.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.admin.Admin;
import za.ac.cput.repositories.admin.IAdminRepository;

import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {

    private final IAdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(IAdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin read(Integer id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Admin authenticate(String username, String password) {
        return adminRepository.findByUsernameAndPassword(username, password).orElse(null);
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional
    public boolean createDefaultAdmin() {
        try {
            // Check if any admin already exists
            if (adminRepository.count() > 0) {
                System.out.println("Admin already exists in database");
                return false;
            }

            // Create default admin
            Admin defaultAdmin = new Admin.Builder()
                    .setUsername("admin")
                    .setPassword("admin123") // In production, this should be hashed
                    .setEmail("admin@wecare.com")
                    .setFullName("System Administrator")
                    .setActive(true)
                    .build();

            adminRepository.save(defaultAdmin);
            System.out.println("Default admin created successfully:");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
            System.out.println("Email: admin@wecare.com");

            return true;
        } catch (Exception e) {
            System.err.println("Error creating default admin: " + e.getMessage());
            return false;
        }
    }
}