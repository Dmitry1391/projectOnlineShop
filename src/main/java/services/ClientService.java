package services;

import dao.exceptions.DAOException;
import dao.impl.DAOFactoryImpl;
import dao.interfaces.ClientDAO;
import models.Client;

import java.util.List;

/**
 * This class is designed to work with the ClientService, which is associated with the DAO layer.
 *
 * @author Huborevich Dmitry.
 * Created by 13.01.2020
 */
public class ClientService {

    private ClientDAO clientDAO  = DAOFactoryImpl.getInstance().getClientDAO();

    /**
     * Basic method for get all clients.
     *
     * @return - list all clients from the database.
     * @throws DAOException - throw the exception above.
     */
    public List<Client> getAll() throws DAOException {
        return clientDAO.getAll();
    }

    /**
     * Basic method for update client.
     *
     * @param oldClient;
     * @param newClient;
     * @return - updating client in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean update(Client oldClient, Client newClient) throws DAOException {
        return clientDAO.update(oldClient,newClient);
    }

    /**
     * Basic method for create client.
     *
     * @param client - accepts product.
     * @return - creating client in the database.
     * @throws DAOException - throw the exception above.
     */
    public boolean create(Client client) throws DAOException {
        return clientDAO.create(client);
    }

    /**
     * Method get client that matches to specified id.
     *
     * @param clientId - client id.
     * @return - null if there was no such user.
     * @throws DAOException ;
     */
    public Client getById(Integer clientId) throws DAOException {
        return clientDAO.getById(clientId);
    }

    /**
     * Method get client that matches to specified login.
     *
     * @param clientLogin - client login.
     * @return - null if there was no such user.
     * @throws DAOException;
     */
    public Client getByLogin(String clientLogin) throws DAOException{
        return clientDAO.getByLogin(clientLogin);
    }

    /**
     * Method get client that matches to specified login and pass.
     *
     * @param clientLogin - client login.
     * @param clientPass - client pass.
     * @return - null if there was no such user.
     * @throws DAOException;
     */
    public Client getByLoginAndPass(String clientLogin, String clientPass) throws DAOException{
        return clientDAO.getByLoginAndPass(clientLogin,clientPass);
    }

    /**
     * Method to get list of client which matches by specified name.
     *
     * @param clientName - client name.
     * @return - null if no users match to argument.
     * @throws DAOException;
     */
    public List<Client> getByName(String clientName) throws DAOException {
        return clientDAO.getByName(clientName);
    }

    /**
     * Method delete of client which matches by specified name.
     *
     * @param clientName - client name.
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    public boolean deleteByName(String clientName) throws DAOException {
        return clientDAO.deleteByName(clientName);
    }
}
