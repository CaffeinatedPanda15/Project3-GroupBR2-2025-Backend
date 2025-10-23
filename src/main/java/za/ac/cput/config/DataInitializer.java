package za.ac.cput.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import za.ac.cput.service.admin.IAdminService;

@Component
public class DataInitializer implements ApplicationRunner {

    private final IAdminService adminService;

    @Autowired
    public DataInitializer(IAdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=== INITIALIZING DEFAULT DATA ===");

        // Create default admin if none exists
        boolean adminCreated = adminService.createDefaultAdmin();
        if (adminCreated) {
            System.out.println("✅ Default admin account created");
        } else {
            System.out.println("ℹ️ Admin account already exists");
        }

        System.out.println("=== DATA INITIALIZATION COMPLETE ===");
    }
}