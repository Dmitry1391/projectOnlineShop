package controllers;

import dao.exceptions.DAOException;
import models.Client;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import services.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class to work with ClientController.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
@WebServlet("/ClientController")
public class ClientController extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(ClientController.class);

    private static final long serialVersionUID = 1L;

    private static final String KEY_CLIENT_ID = "clientId";
    private static final String KEY_CLIENT_NAME = "clientName";
    private static final String KEY_CLIENT_LOGIN = "clientLogin";
    private static final String KEY_CLIENT_PASS = "clientPass";
    private static final String KEY_CLIENT_B_LIST = "clientBList";

    private ClientService clientService;

    @Override
    public void init() {
        clientService = new ClientService();
    }

    // Writing in JSONArray for response
    private JSONArray clientListToArray(List<Client> clients) {

        JSONArray array = new JSONArray();

        for (Client client : clients) {
            JSONObject JSONClient = new JSONObject();
            JSONClient.put(KEY_CLIENT_ID, client.getClientId());
            JSONClient.put(KEY_CLIENT_NAME, client.getName());
            JSONClient.put(KEY_CLIENT_LOGIN, client.getClientLogin());
            JSONClient.put(KEY_CLIENT_PASS, client.getPassword());
            JSONClient.put(KEY_CLIENT_B_LIST, client.getBlackList());
            array.put(JSONClient);
        }
        return array;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String createClient = "createClient";
        String getByLoginAndPass = "getByLoginAndPass";
        String getByLogin = "getByLogin";
        String deleteByLogin = "deleteByLogin";
        String renameClientName = "renameClientName";
        String getAllClients = "getAllClients";
        String getAllClientsInBList = "getAllClientsInBList";
        String setClientInBList = "setClientInBList";
        String setClientOutBList = "setClientOutBList";

        List<String> queries = HttpQueryUtils.getListQuery(req);
        String commandQuery = queries.get(0);
        String nameQuery = queries.get(1);


        // Create client in DB
        if (commandQuery.equals(createClient)) {

            String queryRegName = queries.get(1);
            String queryRegLogin = queries.get(2);
            String queryRegPass = queries.get(3);

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("queryRegName = " + queryRegName);
            System.out.println("queryRegLogin = " + queryRegLogin);
            System.out.println("queryRegPass = " + queryRegPass);

            if ((queryRegName != null && !queryRegName.isEmpty()) && (queryRegLogin != null && !queryRegLogin.isEmpty()) && (queryRegPass != null && !queryRegPass.isEmpty())) {
                try {
                    clientService.create(new Client(queryRegName, queryRegLogin, queryRegPass, false));
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: createClient, into the class: ClientController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: createClient, into the class: ClientController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Set client in Black List in DB
        if (commandQuery.equals(setClientInBList)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("clientId = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    Client client = clientService.getById(Integer.parseInt(nameQuery));
                    Client newClient = new Client(client.getName(), client.getClientLogin(), client.getPassword(), true);
                    clientService.update(client, newClient);

                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: setClientInBList, into the class: ClientController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: setClientInBList, into the class: ClientController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Set client out Black List in DB
        if (commandQuery.equals(setClientOutBList)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("clientId = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    Client client = clientService.getById(Integer.parseInt(nameQuery));
                    Client newClient = new Client(client.getName(), client.getClientLogin(), client.getPassword(), false);
                    clientService.update(client, newClient);

                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: setClientOutBList, into the class: ClientController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: setClientOutBList, into the class: ClientController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Get client By client Login
        if (commandQuery.equals(getByLogin)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    Client client = clientService.getByLogin(nameQuery);
                    List<Client> clients = new ArrayList<Client>();
                    if (client != null) {
                        clients.add(client);
                        resp.setContentType("application/json");
                        resp.setStatus(200);
                        resp.getWriter().write(clientListToArray(clients).toString());
                        logger.info("Successful, in method: getByLogin, into the class: ClientController!");
                    } else {
                        resp.setStatus(404);
                    }
                } catch (DAOException e) {
                    logger.error("Error, in method: getByLogin, into the class: ClientController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Get client by clientLoginAndPass
        if (commandQuery.equals(getByLoginAndPass)) {

            String passQuery = queries.get(2);
            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);
            System.out.println("passQuery = " + passQuery);

            if ((nameQuery != null && !nameQuery.isEmpty()) && (passQuery != null && !passQuery.isEmpty())) {
                try {
                    Client client = clientService.getByLoginAndPass(nameQuery, passQuery);
                    List<Client> clients = new ArrayList<Client>();
                    if (client != null) {
                        clients.add(client);
                        resp.setContentType("application/json");
                        resp.setStatus(201);
                        resp.getWriter().write(clientListToArray(clients).toString());
                        logger.info("Successful, in method: getByLoginAndPass, into the class: ClientController!");
                    } else {
                        resp.setStatus(404);
                    }

                } catch (DAOException e) {
                    logger.error("Error, in method: getByLoginAndPass, into the class: ClientController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        //Delete client by clientLogin
        if (commandQuery.equals(deleteByLogin)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            if (nameQuery != null && !nameQuery.isEmpty()) {
                try {
                    Client client = clientService.getByLogin(nameQuery);
                    clientService.deleteByName(client.getName());
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: deleteByLogin, into the class: ClientController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: deleteByLogin, into the class: ClientController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        // Rename ClientName in DB
        if (commandQuery.equals(renameClientName)) {

            String queryClientLogin = queries.get(1);
            String queryNewName = queries.get(2);

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("queryClientLogin = " + queryClientLogin);
            System.out.println("queryNewName = " + queryNewName);

            if ((queryClientLogin != null && !queryClientLogin.isEmpty()) && (queryNewName != null && !queryNewName.isEmpty())) {
                try {
                    Client client = clientService.getByLogin(queryClientLogin);
                    clientService.update(client, new Client(client.getClientId(), queryNewName, client.getClientLogin(), client.getPassword(), client.getBlackList()));
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    logger.info("Successful, in method: renameClientName, into the class: ClientController!");
                } catch (DAOException e) {
                    logger.error("Error, in method: renameClientName, into the class: ClientController! \n" + e);
                }
            } else {
                resp.setStatus(404);
            }
        }

        //  Get all Clients in DB
        if (commandQuery.equals(getAllClients)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            try {
                List<Client> clients = clientService.getAll();
                if (!clients.isEmpty()) {
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    resp.getWriter().write(clientListToArray(clients).toString());
                    logger.info("Successful, in method: getAllClients, into the class: ClientController!");
                } else {
                    resp.setStatus(404);
                }
            } catch (DAOException e) {
                logger.error("Error, in method: getAllClients, into the class: ClientController! \n" + e);
            }
        }

        //  Get all Clients in Black List in DB
        if (commandQuery.equals(getAllClientsInBList)) {

            System.out.println("---------------------------------");
            System.out.println("commandQuery = " + commandQuery);
            System.out.println("nameQuery = " + nameQuery);

            try {
                List<Client> clients = clientService.getAll();
                List<Client> sendClients = new ArrayList<Client>();
                if (!clients.isEmpty()) {
                    for (Client client : clients) {
                        if (client.getBlackList()) {
                            sendClients.add(client);
                        }
                    }
                    resp.setContentType("application/json");
                    resp.setStatus(200);
                    resp.getWriter().write(clientListToArray(sendClients).toString());
                    logger.info("Successful, in method: getAllClientsInBList, into the class: ClientController!");
                } else {
                    resp.setStatus(404);
                }
            } catch (DAOException e) {
                logger.error("Error, in method: getAllClientsInBList, into the class: ClientController! \n" + e);
            }
        }
    }
}
