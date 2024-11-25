package fajar.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    private String id;

    private String name;

    private Long price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)//dengan menambahkan value pada annotation ManyToOnenya, maka tidak akan dijoin left
    @JoinColumn(name = "brand_id",
    referencedColumnName = "id")//khusu manyToOne harus tambahkan joinColumn
    private Brand brand;


    @ManyToMany(mappedBy = "likes")
    private List<User> likedBy;

}
