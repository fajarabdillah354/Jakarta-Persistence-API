package fajar.jpa.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.sql.exec.spi.StandardEntityInstanceResolver;

import java.util.List;
import java.util.Map;

@Entity
@Data
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private Name name;

    private String email;

    @ElementCollection
    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(//join dari hobbies dengan kolom member_id ke members dengan kolom id
            name = "member_id", referencedColumnName = "id"
    ))
    @Column(name = "name")//kolom yang diambil dari tamble hobbies
    private List<String> hobbies;


    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(
            name = "member_id", referencedColumnName = "id"
    ))
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    private Map<String, Integer> skills;


    @Transient
        private String fullName;

    //Listener dibawah ini ditambahkan langsung di entitynya
    @PostLoad
    public void postLoad(){//ketika di load maka field fullname akan berisi title,namadepan,tengah, dan belakang
        fullName = name.getTitle() + ". " + name.getFirstName() + " " + name.getMiddleName() + " " + name.getLastName();
    }

}
