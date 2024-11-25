package fajar.jpa;

import fajar.jpa.entity.*;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.event.MenuKeyEvent;
import java.util.ArrayList;

public class EntityRelationshipTest {


    /** Table Relationship
     * Salah satu fitur yang paling banyak digunakan saat menggunakan Relational Database adalah Table Relationship
     * Di kelas MySQL, kita sudah bahas secara lengkap tentang jenis-jenis relasi table, dari One to One, One to Many dan Many to Many
     * JPA juga bisa menangani Table Relationship secara otomatis, hal ini menjadikan memanipulasi data Entity yang berhubungan akan lebih mudah
     */

    /** One to One Relationship
     * One to One Relationship adalah relasi yang paling mudah, dimana satu table berelasi dengan satu data di table lain
     * Ada beberapa cara melakukan relasi pada one to one, dengan Foreign Key atau dengan Primary Key yang sama
     * JPA mendukung keduanya, baik itu menggunakan Foreign Key ataupun menggunakan Primary Key yang sama
     */

    /** OneToOne Annotation
     * Untuk menambah atribut di Entity yang berelasi dengan Entity lain, kita perlu menambahkan menggunakan annotation OneToOne
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/onetoone
     * Dan untuk memberi tahu JPA tentang kolom yang digunakan untuk melakukan JOIN Foreignn Key, kita perlu tambahkan annotation JoinColumn
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/joincolumn
     * Namun jika JOIN nya menggunakan primary key yang sama, kita bisa gunakan annotation PrimaryKeyJoinColumn
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/primarykeyjoincolumn
     */

    @Test
    void testOneToOnePersist() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            Credential credential = new Credential();
            credential.setId("abdllh");
            credential.setEmail("fajardillah25@gmail.com");
            credential.setPassword("rahasia354");
            entityManager.persist(credential);

            User user = new User();
            user.setId("abdllh");
            user.setName("fajar abdillah ahmad");
            entityManager.persist(user);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

    }

    @Test
    void testFindOneToOne() {

        //dengan ini kita tidak perlu melakukan join secara manual lagi

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            User user = entityManager.find(User.class,"abdllh");
            Assertions.assertNotNull(user.getCredential());

            Assertions.assertEquals("fajardillah25@gmail.com", user.getCredential().getEmail());
            Assertions.assertEquals("rahasia354", user.getCredential().getPassword());

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

    }

    @Test
    void testPersistOneToOneWallet() {

        //dengan ini kita tidak perlu melakukan join secara manual lagi

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            User user = entityManager.find(User.class, "abdllh");
            Wallet wallet = new Wallet();
            wallet.setUser(user);
            wallet.setBalance(100_000_000L);
            entityManager.persist(wallet);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

    }

    @Test
    void testFindWallet() {

        //dengan ini kita tidak perlu melakukan join secara manual lagi

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            User user = entityManager.find(User.class,"abdllh");
            Assertions.assertNotNull(user.getWallet());
            Assertions.assertEquals(100_000_000L, user.getWallet().getBalance());

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

    }


    /** One to Many Relationship
     * Untuk membuat Entity yang memiliki relasi One to Many dengan Entity lain, kita bisa menggunakan annotation OneToMany
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/onetomany
     * Dan atribut di Entity, tipe datanya harus dibungkus dalam collection, misal List<T> atau Set<T>
     * Relasi OneToMany jika dilihat dari arah sebaliknya adalah relasi ManyToOne, oleh karena itu, nanti di Class Entity sebelahnya, relasinya adalah ManyToOne
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/manytoone
     * Cara penggunaannya hampir mirip dengan relasi OneToOne, kita bisa gunakan JoinColumn pada Entity yang memiliki kolom Foreign Key nya yang menggunakan ManyToOne, dan cukup gunakan attribute mappedBy pada attribute yang menggunakan OneToMany
     */

    @Test
    void testOneToManyInsert() {

        //dengan ini kita tidak perlu melakukan join secara manual lagi

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {

            //OneTOMany relationship
            entityTransaction.begin();
            Brand brand = new Brand();
            brand.setId("samsung");
            brand.setName("Samsung");
            brand.setDescription("Samsung Phone LIMITED edition");
            entityManager.persist(brand);

            //product1 dan product2 satu brand (banyak ke 1 relasi)
            //PRODUCT1
            Product product1 = new Product();
            product1.setName("Samsung J2 Prime");
            product1.setBrand(brand);
            product1.setId("p1");
            product1.setDescription("Phone Samsung J2 Prime LIMITED EDITION");
            product1.setPrice(2_000_000L);
            entityManager.persist(product1);

            //PRODUCT2
            Product product2 = new Product();
            product2.setName("Samsung Galaxy S40 ++");
            product2.setBrand(brand);
            product2.setId("p2");
            product2.setDescription("New Samsung Galaxy S40 Ultra Light");
            product2.setPrice(9_000_000L);
            entityManager.persist(product2);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

    }


    @Test
    void testOneToManyFind() {

        //dengan ini kita tidak perlu melakukan join secara manual lagi

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            Brand brand = entityManager.find(Brand.class, "samsung");
            Assertions.assertEquals(2, brand.getListBrand().size());
            Assertions.assertNotNull(brand.getListBrand());

            brand.getListBrand().forEach(product -> {
                System.out.println(product.getName());
            });

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

    }


    /** Many To Many Relationship
     * Di kelas MySQL, kita sudah bahas tentang relasi Many to Many
     * Pada relasi Many to Many, kita tidak bisa hanya menggunakan dua table, biasanya kita akan menambahkan table ditengah sebagai jembatan relasi antara table pertama dan kedua
     * JPA juga mendukung relasi Many to Many



     ** Join Table
     * Untuk menggunakan relasi Many to Many, kita bisa menggunakan annotation ManyToMany
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/manytomany
     * Yang membedakan dengan relasi lain, karena Many to Many butuh table tambahan ditengah sebagai jembatan, oleh karena itu untuk melakukan join, kita menggunakan annotation JoinTable
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/jointable
     * Untuk table yang ditengah sebagai jembatan, kita tidak butuh membuat Class Entity nya
     */

    @Test
    void testManyToManyInsert() {

        //dengan ini kita tidak perlu melakukan join secara manual lagi

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            User user = entityManager.find(User.class, "abdllh");
            user.setLikes(new ArrayList<>());
            user.getLikes().add(entityManager.find(Product.class,"p1"));
            user.getLikes().add(entityManager.find(Product.class,"p2"));

            entityManager.merge(user);
            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

    }


    @Test
    void testManyToManyUpdate() {

        //dengan ini kita tidak perlu melakukan join secara manual lagi

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        User user = entityManager.find(User.class, "abdllh");

        user.getLikes().forEach(product -> {
            System.out.println("product :"+ product.getName());
        });



        entityTransaction.commit();

        entityManager.close();
    }



    @Test
    void testFetch() {

        /** Fetch
         * Saat kita membuat Entity Class yang kompleks dan banyak sekali relasinya, kita perlu berhati-hati
         * Hal ini karena secara default, beberapa jenis relasi memiliki value fetch EAGER, artinya saat kita melakukan find Entity, relasinya akan secara otomatis di JOIN, walaupun kita tidak membutuhkan relasinya
         * Kebalikan dari EAGER adalah LAZY, dimana artinya relasi akan di QUERY ketika kita memanggil attribute nya, jika tidak, maka tidak akan di QUERY
            One to One -> EAGER
            One to Many -> LAZY
            Many to One -> EAGER
            Many to Many -> LAZY

         **  Mengubah Fetch
         * Jika kita ingin mengubah nilai default Fetch, kita bisa ubah di attribut di annotation relasinya
         * Semua relasi dari One to One, One to Many, Many to One dan Many to Many memiliki attribute fetch yang bisa kita ubah

         */





        //jika kita butuh GetProduct langsung Get data Brandnya maka pada ManyToOne tidak perlu di ubah menjadi LAZY

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        // Product product = entityManager.find(Product.class, "p1");//ini akan selalu menggunakan left join karna Fetch dari ManyToOne adalah EIGER(dimana akan selalu di left join padahall terkadang kita tidak butuh)


        //berbeda dengan class Brand, karna dia relasinya OneToMany sifat dari Fetchnya adalah Lazy, yaitu ketika kita memanggil atributenya relasi akan di query
        Brand brand = entityManager.find(Brand.class,"samsung");


        entityTransaction.commit();

        entityManager.close();
    }



}
