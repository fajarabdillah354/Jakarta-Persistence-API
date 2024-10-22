package fajar.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

@Entity
@Table(name = "wallet")
@Data
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private Long balance;


    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")//INI CONTOH YANG TIDAK MENGGUNAKAN PRIMARY KEY SEBAGAI RELASI ONE TO ONE
    private User user;
}
