package fajar.jpa.entity;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("MANAGER")
@Data
public class Manager extends Employee{

    @Column(name = "total_employee")
    private Integer totalEmployee;


}
