package fajar.jpa;

import fajar.jpa.entity.Customer;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CrudTest {


    /** CRUD
     * Untuk melakukan proses CRUD (Create, Read, Update, Delete) ke database, kita bisa menggunakan EntityManager
     * Secara otomatis JPA akan membuatkan perintah SQL untuk INSERT, UPDATE, DELETE dan SELECT dari Entity Class yang kita gunakan


     ** Entity Manager Method
        persist(entity) -> Untuk menyimpan entity,
        merge(entity) -> merge(entity),
        remove(entity) -> Untuk menghapus entity,
        find(Class, id) -> Untuk mendapatkan entity berdasarkan id


     */


    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setUp(){
        entityManagerFactory = JpaUtil.getEntityManagerFactory();
    }

    @Test
    void testInsert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        Customer customer = new Customer();
        customer.setId("1");
        customer.setName("fajar abdillah ahmad");
        entityManager.persist(customer);//untuk memasukan data ke dalam database
        entityTransaction.commit();
        entityManager.close();

        // jika ada penambahan colum tinggal tambahkan di databasenya lalu di entitynya juga ditambahkan field

    }

    @Test
    void testFindEntity() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();


        transaction.begin();
        Customer customer = entityManager.find(Customer.class, "1");//.find() untuk melakukan query select pada database
        Assertions.assertEquals("1", customer.getId());
        Assertions.assertEquals("fajar abdillah ahmad", customer.getName());

        transaction.commit();

        //dengan menggunkan find kita tidak perlu menggunakan query manual lagi hanya menggunakan .find(Class, object)
    }


    @Test
    void testUpdateEntity() {
        // kita find dulu lalu diubah

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();


        transaction.begin();
        Customer customer = entityManager.find(Customer.class, "1");//.find() untuk melakukan query select pada database

        customer.setName("fajar ajjah");//merubah name yang ada di class Customer
        entityManager.merge(customer);//kita akan merubah name menjadi fajar ajjah

        transaction.commit();
    }


    @Test
    void testRemoveEntity() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();


        transaction.begin();
        Customer customer = entityManager.find(Customer.class, "1");//.find() untuk melakukan query select pada database

        entityManager.remove(customer);//yang diremove bukan id tetapi entitynya

        transaction.commit();

    }
}
