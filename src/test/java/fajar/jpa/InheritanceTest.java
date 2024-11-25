package fajar.jpa;

import fajar.jpa.entity.*;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class InheritanceTest {


    /** IS-A Relationship
     * Di dalam Entity Relationship Diagram (ERD), terdapat jenis relasi bernama IS-A
     * IS-A ini biasanya digunakan untuk mendukung konsep pewarisan di relational database, yang notabenenya tidak mendukung konsep ini
     * Jenis relasi IS-A sering sekali kita temui di kehidupan nyata
     * Contoh, struktur table Employee, dimana memiliki detail Manager, VicePresident, Supervisor, dan lain-lain
     * Atau struktur table Payment, dimana memiliki detail PaymentBCA, PaymentMandiri, PaymentCreditCard, dan lain-lain
     * ISA Relationship jika dalam OOP, maka implementasinya adalah berupa Inheritance / Pewarisan

     ** Implementasi IS-A Relationship
     * IS-A sendiri memiliki beberapa cara implementasi di table nya, yaitu :
     * Single Table Inheritance
     * Joined Table Inheritance, dan
     * Table Per Class Inheritance
     * Semua implementasinya akan kita bahas di materi-materi selanjutnya
     */


    @Test
    void testSingleTableInsert() {

        /** Single Table Inheritance
         * Single Table Inheritance artinya kita menyimpan seluruh Entity untuk relasi IS-A dalam satu table
         * Artinya semua kolom di semua Entity akan digabung dalam satu table
         * Plus nya dari strategi ini adalah mudah dan cepat, tidak butuh melakukan join
         * Minus nya, kita harus membuat semua kolom jadi nullable, karena tiap record hanya milik satu Entity
         */

        /** Parent Entity
         * Saat membuat Entity untuk IS-A Relationship, kita perlu membuat Parent Entity nya terlebih dahulu
         * Parent entity berisikan attribute yang tersedia di semua Child Entity nya
         * Dan khusus untuk Parent Entity, kita harus menyebutkan strategy Inheritance nya menggunakan annotation Inheritance
         * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/inheritance
         * Pada kasus Single Table Inheritance, kita wajib menggunakan Inheritance Type SINGLE_TABLE
         */

        /** Child Entity
         * Untuk Child Entity, kita cukup extends class Parent Entity
         * Pada kasus Single Table Inheritance, kita perlu memberi tahu JPA dari kolom dan value apa Child Entity tersebut dipilih
         * Oleh karena itu, kita perlu menambahkan annotation DiscriminatorColumn pada Parent
         * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/discriminatorcolumn
         * Dan pada Child Entity, kita harus menambahkan annotation DiscriminatorValue
         * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/discriminatorvalue
         */

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            Employee employee = new Employee();
            employee.setId("rina");
            employee.setName("RINA WATI");
            entityManager.persist(employee);


            Manager manager = new Manager();
            manager.setId("joko");
            manager.setName("JOKOWIDODO");
            manager.setTotalEmployee(10);//employee dibawah jokowi
            entityManager.persist(manager);


            VicePresident vicePresident = new VicePresident();
            vicePresident.setId("budi");
            vicePresident.setName("Budi Sudarsono");
            vicePresident.setTotalManager(40);
            entityManager.persist(vicePresident);

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }


    @Test
    void testSingleTableFind() {

        /** Find Entity IS-A
         * Pada kasus relasi ISA, kita bisa find data langsung spesifik ke Child Entity, atau lewat Parent Entity
         * Selain itu, pada kasus jika kita melakukan Find menggunakan Parent Entity, dan ternyata data tersebut adalah Child Entity, kita bisa konversi secara manual
         */

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            //contoh query dari parentnya
            Manager manager = entityManager.find(Manager.class,"joko");
            Assertions.assertEquals("JOKOWIDODO", manager.getName());

            //contoh query dari childnya
            Employee employee = entityManager.find(Employee.class,"budi");
            VicePresident vicePresident = (VicePresident) employee;//konversi dari Employee ke VicePresident
            Assertions.assertEquals("Budi Sudarsono", vicePresident.getName());

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }



    @Test
    void testJoinedTableInsert() {

        /** Joined Table Inheritance
         * Selanjutnya implementasi IS-A adalah menggunakan Join Table
         * Yang artinya, tiap Child Entity memiliki table masing-masing, namun akan melakukan JOIND PRIMARY KEY DENGAN TABLE PARENT ENTITY
         * Pada Joined Table Inheritance, kita tidak perlu menggunakan Discriminator Column lagi, karena data nya sudah terpisah table
         */

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            PaymentGopay paymentGopay = new PaymentGopay();
            paymentGopay.setId("gopay1");
            paymentGopay.setAmount(100000L);
            paymentGopay.setGopayId("gp08123");
            entityManager.persist(paymentGopay);

            PaymentCreditCard paymentCreditCard = new PaymentCreditCard();
            paymentCreditCard.setId("payment_card1");
            paymentCreditCard.setAmount(500_000L);
            paymentCreditCard.setMaskedCard("4552-8713-990912");
            paymentCreditCard.setBank("BCA");
            entityManager.persist(paymentCreditCard);



            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }


    @Test
    void testJoinedTableFind() {



        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            PaymentGopay paymentGopay = entityManager.find(PaymentGopay.class, "gopay1");
            PaymentCreditCard creditCard = entityManager.find(PaymentCreditCard.class, "payment_card1");

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }


    @Test
    void testJoinedTableFindParent() {

        //jika kita ingin query ke parentnya terus lebih menggunakan SINGLE JOIN TABLE bukan JOINED TABLE

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            // jika menggunakan joined table hati-hati kalo menggunakan query ke parent entitynya, dia akan melakukan join ke semua table childnya bayangkan jika childnya ada 40 akan sangat berbahaya
            Payment payment = entityManager.find(Payment.class, "gopay1");
//            PaymentGopay paymentGopay = (PaymentGopay) payment;
//            Assertions.assertEquals("gopay1", paymentGopay.getId());

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }


    @Test
    void testTablePerClassInsert() {

        /** Table Per Class Inheritance
         * Strategi terakhir untuk implementasi IS-A adalah dengan Table per Class
         * Yang artinya tiap Entity akan dibuatkan table masing-masing, artinya Parent Entity dan Child Entity akan memiliki table masing-masing
         * Strategi ini mirip seperti dengan JOIN, namun tiap table menyimpann full kolom
         * Harap bijak ketika menggunakan strategi ini, walaupun akan jadi lebih cepat kalo kita langsung melakukan find Child Entity (karena tidak butuh join), tapi saat melakukan find menggunakan Parent Entity, maka akan sangat lambat karena harus melakukan SELECT from SELECT
         *
         */


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            Transaction transaction = new Transaction();
            transaction.setId("t1");
            transaction.setBalance(100_000_000L);
            transaction.setCreatedAt(LocalDateTime.now());
            entityManager.persist(transaction);

            TransactionCredit transactionCredit = new TransactionCredit();
            transactionCredit.setId("t2");
            transactionCredit.setBalance(100_000_000L);
            transactionCredit.setCreatedAt(LocalDateTime.now());
            transactionCredit.setCreditAmount(90_000_000L);
            entityManager.persist(transactionCredit);

            TransactionDebit transactionDebit = new TransactionDebit();
            transactionDebit.setId("t3");
            transactionDebit.setBalance(50_000_000L);
            transactionDebit.setCreatedAt(LocalDateTime.now());
            transactionDebit.setDebitAmount(40_000_000L);
            entityManager.persist(transactionDebit);



            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }

    @Test
    void testTablePerClassRemove() {


        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            Transaction transaction = entityManager.find(Transaction.class, "t1");
            entityManager.remove(transaction);

            TransactionDebit transactionDebit = entityManager.find(TransactionDebit.class, "t2");
            entityManager.remove(transactionDebit);

            TransactionCredit transactionCredit = entityManager.find(TransactionCredit.class, "t3");
            entityManager.remove(transactionCredit);




            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }


    @Test
    void testTablePerClassFindChild() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            // saat melakukan find child tidak ada join table ke table parentnya, hati2 jika query ke table parentnya

            TransactionDebit transactionDebit = entityManager.find(TransactionDebit.class, "t2");

            TransactionCredit transactionCredit = entityManager.find(TransactionCredit.class, "t3");

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }

    @Test
    void testTablePerClassFindParent() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();

            // jika ingin query ke table parent jangan menggunakan strategi ini, karna akan sangat lambat karna kompllek disarankan make Joined table atau kalo sederhana make Single Join

            Transaction transaction = entityManager.find(Transaction.class, "t1");

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }


    @Test
    void testMappedSuperClass() {
        /** Mapped Superclass
         * Saat membuat Class Entity, kadang ada beberapa Class Entity yang memiliki attribute yang sama, namun bukan bagian dari IS-A Relationship
         * Pada kasus OOP, biasanya kita membuat Parent Class sebagai class yang berisikan attribute-attribute yang sama
         * Pada kasus Entity, kita bisa membuat Parent Class juga, namun Kita perlu memberi annotation MapperSuperclass untuk memberi tahu ini hanya sebuah Parent Class tanpa IS-A Relationship
         * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/mappedsuperclass
         */

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();
            Brand brand = new Brand();
            brand.setId("xiomi");
            brand.setName("Xiomi");
            brand.setDescription("Xiomi Phone Indonesia");
            brand.setCreatedAt(LocalDateTime.now());
            brand.setUpdatedAt(LocalDateTime.now());
            entityManager.persist(brand);

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }

        entityManager.close();

    }
}
