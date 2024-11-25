package fajar.jpa;

import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityManagerFactoryTest {


    /** Entity Manager Factory
     * Saat menggunakan JPA, hal yang pertama kita perlu buat adalah EntityManagerFactory
     * Ini seperti DataSource, dimana kita hanya buat satu kali saja dalam satu aplikasi
     * EntityManagerFactory merupakan object yang sangat berat, karena perlu membaca Entity Class dan fitur-fitur lainnya saat pertama kali dibuat, oleh karena itu biasanya memang hanya dibuat sekali pada saat aplikasi berjalan saja
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/entitymanagerfactory
     */

    @Test
    void create() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        Assertions.assertNotNull(entityManagerFactory);

        entityManagerFactory.close();//kita harus menutupnya setelah menggunakannya
    }
}
