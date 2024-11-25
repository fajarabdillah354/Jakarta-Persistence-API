package fajar.jpa;

import fajar.jpa.entity.Department;
import fajar.jpa.entity.DepartmentId;
import fajar.jpa.entity.Member;
import fajar.jpa.entity.Name;
import fajar.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmbeddedTest {
    /** Embedded
     * Saat membuat Class Entity, praktek yang baik adalah membuat jenis Class sesuai dengan yang dibutuhkan, Misal jika dalam Class Member, terdapat informasi seperti firstName, middleName, lastName, title, mungkin lebih baik digabung dalam satu Class Name misalnya
     * Namun jika kolom nya masih di table yang sama, kadang menyulitkan jika kita harus tetap membuat atribut nya pada Class Entity yang sama, karena lama-lama atribut nya akan semakin banyak
     * Untungnya di JPA terdapat fitur Embedded, dimana kita bisa menambahkan atribut berupa Class lain, namun tetap mapping ke table yang sama


     ** Embeddable
     * Untuk menandai bahwa sebuah class itu bisa digunakan sebagai atribut di Class Entity atau istilahnya embedded atribut, maka kita wajib menambahkan annotation Embeddable di Class nya
     * Selanjutnya isi atribut pada Class Embedded tersebut, bisa kita tambahkan annotation Column seperti biasanya
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/embeddable

     ** Embedded
     * Saat kita menggunakan atribut class Embedded di Class Entity, kita wajib menggunakan annotation Embedded untuk menandai bahwa itu ada class Embedded
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/embedded

     */



    @Test
    void tesEmbedded() {

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            Name name = new Name();
            name.setTitle("Staff");
            name.setFirstName("Fajar");
            name.setMiddleName("Abdillah");
            name.setLastName("Ahmad");

            Member member = new Member();
            member.setEmail("fajarabdillahahmad@gmail.com");
            member.setName(name);

            entityManager.persist(member);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();
    }


    /** Embedded ID
     * Id adalah representasi dari Primary Key di Table
     * Seperti yang sudah kita bahas di materi sebelumnya, Class Entity wajib memiliki Id
     * Namun bagaimana jika ternyata ada table dengan Primary Key lebih dari satu kolom?
     * Pada kasus seperti ini, kita tidak bisa membuat dua atribut dengan annotation Id, karena method-method yang terdapat di EntityManager hanya menggunakan 1 parameter sebagai ID
     * Oleh karena itu, kita perlu menggunakan Class Embedded
     * Dan untuk menandai bahwa atribut itu adalah Id, kita gunakan annotation EmbeddedId
     * Khusus untuk class Embedded untuk Primary Key, direkomendasikan implements Serializable
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/embeddedid
     */

    @Test
    void testEmbeddedId() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            DepartmentId id = new DepartmentId();
            id.setCompanyId("Tilabs");
            id.setDepartmentId("dev");

            Department department = new Department();
            department.setName("Developer");
            department.setDepartmentId(id);
            entityManager.persist(department);

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();

    }


    @Test
    void testEmbeddedFind() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();


        try {
            entityTransaction.begin();
            DepartmentId id = new DepartmentId();
            id.setCompanyId("Tilabs");
            id.setDepartmentId("dev");

            //ini jika primarynya lebih dari 1 kolom
            Department department = entityManager.find(Department.class, id);//untuk Find kita menggunakan DepartmentId sebagi parameter Objectnya
            Assertions.assertEquals("Developer", department.getName());

            entityTransaction.commit();
        }catch (Exception exception){
            entityTransaction.rollback();
        }

        entityManager.close();
        entityManagerFactory.close();

    }



}
