package controllers;


import dao.exceptions.DAOException;
import models.Admin;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import services.AdminService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class to work with AdminController.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(AdminController.class);

    private static final long serialVersionUID = 1L;

    public static final String KEY_ADMIN_ID = "id";
    public static final String KEY_ADMIN_NAME = "name";
    public static final String KEY_ADMIN_LOGIN = "login";
    public static final String KEY_ADMIN_PASS = "pass";

    private AdminService adminService;

    // Writing in JSONArray for response
    private JSONArray adminListToArray(List<Admin> admins) {

        JSONArray array = new JSONArray();

        for (Admin adm : admins) {
            JSONObject admin = new JSONObject();
            admin.put(KEY_ADMIN_ID, adm.getAdminId());
            admin.put(KEY_ADMIN_NAME, adm.getAdminName());
            admin.put(KEY_ADMIN_LOGIN, adm.getAdminLogin());
            admin.put(KEY_ADMIN_PASS, adm.getAdminPass());
            array.put(admin);
        }
        return array;
    }

    @Override
    public void init() {
        adminService = new AdminService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String getById = "getById";
        String createAdmin = "createAdmin";
        String getByLogin = "getByLogin";
        String getByLoginAndPass = "getByLoginAndPass";
        String getAllAdmins = "getAllAdmins";
        String deleteByLogin = "deleteByLogin";
        String renameAdminName = "renameAdminName";

        Admin admin;

        List<String> queries = HttpQueryUtils.getListQuery(req);

        String commandQuery = queries.get(0);
        String nameQuery = queries.get(1);

        // Get admin by id in DB
        if (commandQuery.equals(getById)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    admin = adminService.getById(Integer.parseInt(nameQuery));
                    List<Admin> admins = new ArrayList<Admin>();

                    if (admin != null) {
                        admins.add(admin);
                        resp.setStatus(200);
                        logger.info("Successful, in method: getById, into the class: AdminController!");
                        resp.setContentType("application/json");
                        resp.getWriter().write(adminListToArray(admins).toString());
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getById, into the class: AdminController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        //Create admin in DB
        if (commandQuery.equals(createAdmin)) {

            String adminName = queries.get(1);
            String adminLogin = queries.get(2);
            String adminPass = queries.get(3);

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("AdminName = " + adminName);
            System.out.println("AdminLogin = " + adminLogin);
            System.out.println("AdminPass = " + adminPass);

            if ((adminName != null && !adminName.isEmpty()) && (adminLogin != null && !adminLogin.isEmpty()) && (adminPass != null && !adminPass.isEmpty())) {

                try {
                    adminService.create(new Admin(adminName, adminLogin, adminPass));
                    resp.setStatus(200);
                    resp.setContentType("application/json");
                    logger.info("Successful, in method: createAdmin, into the class: AdminController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: createAdmin, into the class: AdminController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Get admin by login in DB
        if (commandQuery.equals(getByLogin)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    admin = adminService.getByLogin(nameQuery);
                    List<Admin> admins = new ArrayList<Admin>();

                    if (admin != null) {
                        admins.add(admin);
                        resp.setStatus(200);
                        logger.info("Successful, in method: getByLogin, into the class: AdminController!");
                    } else {
                        resp.setStatus(404);
                    }

                    resp.setContentType("application/json");
                    resp.getWriter().write(adminListToArray(admins).toString());
                } catch (DAOException e) {
                    logger.error("Error, in method: getByLogin, into the class: AdminController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Get admin or client by login and pass in DB
        if (commandQuery.equals(getByLoginAndPass)) {

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/ClientController");

            String passQuery = queries.get(2);

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);
            System.out.println("passQuery = " + passQuery);

            if ((nameQuery != null && !nameQuery.isEmpty()) && (passQuery != null && !passQuery.isEmpty())) {
                try {
                    admin = adminService.getByLoginAndPass(nameQuery, passQuery);
                    List<Admin> admins = new ArrayList<Admin>();
                    if (admin != null) {
                        admins.add(admin);
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        resp.getWriter().write(adminListToArray(admins).toString());
                        logger.info("Successful, in method: getByLoginAndPass, into the class: AdminController!");
                    } else {
                        resp.setContentType("application/json");
                        requestDispatcher.forward(req, resp);
                    }

                } catch (DAOException e) {
                    logger.error("Error, in method: getByLoginAndPass, into the class: AdminController! \n" + e);
                }
            }else {
                resp.setStatus(404);
            }
        }


        // Get all admins in DB
        if (getAllAdmins.equals(commandQuery)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    List<Admin> admins = adminService.getAll();

                    if (!admins.isEmpty()) {
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        resp.getWriter().write(adminListToArray(admins).toString());
                        logger.info("Successful, in method: getAllAdmins, into the class: AdminController!");
                    } else {
                        resp.setStatus(404);
                    }

                } catch (DAOException e) {
                    logger.error("Error, in method: getAllAdmins, into the class: AdminController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Delete admin by login
        if (commandQuery.equals(deleteByLogin)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    adminService.deleteByLogin(nameQuery);
                    resp.setStatus(200);
                    resp.setContentType("application/json");
                    logger.info("Successful, in method: deleteByLogin, into the class: AdminController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: deleteByLogin, into the class: AdminController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Rename AdminName in DB
        if (commandQuery.equals(renameAdminName)) {

            String queryAdminLogin = queries.get(1);
            String queryNewName = queries.get(2);

            System.out.println("commandQuery = " + commandQuery);
            System.out.println("queryAdminLogin = " + queryAdminLogin);
            System.out.println("queryNewName = " + queryNewName);

            if ((queryAdminLogin != null && !queryAdminLogin.isEmpty()) && (queryNewName != null && !queryNewName.isEmpty())) {
                try {
                    admin = adminService.getByLogin(queryAdminLogin);
                    adminService.update(admin, new Admin(admin.getAdminId(), queryNewName, admin.getAdminLogin(), admin.getAdminPass()));
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: renameAdminName, into the class: AdminController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: renameAdminName, into the class: AdminController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }
    }
}