package fajar.jpa;

import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {


    /** Transaction
     * Saat kita menggunakan database, fitur yang wajib kita mengerti adalah database transaction
     * Saat belajar JDBC, kita sudah belajar dengan fitur database transaction di JDBC
     * Saat menggunakan JDBC, secara default operasi ke DB adalah auto commit, sehingga kita tidak perlu membuat transaction
     * Namun, di JPA, secara default kita diwajibkan menggunakan database transaction saat melakukan operasi manipulasi data entity
     * Transaction di JPA di representasikan dalam interface EntityTransaction
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/entitytransaction
     */

    @Test
    void testTransaction() {
        //saat membuat aplikasi database biasakan menggunakan transaction agar lebih mudah di maintence dan aman

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            // manupulation database

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }
}
