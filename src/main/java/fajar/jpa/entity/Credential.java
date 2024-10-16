package fajar.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.scheduling.support.SimpleTriggerContext;

@Entity
@Table(name = "credentials")
@Data public class Credential {

    @Id
    private String id;

    private String email;

    private String password;

    @OneToOne(mappedBy = "credential")//mendefinikan di mapping di field credential
    private User user;

}
