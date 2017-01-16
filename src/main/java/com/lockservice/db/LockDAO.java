package com.lockservice.db;

import com.lockservice.core.LockModel;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by ishank on 15/1/17.
 * A DAO class to access LockModel.
 */
public class LockDAO extends AbstractDAO<LockModel> {

    public LockDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Checks if lock is acquired by checking existence of a row in locks
     * table.
     * @return list of LockModel (rows) present in table.
     */
    public List<LockModel> isLocked() {
        return list(namedQuery("com.lockservice.core.LockModel.getAll").setMaxResults(1));
    }

    /**
     * Acquires lock by inserting user id in table.
     * @param userId id of user who wants to take lock.
     */
    public void acquireLock(String userId) {
        Session session = currentSession();
        session.save(new LockModel(userId));
    }

    /**
     * Check if lock has been acquired by a particular user.
     * @param userId id of user who wants to take lock.
     * @return row corresponding to user if present.
     */
    public List<LockModel> isLockedByUser(String userId) {
        return list(namedQuery("com.lockservice.core.LockModel.isLockedByUser")
                .setParameter("_id", userId));
    }

    /**
     * releases lock by deleting corresponding entry in table.
     * @param userId id of user who wants to release lock.
     */
    public void releaseLock(String userId) {
        namedQuery("com.lockservice.core.LockModel.releaseLock")
                .setParameter("_id", userId)
                .executeUpdate();
    }
}
