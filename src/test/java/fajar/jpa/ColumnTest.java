package fajar.jpa;

import fajar.jpa.entity.Customer;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.aop.scope.ScopedObject;

public class ColumnTest {


    /** Column
     * Secara default, nama attribute di Class Entity akan di mapping sebagai nama kolom di Table
     * Namun terkadang, nama kolom sering berbeda format dengan attribute di Class, misal di attribute Class menggunakan camelCase, sedangkan kolom di Table menggunakan snake_case
     * Untuk melakukan mapping kolom, kita bisa menggunakan annotation Column
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/column
     */

    @Test
    void testColumn() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Customer customer = new Customer();
        customer.setId("1");
        customer.setName("fajar");
        customer.setPrimaryEmail("fajardillah25@gmail.com");

        entityManager.persist(customer);

        entityTransaction.commit();


    }
}
