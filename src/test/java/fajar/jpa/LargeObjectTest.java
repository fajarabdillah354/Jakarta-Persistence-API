package fajar.jpa;

import fajar.jpa.entity.Image;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.nio.file.Path.of;

public class LargeObjectTest {


    /** Large Object
     * Kadang ada kasus kita membuat tipe kolom large object, yaitu tipe kolom yang digunakan untuk menampung data besar
     * Terdapat dua jenis Large Object di database
     * Character Large Object, untuk menampung tipe data Text besar, dan
     * Binary Large Object, untuk menampung tipe data Binary besar (misal gambar, audio, dan lain-lain)
     * @throws IOException
     */

    /*
        Java SQL
        java.sql.Clob untuk Character Large Object
        java.sql.Blob untuk Binary Large Object
     */

    /** Java Types
     * Namun, kadang menggunakan java.sql.Clob dan java.sql.Blob agak menyulitkan, karena harus membaca datanya secara manual menggunakan Java IO
     * JPA juga bisa digunakan untuk secara otomatis mengkonversi tipe data Large Object ke tipe data yang lebih familiar di Java
     * String dan char[] bisa digunakan untuk tipe data Character Large Object
     * byte[] bisa digunakan untuk tipe data Binary Large Object
     * Jika kita menggunakan tipe data ini, kita perlu menambahkan annotation Lob untuk memberi tahu ke JPA, bahkan ini adalah Large Object
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/lob
     * @throws IOException
     */

    @Test
    void testLargeObject() throws IOException {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


            entityTransaction.begin();
            Image image = new Image();
            image.setName("Code Fajar");
            image.setDescription("Screnshoot code fajar");
            byte[] bytes = Files.readAllBytes(Paths.get("D:/myProgramming/myJavaProgramming/belajar-java-JPA/target/classes/images/code.png"));


            image.setImage(null);
            entityManager.persist(image);

            entityTransaction.commit();


        entityManager.close();
        entityManagerFactory.close();

    }


    @Test
    void testLargeObject2() throws IOException {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Image image = new Image();
        image.setName("Fajar Code");
        image.setDescription("HTML Code From FAJAR");
        InputStream inputStream = getClass().getResourceAsStream("/images/code.png");//inputStream untuk mengambil resource path
        byte[] bytes = inputStream.readAllBytes();
        image.setImage(bytes);


        entityManager.persist(image);

        entityTransaction.commit();


        entityManager.close();
        entityManagerFactory.close();

    }

}
