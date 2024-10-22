package fajar.jpa.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employees")
@DiscriminatorValue("EMPLOYEE")
@DiscriminatorColumn(name = "type")//wajib ditambahkan,karna ketika find atau update ke database yang ada dicompare adalah typenya
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//Strategi yang digunakanx`xgti
@Data
public class Employee {

    /*
        CREATE TABLE employees(
            id VARCHAR(100) NOT NULL PRIMARY KEY,
            type VARCHAR(50) NOT NULL,
            name VARCHAR(100) NOT NULL,
            total_manager INT,      nilai dari colom ini harus null karna menggunakan single table
            total_employee INT      nilai dari colom ini harus null karna menggunakan single table
        )engine = InnoDB;




     */



    // dari ketiga entity (VicePresident, Manager, dan Employee) harus dimerge dalam satu table

    @Id
    private String id;


    private String name;

}
