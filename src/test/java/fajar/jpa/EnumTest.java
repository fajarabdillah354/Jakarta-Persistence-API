package fajar.jpa;

import fajar.jpa.entity.Customer;
import fajar.jpa.entity.CustomerType;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

public class EnumTest {


    /** Enum
     * Di Java, terdapat fitur Enum
     * Bagaimana cara menyimpan data Enum di Java ke table di database?
     * Jika kita memiliki attribute dengan tipe data Enum, maka kita perlu memberitahu JPA bagaimana caranya menyimpan data Enum nya ke Table dengan menggunakan annotation Eumerated
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/enumerated


     ** Enum Type
     */

    @Test
    void testEnumCustomer() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            Customer customer = new Customer();
            customer.setId("3");
            customer.setName("Rozak Abdullah");
            customer.setPrimaryEmail("rojakgantengBGT@gmail.com");
            customer.setAge((byte) 54);
            customer.setMarried(true);
            customer.setType(CustomerType.PREMIUM);
            entityManager.persist(customer);

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }


        entityManager.close();
        entityManagerFactory.close();


    }
}
