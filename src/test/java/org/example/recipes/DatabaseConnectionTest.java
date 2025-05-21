package org.example.recipes;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void dataSourceShouldBeConnected() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            // Connection không được đóng ngay lập tức
            assertFalse(conn.isClosed(), "Kết nối đến database nên đang mở");
            // In tên DB ra console để xác nhận nếu muốn
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());
        }
    }
}
