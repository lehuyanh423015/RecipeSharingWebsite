// src/main/java/org/example/recipes/login/IdGeneratorService.java
package org.example.recipes.login;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IdGeneratorService {
    private final IdSeqRepository seqRepo;
    private static final String PREFIX   = "ID";
    private static final int    PAD_SIZE = 8;

    public IdGeneratorService(IdSeqRepository seqRepo) {
        this.seqRepo = seqRepo;
    }

    /**
     * Sinh ID mới dạng PREFIX + zero-pad,
     * rồi insert vào bảng id_seq để đảm bảo không trùng.
     */
    @Transactional
    public synchronized String generateId() {
        // 1. Lấy id cao nhất hiện tại
        Optional<IdSeq> lastOpt = seqRepo.findTopByOrderByIdDesc();

        int nextSeq = 1;
        if (lastOpt.isPresent()) {
            String lastId = lastOpt.get().getId();
            if (lastId.startsWith(PREFIX)) {
                String numPart = lastId.substring(PREFIX.length());
                try {
                    nextSeq = Integer.parseInt(numPart) + 1;
                } catch (NumberFormatException e) {
                    // bỏ qua, để nextSeq = 1
                }
            }
        }

        // 2. Tạo chuỗi zero-padded
        String padded = String.format("%0" + PAD_SIZE + "d", nextSeq);
        String newId = PREFIX + padded;

        // 3. Lưu vào bảng id_seq để “ghi nhận” ID này
        seqRepo.save(new IdSeq(newId));

        return newId;
    }
}
