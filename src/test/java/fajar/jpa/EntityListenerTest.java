package fajar.jpa;

import fajar.jpa.entity.Category;
import fajar.jpa.entity.Member;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityListenerTest {

    /** Entity Listener
     * Saat kita memanipulasi data Entity, kadang kita ingin melakukan instruksi program yang sama
     * Misal, setiap data diubah, kita ingin mengubah kolom updated_at menjadi waktu saat ini misalnya
     * Jika dilakukan di semua kode program kita, maka akan menyulitkan
     * Untungnya JPA memiliki fitur Entity Listener, dimana kita bisa membuat sebuah class Listener, yang nanti akan dipanggil oleh JPA ketika sebuah operasi terjadi terhadap Entity nya
     */

    @Test
    void testListener() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            Category category = new Category();
            category.setName("Contoh");

            entityManager.persist(category);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }
        entityManager.close();
        entityManagerFactory.close();

    }


    @Test
    void testListenerClass() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            //load Member fullname
            Member member = entityManager.find(Member.class, 1);
            Assertions.assertEquals("Staff. Fajar Abdillah Ahmad", member.getFullName());

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }
        entityManager.close();
        entityManagerFactory.close();

    }
}
