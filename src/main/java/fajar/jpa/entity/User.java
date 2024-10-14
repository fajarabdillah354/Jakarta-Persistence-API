package fajar.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {


    //join User ke Credential dengan one to one relationship

    @Id
    private String id;

    private String name;

    @OneToOne//relasi satu ke satu
    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")//dari id di User direlasikan ke id di Credential
    private Credential credential;

    @OneToOne(mappedBy = "user")
    private Wallet wallet;


    @ManyToMany//jika relasinya manytomany menggunakan anntotion @ManyToMany, dan menggunakan @JoinTable
    @JoinTable(
            name = "users_like_products",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id",referencedColumnName = "id")
    )
    private List<Product> likes;

}
