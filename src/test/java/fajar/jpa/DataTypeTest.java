package fajar.jpa;

import fajar.jpa.entity.Customer;
import fajar.jpa.entity.CustomerType;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

public class DataTypeTest {


    /** Basic Data Type
     * JPA akan melakukan mapping data type secara otomatis dari table ke Class Entity
     * Secara default, tipe data dasar yang biasa digunakan di Java sudah didukung oleh JPA
     * Yang kita hanya perlukan adalah memastikan tipe data di Class Entity sama dengan tipe data di kolom Table di Database
     * Usahakan setiap field yang dibuat dalam class entity menggunakan tipe data non primitive atau object


     ** Daftar Tipe Data
     * Semua Number (Byte, Short, Integer, Long, Float, Double)
     * Semua Big Number (BigInteger, BigDecimal)
     * Boolean
     * String & Character
     */

    @Test
    void testDataType() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Customer customer = new Customer();
        customer.setId("2");
        customer.setName("Udin Sariudin");
        customer.setPrimaryEmail("udienkeren@gmail.com");
        customer.setAge((byte) 35);
        customer.setMarried(true);

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();

        //pastikan tipe datanya compatibel antara entity class dan databasenya

    }


    @Test
    void testDataTypeUpdate() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Customer customer = entityManager.find(Customer.class, "2");
        customer.setType(CustomerType.VIP);//penggunaan @Enumereted dengan enumType STRING
        entityManager.merge(customer);

        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();



    }

}
