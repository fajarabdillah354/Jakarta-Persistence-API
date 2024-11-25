package fajar.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transactions_credit")
@Data
public class TransactionCredit extends Transaction{


    @Column(name = "credit_amount")
    private Long creditAmount;



}
