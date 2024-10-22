package fajar.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "images")
@Data
public class Image {

    @Id//membuat primary key dari Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//untuk auto increment
    private Integer id;

    private String name;

    @Lob//Lob (Large Object) ini karna isi data ini kemungkinan besar
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

}
