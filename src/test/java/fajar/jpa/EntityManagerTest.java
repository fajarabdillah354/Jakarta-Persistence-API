package fajar.jpa;

import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityManagerTest {


    /** Entity Manager
     * EntityManagerFactory itu mirip seperti DataSource, digunakan untuk melakukan management EntityManager
     * EntityManager mirip seperti Connection pada JDBC, dimana jika kita ingin berinteraksi dengan database, maka kita akan menggunakan EntityManager
     * EntityManager dibuat ketika kita butuh berinteraksi dengan database, dan setelah selesai, kita perlu menutupnya, sama seperti ketika kita menggunakan Connection pada JDBC
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/entitymanager EntityManagerFactory itu mirip seperti DataSource, digunakan untuk melakukan management EntityManager
     * EntityManager mirip seperti Connection pada JDBC, dimana jika kita ingin berinteraksi dengan database, maka kita akan menggunakan EntityManager
     * EntityManager dibuat ketika kita butuh berinteraksi dengan database, dan setelah selesai, kita perlu menutupnya, sama seperti ketika kita menggunakan Connection pada JDBC
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/entitymanager
     */


    @Test
    void createEntityManager() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        //OPERASI DATABASE
        Assertions.assertNotNull(entityManager);

        entityManager.close();

        entityManagerFactory.close();


    }
}
