package fajar.jpa;

import fajar.jpa.entity.Customer;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.UserTransaction;
import org.junit.jupiter.api.Test;

public class TransientTest {

    /** Transient
     * Saat membuat attribute di Class Entity, secara default attribute tersebut akan dianggap sebagai kolom di Table
     * Kadang ada kasus kita ingin membuat attribute yang bukan kolom di table
     * Pada kasus ini, kita bisa menambahkan annotation Transient, untuk menandai bahwa attribute tersebut bukan kolom di Table, sehingga akan di ignore oleh JPA
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/transient
     */

    @Test
    void testTransient() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            Customer customer = new Customer();
            customer.setId("4");
            customer.setName("Joko");
            customer.setPrimaryEmail("Jokowikwik@gmail.com");
            customer.setAge((byte) 67);
            customer.setMarried(true);
            customer.setFullName("JokowiDodo");//kolum fullName tidak ada di db, karna ini Transient
            entityManager.persist(customer);
            entityTransaction.commit();

        }catch (Exception e){
            entityTransaction.rollback();

        }

        entityManager.close();
        entityManagerFactory.close();

    }
}
