package fajar.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class DepartmentId implements Serializable {

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "department_id")
    private String departmentId;

}
