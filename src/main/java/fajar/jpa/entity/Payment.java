package fajar.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payments")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Payment {

    @Id
    private String id;


    private Long amount;




}
