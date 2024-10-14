package fajar.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "brands")
@Data
public class Brand extends AuditableEntity<String>{



    private String name;

    private String description;

    @OneToMany(mappedBy = "brand")
    private List<Product> listBrand;//khusus oneToMany kita menggunakan Collection sebagai tempat menaruh data

    /** Version Column
     * Saat menggunakan Optimistic Locking, kita wajib membuat version column yang digunakan sebagai tanda perubahan yang sudah terjadi di data
     * JPA mendukung dua jenis tipe data version, Number (Integer, Short, Long) dan Timestamp (java.sql.Timestamp, java.time.Instant)
     * Untuk menandai bahwa attribute tersebut adalah version column, kita perlu menambahkan annotation Version
     * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/version
     * JPA akan secara otomatis mengupdate attribut version setiap kali kita melakukan update data tersebut
     */


    @Version// @Version UNTUK MELAKUKAN  LOCKING, ketika melakukan perubahan data version akan di update secara otomatis, nilai awal setelah melakukan insert adalah 0, dan jika terjadi perubahan akan increment secara otomatis
    private Long version;

}
