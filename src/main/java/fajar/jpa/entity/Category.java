package fajar.jpa.entity;


import fajar.jpa.listener.UpdatedAtListener;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Calendar;

@Entity
@Table(name = "categories")
@Data
@EntityListeners({
        UpdatedAtListener.class//apapun yang terjadi pada Category maka class ini akan dipanggil untuk updatedAt dan createdAt
})
public class Category implements UpdatedAtAware{

    //untuk entity pastikan menggunakan tipe data object jangan non object ex = Boolean, ini karna di db tipe data bisa null, jika menggunakan tipe primitif tidak bisa null
    private String name;

    private String description;

    /*
    strategy IDENTITY -> driver DB MYSQL
    strategy SEQUEN -> driver DB PosgreSql
    dll(https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/generationtype)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//membuat id auto generated sehingga tidak perlu menambahkan lagi Id
    private String id;


    @Temporal(TemporalType.TIMESTAMP)//konversi ke tipe DateTime yang ada di package java.time
    @Column(name = "created_at")
    private Calendar createdAt;//karna kita menggunakan library lama (Java.Calendar) maka kita perlu annotation @Temporal

    @Column(name = "updated_at")//nama kolom yang ada di DB
    private LocalDateTime updatedAt;//sudah menggunakan java DataTime yang baru

}
