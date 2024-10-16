package fajar.jpa.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    /** Konfigurasi JPA
     * Sebelum membuat EntityManagerFactory, kita perlu membuat konfigurasi JPA terlebih dahulu
     * Untuk melakukan konfigurasi JPA, kita perlu membuat file META-INF/persistence.xml pada resource yang berisikan konfigurasi JPA
     * Kita bisa lihat contoh template nya disini : https://gist.github.com/khannedy/e74bf1b7be064dfd16a626d948f04218


     ** Membuat Entity Manager Factory
     * Untuk membuat EntityManagerFactory, kita bisa menggunakan Persistence.createEntityManagerFactory(nama);
     * Method tersebut akan membaca semua konfigurasi pada file META-INF/persistence.xml
     */

    private static EntityManagerFactory entityManagerFactory = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("FAJAR-JPA");
        }

        return entityManagerFactory;
    }
}
