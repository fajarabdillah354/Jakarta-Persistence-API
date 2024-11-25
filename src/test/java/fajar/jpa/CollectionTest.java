package fajar.jpa;

import fajar.jpa.entity.Member;
import fajar.jpa.entity.Name;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class CollectionTest {





    /** Collection Update
     * Hati-hati ketika menggunakan Collection Element
     * Setiap kita mengubah data di Collection Element, JPA akan menghapus dulu seluruh data di table Collection nya, setelah itu akan melakukan insert data baru
     * Oleh karena itu, Id pada Collection Element akan selalu berubah
     * Jadi pastikan Id pada table Collection Element tidak digunakan sebagai Foreign Key di table lain
     * Lantas bagaimana jika kita ingin menggunakan table Collection Element sebagai FK di table lain?
     * Maka kita jangan menggunakan Collection Element, kita harus menggunakan JPA Entity Relationship yang akan dibahas di bab khusus
     *
     ** Collection
     *  Saat kita membuat Data dalam bentuk Class, kita sering sekali membuat atribut dalam bentuk Collection, dan itu sangat mudah
     * Namun hal itu tidak mudah dilakukan di Table, kita harus membuat table terpisah dengan relasi One to Many
     * JPA mendukung database relation, namun pada kasus yang sangat sederhana, kita bisa membuat atribut dengan tipe Collection yang secara otomatis menggunakan Table Join untuk mengambil datanya


     ** Tipe Data Collection
     * JPA mendukung banyak tipe data collection, seperti List<T> atau Set<T>
     * Saat kita menggunakan tipe data collection, secara otomatis kita perlu membuat table untuk menyimpan data collection nya
     * Atribut collection harus kita tandai dengan annotation ElementCollection
     * Dan untuk menentukan table mana yang digunakan sebagai table untuk menyimpan Collection, kita gunakan annotation CollectionTable
     * Dan untuk menentukan kolom mana di table collection yang kita ambil datanya, kita bisa gunakan annotation Column seperti biasaya



     */

    @Test
    void testCreate() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            Name name = new Name();
            name.setFirstName("Fahmi");
            name.setMiddleName("Fadilah");
            name.setLastName("Ahmad");

            Member member = new Member();
            member.setEmail("fahmiGG@gmail.com");
            member.setName(name);
            member.setHobbies(new ArrayList<>());
            member.getHobbies().add("Coding");
            member.getHobbies().add("Gaming");
            member.getHobbies().add("Streaming");

            entityManager.persist(member);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();
    }




    @Test
    void testUpdate() {
        //khusus untuk tipe collection ketika update hibernate akan menghapus dulu semua data yang ada di table collectionnya lalu diinsert ulang

        // perhatikan jika buat field collection eleman pastikan di tablenya tidakk ada relasi ke table lainnya

        // ketika di select lagi pada db maka Id akan berubah karna yang awalnya sudah terdeleted
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            Member member = entityManager.find(Member.class, 2);

            member.getHobbies().add("Traveling");
            entityManager.merge(member);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();
    }


    /** Map Collection
     * Selain List<T> dan Set<T>, kita juga bisa menggunakan collection Map<K, V> sebagai atribut di Class Entity
     * Cara penggunaannya hampir mirip dengan List<T> atau Set<T>, namun karena pada Map<K, V> terdapat key dan value, maka kita harus memberi tahu JPA untuk kolom key dan kolom value menggunakan annotation MapKeyColumn, sedangkan untuk value nya tetap menggunakan annotation Column
     */

    @Test
    void testUpdateSkills() {
        //khusus untuk tipe collection ketika update hibernate akan menghapus dulu semua data yang ada di table collectionnya lalu diinsert ulang

        // perhatikan jika buat field collection eleman pastikan di tablenya tidakk ada relasi ke table lainnya

        // ketika di select lagi pada db maka Id akan berubah karna yang awalnya sudah terdeleted
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            Member member = entityManager.find(Member.class, 2);
            member.setSkills(new HashMap<>());
            member.getSkills().put("Java", 90);
            member.getSkills().put("Golang", 90);
            member.getSkills().put("Kotlin", 90);

            //behaviornya sama dengan collection yang tipenya bukan Map

            entityManager.merge(member);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();
    }

    //DISARANKAN MENGGUNAKAN JPA ENTITY TIDAK MENGGUNAKAN COLLECTION

}
