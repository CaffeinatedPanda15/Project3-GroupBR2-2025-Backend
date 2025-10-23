package za.ac.cput.domain.admin;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private boolean isActive = true;

    protected Admin() {
    }

    private Admin(Builder builder) {
        this.adminId = builder.adminId;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.fullName = builder.fullName;
        this.isActive = builder.isActive;
    }

    // Getters
    public int getAdminId() {
        return adminId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters for update operations
    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    public static class Builder {
        private int adminId;
        private String username;
        private String password;
        private String email;
        private String fullName;
        private boolean isActive = true;

        public Builder setAdminId(int adminId) {
            this.adminId = adminId;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }

        public Builder copy(Admin admin) {
            this.adminId = admin.adminId;
            this.username = admin.username;
            this.password = admin.password;
            this.email = admin.email;
            this.fullName = admin.fullName;
            this.isActive = admin.isActive;
            return this;
        }
    }
}