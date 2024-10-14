package fajar.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass//membuat class tidak terregistrasi di database dan bukan bagian dari IS-A relationship, kita juga bisa men inheritance dari class ini
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditableEntity<T extends Serializable> {

    // JIKA membuat MapperSuperClass lebih baik dengan abstract sehingga kita tidak bisa membuat object dari MapperSuperClassnya


    @Id
    private T id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
