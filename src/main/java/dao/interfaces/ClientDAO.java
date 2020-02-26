package dao.interfaces;


import dao.exceptions.DAOException;
import models.Client;

import java.util.List;

/**
 * This interface defines operations that relate to the client entity.
 *
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public interface ClientDAO extends GenericDAO<Client> {

    /**
     * Method get client that matches to specified id.
     *
     * @param id - client id.
     * @return - null if there was no such user.
     * @throws DAOException;
     */
    Client getById(Integer id) throws DAOException;

    /**
     * Method get client that matches to specified login.
     *
     * @param clientLogin - client login.
     * @return - null if there was no such client.
     * @throws DAOException;
     */
    Client getByLogin(String clientLogin) throws DAOException;

    /**
     * Method get client that matches to specified login and pass.
     *
     * @param clientLogin - client login.
     * @param clientPass - client pass.
     * @return - null if there was no such user.
     * @throws DAOException;
     */
    Client getByLoginAndPass(String clientLogin, String clientPass) throws DAOException;

    /**
     * Method to get list of client which matches by specified name.
     *
     * @param name - client name.
     * @return - null if no users match to argument.
     * @throws DAOException;
     */
    List<Client> getByName(String name) throws DAOException;

    /**
     * Method delete of client which matches by specified name.
     *
     * @param name - client name.
     * @return - "true" in case of success, "false" otherwise.
     * @throws DAOException;
     */
    boolean deleteByName(String name) throws DAOException;

}