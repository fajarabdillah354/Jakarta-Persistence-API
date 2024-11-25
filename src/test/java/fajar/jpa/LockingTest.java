package fajar.jpa;

import fajar.jpa.entity.Brand;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class LockingTest {

    @Test
    void testOptimisticLockingVersion() {

        /** Optimistic Locking
         * Optimistic Locking diimplementasikan dengan cara menambah versi data pada Class Entity untuk memberitahu jika terjadi perubahan data pada Entity
         * Pada saat transaksi melakukan commit, trasaksi akan melakukan pengecekan versi terlebih dahulu, jika ternyata versi telah berubah di database, secara otomatis transaksi akan melakukan rollback
         * Optimistic Locking sangat cepat karena tidak butuh melakukan lock data, sehingga tidak perlu menunggu transaksi yang melakukan lock
         * Konsekuensinya, Pada Optimistic Locking, transaksi akan sering melakukan rollback jika ternyata data yang di commit sudah berubah
         */


        /** Version Column
         * Saat menggunakan Optimistic Locking, kita wajib membuat version column yang digunakan sebagai tanda perubahan yang sudah terjadi di data
         * JPA mendukung dua jenis tipe data version, Number (Integer, Short, Long) dan Timestamp (java.sql.Timestamp, java.time.Instant)
         * Untuk menandai bahwa attribute tersebut adalah version column, kita perlu menambahkan annotation Version
         * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/version
         * JPA akan secara otomatis mengupdate attribut version setiap kali kita melakukan update data tersebut
         */


        //Dalam optimistic locking walaupun ada banyak request hanya ada 1 yang akan menang dan berhasil, dimana 1 itu adalah yang paling cepat

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            Brand brand = new Brand();
            brand.setId("nokia");
            brand.setName("Nokia");
            brand.setCreatedAt(LocalDateTime.now());
            brand.setUpdatedAt(LocalDateTime.now());
            brand.setDescription("Nokia Phone Indonesia");
            entityManager.persist(brand);

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }

    @Test
    void testOptimisticLockingVersionDemo1() throws InterruptedException {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();



            entityTransaction.begin();
            Brand brand = entityManager.find(Brand.class, "nokia");
            brand.setName("Nokia Updated 2");
            brand.setCreatedAt(LocalDateTime.now());
            brand.setUpdatedAt(LocalDateTime.now());
            brand.setDescription("Updated Nokia Version 2");

            Thread.sleep(10*1000L);
            entityManager.persist(brand);


            entityTransaction.commit();


        entityManager.close();

    }



    @Test
    void testOptimisticLockingVersionDemo2() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            Brand brand = entityManager.find(Brand.class, "nokia");
            brand.setName("Nokia Updated 4");
            brand.setCreatedAt(LocalDateTime.now());
            brand.setUpdatedAt(LocalDateTime.now());
            brand.setDescription("Updated Nokia Version 4");


            entityManager.persist(brand);


            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();
    }



    @Test
    void testPessimisticLockingVersion() {

        /** Pessimistic Locking
         * Pada Optimistic Locking, pengecekan versi data dilakukan ketika melakukan commit
         * Pada Pessimistic Locking, data akan di lock ketika di select, dan ini menjadikan transaksi lain tidak bisa mengubah data sampai transaksi yang pertama melakukan lock selesai melakukan commit transaksi
         * JPA Mendukung banyak jenis tipe Lock, namun tetap saja, itu tergantung ke database yang digunakan, bisa saja database yang digunakan tidak mendukung semua jenis Lock yang terdapat di JPA
         * Semua jenis Lock terdapat di enum LockModeType
         * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/lockmodetype



         ** LockModeType
         * PESSIMISTIC_FORCE_INCREMENT -> Entity akan di lock secara pessimistic, dan versi akan dinaikkan walaupun data tidak di update
         * PESSIMISTIC_READ -> Entity akan di lock secara pessimistic menggunakan shared lock (jika database mendukung), SELECT FOR SHARE
         * PESSIMISTIC_WRITE -> Entity akan di lock secara explicit, SELECT FOR UPDATE


            ** Kenapa Pessimistic Locking
         * hal ini karena ketika transaksi pertama sudah sukses, bisa saja datanya diubah oleh transaksi kedua walaupun transaksi pertama lebih dulu selesai
         * Oleh karena itu disebut pessimistic
         */

        //ketika menggunakan PessimisticLocking data akan ditampung semua lalu akan di tunggu untuk di eksekusi semua, ini seperti konsep antrian data pasti akan di update hanya saja antri

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            Brand brand = new Brand();
            brand.setId("redmi");
            brand.setName("Redmi");
            brand.setCreatedAt(LocalDateTime.now());
            brand.setUpdatedAt(LocalDateTime.now());
            brand.setDescription("Redmi Phone Indonesia");
            entityManager.persist(brand);

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }


    @Test
    void testPessimisticLockingVersionDemo1() throws InterruptedException {

        //pada update ini akan menunggu selama 10 detik, baru data akan diupdate

        //Jika menggunakan PessimisticLocking ketika melakukan update 10 data, maka 10 datanya akan terupdate hanya saya ada proses antri dulu, berbeda dengan Optimistic yang konsepnya siapa cepat dia menang

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();



        entityTransaction.begin();
        Brand brand = entityManager.find(Brand.class, "redmi", LockModeType.PESSIMISTIC_WRITE);//untuk menggunakan fitur pessimistic harus menggunakan LockModeType.Pessimistic_ ...
        brand.setName("Redmi Updated 1");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        brand.setDescription("Updated Redmi to Version 1");

        Thread.sleep(10*1000L);//sleep selama 10 detik
        entityManager.persist(brand);


        entityTransaction.commit();


        entityManager.close();

    }


    @Test
    void testPessimisticLockingVersionDemo2() throws InterruptedException {
        //pada data ini tidak ada waktu tunggunya, data ini akan menunggu data demo1 hingga selesai baru akan di update

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();



        entityTransaction.begin();
        Brand brand = entityManager.find(Brand.class, "redmi",LockModeType.PESSIMISTIC_WRITE);//untuk menggunakan fitur pessimistic harus menggunakan LockModeType.Pessimistic_ ...
        brand.setName("Redmi Updated new");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        brand.setDescription("Updated Redmi to New Version");


        entityManager.persist(brand);


        entityTransaction.commit();


        entityManager.close();

    }


}
