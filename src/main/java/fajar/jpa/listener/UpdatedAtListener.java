package fajar.jpa.listener;

import fajar.jpa.entity.UpdatedAtAware;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class UpdatedAtListener {
    /**
     * tambahkan sebuah method annotation eventnya jenisnya apa
     * lalu method ini akan dieksekusi sesuai jenis eventnya
     * dimana parameter di methodnya harus tambahkan entitynya
     * @param updatedAt


     ** Event Annotation
     * @PrePersist -> Dieksekusi sebelum melakukan persist entity
     * @PostPersist -> Dieksekusi setelah melakukan persist entity
     * @PreRemove -> Dieksekusi sebelum melakukan remove entity
     * @PostRemove -> Dieksekusi setelah melakukan remove entity
     * @PreUpdate -> Dieksekusi sebelum melakukan update entity
     * @PostUpdate -> Dieksekusi setelah melakukan update entity
     * @PostLoad -> Dieksekusi setelah melakukan load entity
     */


    //@PrePersist dan @PreUpdate akan dipanggil ketika insert data dan update data(kita bisa menambahkan yang lainnya)
    @PrePersist
    @PreUpdate
    public void setLastUpdatedAt(UpdatedAtAware updatedAt){//pada parameter kita bisa mengisi object namun harus dikonversi sesuai object yang dimau
        updatedAt.setUpdatedAt(LocalDateTime.now());
    }

}
