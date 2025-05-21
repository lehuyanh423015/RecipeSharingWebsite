package org.example.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class RecipeSharingWebsiteApplication
        implements ApplicationListener<ApplicationReadyEvent> {

    public static void main(String[] args) {
        SpringApplication.run(RecipeSharingWebsiteApplication.class, args);
    }

    /**
     * Sau khi ứng dụng đã khởi xong, tự open URL.
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String url = "http://localhost:8080/auth/login";
        System.out.println("=== RecipeSharingWebsiteApplication started successfully ===");
        System.out.println("Opening browser at " + url);
        openBrowser(url);
    }

    private void openBrowser(String url) {
        try {
            // try Desktop API first
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                // fallback cho Windows
                Runtime.getRuntime().exec("cmd /c start " + url);
            }
        } catch (Exception e) {
            System.err.println("Không thể mở trình duyệt tự động: " + e.getMessage());
        }
    }
}
