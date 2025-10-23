package za.ac.cput.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.admin.Admin;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.repositories.employees.IDriverRepository;
import za.ac.cput.repositories.employees.INannyRepository;
import za.ac.cput.repositories.parent.IParentRepository;
import za.ac.cput.service.admin.IAdminService;
import za.ac.cput.service.employees.IDriverService;
import za.ac.cput.service.employees.INannyService;
import za.ac.cput.service.parent.IParentService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = "*")
public class AuthController {

    private final IParentService parentService;
    private final INannyService nannyService;
    private final IDriverService driverService;
    private final IAdminService adminService;
    private final IParentRepository parentRepository;
    private final INannyRepository nannyRepository;
    private final IDriverRepository driverRepository;

    @Autowired
    public AuthController(IParentService parentService, INannyService nannyService, IDriverService driverService,
            IAdminService adminService, IParentRepository parentRepository, INannyRepository nannyRepository,
            IDriverRepository driverRepository) {
        this.parentService = parentService;
        this.nannyService = nannyService;
        this.driverService = driverService;
        this.adminService = adminService;
        this.parentRepository = parentRepository;
        this.nannyRepository = nannyRepository;
        this.driverRepository = driverRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegistrationRequest req) {
        Map<String, Object> resp = new HashMap<>();

        try {
            String role = req.role != null ? req.role.toLowerCase() : "parent";

            // Check if email already exists in any table
            if (req.email != null && !req.email.isEmpty()) {
                String email = req.email.trim().toLowerCase();

                if (parentRepository.findByEmail(email).isPresent()) {
                    resp.put("success", false);
                    resp.put("message", "Email already registered");
                    return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
                }

                if (nannyRepository.findByEmail(email).isPresent()) {
                    resp.put("success", false);
                    resp.put("message", "Email already registered");
                    return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
                }

                if (driverRepository.findByEmail(email).isPresent()) {
                    resp.put("success", false);
                    resp.put("message", "Email already registered");
                    return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
                }
            }

            // Build Contact from phone numbers
            Contact contact = null;
            if (req.phone1 != null && !req.phone1.isEmpty()) {
                Contact.Builder contactBuilder = new Contact.Builder();
                try {
                    int phone1 = Integer.parseInt(req.phone1.replaceAll("[^0-9]", ""));
                    contactBuilder.setPhoneNumber1(phone1);
                } catch (NumberFormatException e) {
                    // ignore invalid phone
                }
                if (req.phone2 != null && !req.phone2.isEmpty()) {
                    try {
                        int phone2 = Integer.parseInt(req.phone2.replaceAll("[^0-9]", ""));
                        contactBuilder.setPhoneNumber2(phone2);
                    } catch (NumberFormatException e) {
                        // ignore invalid phone
                    }
                }
                contact = contactBuilder.build();
            }

            // Build Address from address fields
            Address address = null;
            if (req.houseNumber != null && !req.houseNumber.isEmpty()) {
                Address.Builder addressBuilder = new Address.Builder();
                try {
                    int houseNo = Integer.parseInt(req.houseNumber.replaceAll("[^0-9]", ""));
                    addressBuilder.setHouseNo(houseNo);
                } catch (NumberFormatException e) {
                    // ignore invalid house number
                }
                if (req.streetName != null && !req.streetName.isEmpty()) {
                    addressBuilder.setStreetName(req.streetName);
                }
                if (req.postalCode != null && !req.postalCode.isEmpty()) {
                    addressBuilder.setPostalCode(req.postalCode);
                }
                address = addressBuilder.build();
            }

            switch (role) {
                case "nanny":
                    // Build collections first
                    Set<Contact> nannyContacts = new HashSet<>();
                    if (contact != null) {
                        nannyContacts.add(contact);
                    }
                    Set<Address> nannyAddresses = new HashSet<>();
                    if (address != null) {
                        nannyAddresses.add(address);
                    }

                    // Build nanny with all relationships
                    Nanny nanny = new Nanny.Builder()
                            .setNannyName(req.firstName)
                            .setNannySurname(req.lastName)
                            .setEmail(req.email)
                            .setPassword(req.password)
                            .setContacts(nannyContacts)
                            .setAddresses(nannyAddresses)
                            .build();

                    // Set bidirectional relationships after building
                    if (contact != null) {
                        contact.setNanny(nanny);
                    }
                    if (address != null) {
                        address.setNanny(nanny);
                    }

                    Nanny createdNanny = nannyService.create(nanny);
                    resp.put("success", true);
                    resp.put("role", "nanny");
                    resp.put("id", createdNanny.getNannyId());
                    resp.put("message", "Nanny registered successfully");
                    return new ResponseEntity<>(resp, HttpStatus.CREATED);

                case "driver":
                    Driver driver = new Driver.Builder()
                            .setDriverName(req.firstName)
                            .setDriverSurname(req.lastName)
                            .setEmail(req.email)
                            .setPassword(req.password)
                            .build();
                    // Note: Driver entity doesn't have contacts/addresses in the domain model
                    // currently
                    // Contact and Address records are not created for drivers in this version
                    Driver createdDriver = driverService.create(driver);
                    resp.put("success", true);
                    resp.put("role", "driver");
                    resp.put("id", createdDriver.getDriverId());
                    resp.put("message", "Driver registered successfully");
                    return new ResponseEntity<>(resp, HttpStatus.CREATED);

                default:
                    // Build collections first
                    Set<Contact> parentContacts = new HashSet<>();
                    if (contact != null) {
                        parentContacts.add(contact);
                    }
                    Set<Address> parentAddresses = new HashSet<>();
                    if (address != null) {
                        parentAddresses.add(address);
                    }

                    // Build parent with all relationships
                    Parent parent = new Parent.Builder()
                            .setParentName(req.firstName)
                            .setParentSurname(req.lastName)
                            .setEmail(req.email)
                            .setPassword(req.password)
                            .setContacts(parentContacts)
                            .setAddresses(parentAddresses)
                            .build();

                    // Set bidirectional relationships after building
                    if (contact != null) {
                        contact.setParent(parent);
                    }
                    if (address != null) {
                        address.setParent(parent);
                    }

                    Parent createdParent = parentService.create(parent);
                    resp.put("success", true);
                    resp.put("role", "parent");
                    resp.put("id", createdParent.getParentId());
                    resp.put("message", "Parent registered successfully");
                    return new ResponseEntity<>(resp, HttpStatus.CREATED);
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.put("success", false);
            resp.put("message", "Registration failed: " + e.getMessage());
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest req) {
        Map<String, Object> resp = new HashMap<>();

        try {
            if (req.email == null || req.email.isEmpty() || req.password == null || req.password.isEmpty()) {
                resp.put("success", false);
                resp.put("message", "Email and password are required");
                return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
            }

            // Trim and convert email to lowercase for case-insensitive comparison
            String loginEmail = req.email.trim().toLowerCase();
            String loginPassword = req.password.trim();

            System.out.println("Login attempt - Email: " + loginEmail);

            // Try to find user in Parent table
            Parent parent = parentService.getAll().stream()
                    .filter(p -> p.getEmail() != null && loginEmail.equals(p.getEmail().trim().toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (parent != null) {
                System.out.println("Found parent with email: " + parent.getEmail());
                System.out.println("Stored password: '" + parent.getPassword() + "'");
                System.out.println("Login password: '" + loginPassword + "'");
                System.out.println("Password match: " + loginPassword.equals(parent.getPassword()));

                if (parent.getPassword() != null && loginPassword.equals(parent.getPassword())) {
                    resp.put("success", true);
                    resp.put("role", "parent");
                    resp.put("id", parent.getParentId());
                    resp.put("firstName", parent.getParentName());
                    resp.put("lastName", parent.getParentSurname());
                    resp.put("email", parent.getEmail());
                    resp.put("message", "Login successful");
                    return new ResponseEntity<>(resp, HttpStatus.OK);
                } else {
                    resp.put("success", false);
                    resp.put("message", "Invalid email or password");
                    return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
                }
            }

            // Try to find user in Nanny table
            Nanny nanny = nannyService.getAll().stream()
                    .filter(n -> n.getEmail() != null && loginEmail.equals(n.getEmail().trim().toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (nanny != null) {
                System.out.println("Found nanny with email: " + nanny.getEmail());
                System.out.println("Stored password: '" + nanny.getPassword() + "'");
                System.out.println("Login password: '" + loginPassword + "'");
                System.out.println("Password match: " + loginPassword.equals(nanny.getPassword()));

                if (nanny.getPassword() != null && loginPassword.equals(nanny.getPassword())) {
                    resp.put("success", true);
                    resp.put("role", "nanny");
                    resp.put("id", nanny.getNannyId());
                    resp.put("firstName", nanny.getNannyName());
                    resp.put("lastName", nanny.getNannySurname());
                    resp.put("email", nanny.getEmail());
                    resp.put("message", "Login successful");
                    return new ResponseEntity<>(resp, HttpStatus.OK);
                } else {
                    resp.put("success", false);
                    resp.put("message", "Invalid email or password");
                    return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
                }
            }

            // Try to find user in Driver table
            Driver driver = driverService.getAll().stream()
                    .filter(d -> d.getEmail() != null && loginEmail.equals(d.getEmail().trim().toLowerCase()))
                    .findFirst()
                    .orElse(null);

            if (driver != null) {
                System.out.println("Found driver with email: " + driver.getEmail());
                System.out.println("Stored password: '" + driver.getPassword() + "'");
                System.out.println("Login password: '" + loginPassword + "'");
                System.out.println("Password match: " + loginPassword.equals(driver.getPassword()));

                if (driver.getPassword() != null && loginPassword.equals(driver.getPassword())) {
                    resp.put("success", true);
                    resp.put("role", "driver");
                    resp.put("id", driver.getDriverId());
                    resp.put("firstName", driver.getDriverName());
                    resp.put("lastName", driver.getDriverSurname());
                    resp.put("email", driver.getEmail());
                    resp.put("message", "Login successful");
                    return new ResponseEntity<>(resp, HttpStatus.OK);
                } else {
                    resp.put("success", false);
                    resp.put("message", "Invalid email or password");
                    return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
                }
            }

            // Try to find user in Admin table (for username/password login)
            // Check if login input looks like a username (no @ symbol) or email
            if (!loginEmail.contains("@")) {
                // This might be a username login for admin
                Admin admin = adminService.authenticate(loginEmail, loginPassword);
                if (admin != null && admin.isActive()) {
                    System.out.println("Found admin with username: " + admin.getUsername());
                    resp.put("success", true);
                    resp.put("role", "admin");
                    resp.put("id", admin.getAdminId());
                    resp.put("firstName", admin.getFullName().split(" ")[0]);
                    resp.put("lastName",
                            admin.getFullName().contains(" ")
                                    ? admin.getFullName().substring(admin.getFullName().indexOf(" ") + 1)
                                    : "");
                    resp.put("email", admin.getEmail());
                    resp.put("username", admin.getUsername());
                    resp.put("message", "Admin login successful");
                    return new ResponseEntity<>(resp, HttpStatus.OK);
                }
            } else {
                // Try admin login with email if no username found
                Admin adminByEmail = adminService.getAll().stream()
                        .filter(a -> a.getEmail() != null && loginEmail.equals(a.getEmail().trim().toLowerCase()))
                        .findFirst()
                        .orElse(null);

                if (adminByEmail != null && adminByEmail.isActive()
                        && adminByEmail.getPassword().equals(loginPassword)) {
                    System.out.println("Found admin with email: " + adminByEmail.getEmail());
                    resp.put("success", true);
                    resp.put("role", "admin");
                    resp.put("id", adminByEmail.getAdminId());
                    resp.put("firstName", adminByEmail.getFullName().split(" ")[0]);
                    resp.put("lastName",
                            adminByEmail.getFullName().contains(" ")
                                    ? adminByEmail.getFullName().substring(adminByEmail.getFullName().indexOf(" ") + 1)
                                    : "");
                    resp.put("email", adminByEmail.getEmail());
                    resp.put("username", adminByEmail.getUsername());
                    resp.put("message", "Admin login successful");
                    return new ResponseEntity<>(resp, HttpStatus.OK);
                }
            }

            // User not found in any table
            System.out.println("No user found with email: " + loginEmail);
            resp.put("success", false);
            resp.put("message", "Invalid email or password");
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();
            resp.put("success", false);
            resp.put("message", "Login failed: " + e.getMessage());
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
