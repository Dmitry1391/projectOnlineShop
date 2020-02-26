package models;

/**
 * This class describes the Admin object.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public class Admin {
    private Integer adminId;
    private String adminName;
    private String adminLogin;
    private String adminPass;

    public Admin(int adminId, String adminName, String adminLogin, String adminPass) {
        this.adminId = adminId;
        this.adminLogin = adminLogin;
        this.adminName = adminName;
        this.adminPass = adminPass;
    }

    public Admin(String adminName, String adminLogin, String adminPass) {
        this.adminName = adminName;
        this.adminLogin = adminLogin;
        this.adminPass = adminPass;
    }

    public Admin() {
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminLogin() {
        return adminLogin;
    }

    public void setAdminLogin(String adminLogin) {
        this.adminLogin = adminLogin;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    @Override
    public String toString() {
        return "Admin: id = " + adminId +
                ", name = " + adminName +
                ", login = " + adminLogin +
                ", password = " + adminPass;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result += prime * adminId.hashCode();
        result += prime * adminName.hashCode();
        result += prime * adminLogin.hashCode();
        result += prime * adminPass.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return adminId.equals(admin.adminId) &&
                adminName.equals(admin.adminName) &&
                adminLogin.equals(admin.adminLogin) &&
                adminPass.equals(admin.adminPass);
    }
}
