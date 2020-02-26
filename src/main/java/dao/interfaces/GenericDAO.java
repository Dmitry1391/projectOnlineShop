package dao.interfaces;

import dao.exceptions.DAOException;

import java.util.List;

/**
 * This unified interface to unite CRUD operations.
 *
 * @param <E>
 * @author Huborevich Dmitry.
 * Created by 21.11.2019
 */
public interface GenericDAO<E> {

    /**
     * Basic method for creat entity.
     *
     * @param entity - accepts entity.
     * @return - creating entity in the database.
     * @throws DAOException - throw the exception above.
     */
    boolean create(E entity) throws DAOException;

    /**
     * Basic method for update entity.
     *
     * @param oldEntity;
     * @param newEntity;
     * @return - updating entity in the database.
     * @throws DAOException - throw the exception above.
     */
    boolean update(E oldEntity, E newEntity) throws DAOException;

    /**
     * Basic method for delete entity.
     *
     * @param entity;
     * @return - delete entity in the database.
     * @throws DAOException - throw the exception above.
     */
    boolean delete(E entity) throws DAOException;

    /**
     * Basic method for get all entity.
     *
     * @return - list all entities from the database.
     * @throws DAOException - throw the exception above.
     */
    List<E> getAll() throws DAOException;
}