package fajar.jpa;

import fajar.jpa.entity.Category;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Calendar;

public class DateTest {


    /** Date dan Time
     * Selain tipe data dasar, JPA juga bisa melakukan mapping tipe data Date dan Time secara otomatis
     * Saat kita belajar JDBC, tipe data Date dan Time hanya terbatas pada tipe data di package java.sql
     * Namun jika menggunakan JPA, kita bisa menggunakan tipe data Date, Timestamp, Calendar, bahkan class-class di Date and Time API seperti LocalDate, LocalTime dan lain-lain
     */


    /*
    Mapping Date dan Time API
        Class Java                                  Tipe Data Database
        1. java.sql.Date, java.time.LocalDate          DATE
        2. java.sql.Time, java.time.LocalTime          TIME
        3. java.sql.Timestamp, java.time.Instant,      TIMESTAMP
           java.time.LocalDateTime,
           java.time.OffsetDateTime,
           java.time.ZonedDateTime

     */

    /*
        Java Util
        * Bagaimana dengan tipe data java.util.Date dan java.util.Calendar
        * Tipe data itu sedikit membingungkan karena bisa digunakan untuk Date, Time dan Timestamp di database
        * Oleh karena itu, direkomendasikan menggunakan package java.time
        * Namun jika menggunakan package java.util, disarankan menambahkan annotation Temporal, untuk memberi tahu detail tipe data di database, apakah Date, Time atau Timestamp
        * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/temporal

     */

    @Test
    void testDateTime() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            Category category = new Category();
            category.setName("Food");
            category.setDescription("Indonesian Food");
            category.setCreatedAt(Calendar.getInstance());
            category.setUpdatedAt(LocalDateTime.now());
            entityManager.persist(category);

            entityTransaction.commit();
        }catch (Throwable throwable){
            entityTransaction.rollback();
        }
        entityManager.close();
        entityManagerFactory.close();

    }
}
