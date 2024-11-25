package fajar.jpa.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Transaction {

    @Id
    private String id;

    private Long balance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
