package fajar.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;


/** Entity
 * Entity adalah Class yang merepresentasikan table di database
 * Di JPA, saat kita membuat Class Entity, maka kita perlu menambahkan annotation @Entity pada class tersebut
 * Class Entity adalah sebuah class Java Bean, dimana tiap attribute (yang memiliki getter dan setter) di Class tersebut merepresentasikan kolom di table database
 * Class Entity wajib memiliki default constructor yang tidak memiliki parameter, hal ini agar JPA bisa membuat object baru tanpa parameter ketika melakukan mapping data dari table ke object Entity
 * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/entity



 ** @Idq
 * Saat membuat Class Entity, JPA mewajibkan tiap Class Entity memiliki Primary Key
 * Walaupun di database seperti MySQL, Primary Key tidak wajib, namun di JPA hal itu diwajibkan
 * Untuk menandai sebuah atribut di Class Entity adalah Primary Key, kita harus menggunakan annotation Id
 * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/id


 ** @Table(name = )
 * Saat kita membuat Class Entity, secara default nama table di database akan menggunakan nama Class Entity nya
 * Kadang kita membuat nama Table dalam bentuk jamak, misal customers, sehingga berbeda dengan nama class Customer
 * Untuk mengubah informasi Table pada Class Entity, kita bisa menambahkan annotation Table pada Class Entity nya
 * https://jakarta.ee/specifications/persistence/3.1/apidocs/jakarta.persistence/jakarta/persistence/table

 */

@Entity
@Data
@Table(name = "customers")
public class Customer {

    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "id")
    private String id;



    //jika field entity sama dengan kolom name yang ada di db maka penggunaan @Column opsional
    @Column(name = "primary_email")//ini adalah penulisan di db, dengan begini maka primaryEmail yang ada di entity tetep bisa sinkron ke dalam db
    private String primaryEmail;//walaupun camelCase tapi kita bisa sinkron ke db dengan annotation @Column(name""), dimana parameter name adalah nama kolom yang dibuat dalam database

    private Boolean married;

    private Byte age;

    @Enumerated(EnumType.STRING)
    private CustomerType type;

    @Transient//dengan annotation ini berarti field fullName akan di ignore, field ini tidak akan masuk ke dalam db saat proses manipulasi
    private String fullName;


}
