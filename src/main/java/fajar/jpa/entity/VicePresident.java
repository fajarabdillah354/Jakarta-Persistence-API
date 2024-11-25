package fajar.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("VP")
@Data
public class VicePresident extends Employee{


    @Column(name ="total_manager")
    private Integer totalManager;

}
