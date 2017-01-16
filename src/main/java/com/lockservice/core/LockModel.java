package com.lockservice.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ishank on 15/1/17.
 * Class to store lock data.
 */

@Entity
@Table(name = "locks")
@NamedQueries(
        {
/*                @NamedQuery(
                        name = "com.lockservice.core.LockModel.acquireLock",
                        query = "INSERT INTO LockModel (userId) " +
                                "SELECT :_id " +
                                "WHERE NOT EXISTS (SELECT l FROM LockModel l)"
                ),*/
/*                @NamedQuery(
                        name = "com.lockservice.core.LockModel.isLocked",
                        query = "SELECT CASE " +
                                "WHEN EXISTS (SELECT l FROM LockModel l LIMIT 1) THEN l.userId " +
                                "ELSE 0 " +
                                "END"
                )*/
                @NamedQuery(
                        name = "com.lockservice.core.LockModel.getAll",
                        query = "SELECT l FROM LockModel l"
                ),
                @NamedQuery(
                        name = "com.lockservice.core.LockModel.releaseLock",
                        query = "DELETE FROM LockModel l WHERE l.userId = :_id"
                ),
                @NamedQuery(
                        name = "com.lockservice.core.LockModel.isLockedByUser",
                        query = "SELECT l FROM LockModel l WHERE l.userId = :_id"
                )
        }
)
public class LockModel implements Serializable{
    /**
     * Identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Id of user which has acquired lock.
     */

    @Column(name = "user_id", length = 30)
    private String userId;


    public LockModel(){}

    public LockModel(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LockModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockModel lockModel = (LockModel) o;

        if (id != lockModel.id) return false;
        return userId.equals(lockModel.userId);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + userId.hashCode();
        return result;
    }
}
