package fajar.jpa;

import fajar.jpa.entity.Category;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneratedValueTest {


    /** Generated Value
     * Beberapa database, kadang memiliki fitur membuat Primary Key yang diisi secara otomatis
     * Contohnya di MySQL terdapat fitur Auto Increment untuk Primary Key dengan tipe Number
     * Hal ini menyebabkan dalam JPA, kita tidak bisa mengubah nilai Id nya, karena Id akan dibuat oleh database
     * Pada kasus seperti ini, kita bisa menandai bahwa value Id dibuat secara otomatis oleh database menggunakan annotation GeneratedValue
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/generatedvalue

     ** Generated Value Strategy
     * Selain Auto Increment, terdapat cara lain untuk membuat Id secara otomatis, contohnya di PostgreSQL menggunakan Sequence, atau menggunakan Table lain untuk melakukan management Id, atau bahkan menggunakan UUID
     * Oleh karena itu, kita perlu memberi tahu Strategy apa yang dilakukan untuk membuat Id nya
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/generationtype
     */

    @Test
    void testGeneratedValue() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();


        transaction.begin();
        Category category = new Category();
        category.setName("Phone");
        category.setDescription("HandPhone Low Budget");

        entityManager.persist(category);

        Assertions.assertNotNull(category.getId());

        transaction.commit();

    }


    @Test
    void testRemoveCategory() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();


        transaction.begin();
        Category category = entityManager.find(Category.class, "2");//kita select dulu setelah itu dihapus
        entityManager.remove(category);



        transaction.commit();
    }
}
