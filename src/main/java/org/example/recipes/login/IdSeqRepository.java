package org.example.recipes.login;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IdSeqRepository extends JpaRepository<IdSeq, String> {
    /** Lấy bản ghi có id lớn nhất (theo thứ tự desc trên chuỗi) */
    Optional<IdSeq> findTopByOrderByIdDesc();
}