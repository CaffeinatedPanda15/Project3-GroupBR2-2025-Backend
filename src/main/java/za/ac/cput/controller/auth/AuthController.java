package za.ac.cput.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Address;
import za.ac.cput.domain.Contact;
import za.ac.cput.domain.employees.Driver;
import za.ac.cput.domain.employees.Nanny;
import za.ac.cput.domain.parent.Parent;
import za.ac.cput.service.employees.IDriverService;
import za.ac.cput.service.employees.INannyService;
import za.ac.cput.service.parent.IParentService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final IParentService parentService;
    private final INannyService nannyService;
    private final IDriverService driverService;

    @Autowired
    public AuthController(IParentService parentService, INannyService nannyService, IDriverService driverService) {
        this.parentService = parentService;
        this.nannyService = nannyService;
        this.driverService = driverService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegistrationRequest req) {
        Map<String, Object> resp = new HashMap<>();

        try {
            String role = req.role != null ? req.role.toLowerCase() : "parent";

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
                    Nanny nanny = new Nanny.Builder()
                            .setNannyName(req.firstName)
                            .setNannySurname(req.lastName)
                            .build();

                    // Set bidirectional relationships
                    Set<Contact> nannyContacts = new HashSet<>();
                    if (contact != null) {
                        contact.setNanny(nanny);
                        nannyContacts.add(contact);
                    }
                    Set<Address> nannyAddresses = new HashSet<>();
                    if (address != null) {
                        address.setNanny(nanny);
                        nannyAddresses.add(address);
                    }

                    Nanny nannyWithRelations = new Nanny.Builder()
                            .copy(nanny)
                            .setContacts(nannyContacts)
                            .setAddresses(nannyAddresses)
                            .build();

                    Nanny createdNanny = nannyService.create(nannyWithRelations);
                    resp.put("success", true);
                    resp.put("role", "nanny");
                    resp.put("id", createdNanny.getNannyId());
                    resp.put("message", "Nanny registered successfully");
                    return new ResponseEntity<>(resp, HttpStatus.CREATED);

                case "driver":
                    Driver driver = new Driver.Builder()
                            .setDriverName(req.firstName)
                            .setDriverSurname(req.lastName)
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
                    Parent parent = new Parent.Builder()
                            .setParentName(req.firstName)
                            .setParentSurname(req.lastName)
                            .build();

                    // Set bidirectional relationships
                    Set<Contact> parentContacts = new HashSet<>();
                    if (contact != null) {
                        contact.setParent(parent);
                        parentContacts.add(contact);
                    }
                    Set<Address> parentAddresses = new HashSet<>();
                    if (address != null) {
                        address.setParent(parent);
                        parentAddresses.add(address);
                    }

                    Parent parentWithRelations = new Parent.Builder()
                            .copy(parent)
                            .setContacts(parentContacts)
                            .setAddresses(parentAddresses)
                            .build();

                    Parent createdParent = parentService.create(parentWithRelations);
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
}
