package fajar.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "payments_credit_card")
@Data
public class PaymentCreditCard extends Payment{

    @Column(name = "masked_card")
    private String maskedCard;

    private String bank;

}
