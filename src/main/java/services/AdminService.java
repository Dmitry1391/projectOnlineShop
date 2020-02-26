package services;

import dao.exceptions.DAOException;
import dao.impl.DAOFactoryImpl;
import dao.interfaces.AdminsDAO;
import models.Admin;

import java.util.List;

/**
 * This class is designed to work with the AdminService, which is associated with the DAO layer.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
public class AdminService {

    private AdminsDAO adminDAO  = DAOFactoryImpl.getInstance().getAdminsDAO();

    /**
     * Basic method for get all admins.
     *
     * @return - list all admins from the database.
     * @throws DAOException - throw the exception above.
     */
    public List<Admin> getAll() throws DAOException {
        return adminDAO.getAll();
    }

    /**
     * Basic method for delete admin.
     *
     * @param admin;
     * @return - delete admin in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean delete(Admin admin) throws DAOException {
        return adminDAO.delete(admin);
    }

    /**
     * Basic method for update product.
     *
     * @param oldAdmin;
     * @param newAdmin;
     * @return - updating admin in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean update(Admin oldAdmin, Admin newAdmin) throws DAOException {
        return adminDAO.update(oldAdmin,newAdmin);
    }

    /**
     * Basic method for create admin.
     *
     * @param admin - accepts admin.
     * @return - creating admin in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean create(Admin admin) throws DAOException {
        return adminDAO.create(admin);
    }

    /**
     * Method to get admin by specified id.
     *
     * @param adminId - admin id
     * @return - null if there was no such admin.
     * @throws DAOException;
     */
    public Admin getById(Integer adminId) throws DAOException {
        return adminDAO.getById(adminId);
    }

    /**
     * Method to get list of admin which matches by specified name.
     *
     * @param adminName - admin name.
     * @return - null if no admin match to argument.
     * @throws DAOException;
     */
    public List<Admin> getByName(String adminName) throws DAOException {
        return adminDAO.getByName(adminName);

    }

    /**
     * Method to get list of admin which matches by specified login.
     *
     * @param adminLogin - admin login.
     * @return - null if no admin match to argument.
     * @throws DAOException;
     */
    public Admin getByLogin(String adminLogin) throws DAOException {
        return adminDAO.getByLogin(adminLogin);

    }

    /**
     * Method get admin that matches to specified login and pass.
     *
     * @param adminLogin - admin login.
     * @param adminPass  - admin pass.
     * @return - null if there was no such admin.
     * @throws DAOException;
     */
    public Admin getByLoginAndPass(String adminLogin, String adminPass) throws DAOException {
        return adminDAO.getByLoginAndPass(adminLogin,adminPass);

    }

    /**
     * Method delete of admin which matches by specified name.
     *
     * @param adminLogin - admin name;
     * @return "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    public boolean deleteByLogin(String adminLogin) throws DAOException {
        return adminDAO.deleteByLogin(adminLogin);
    }
}
