package org.example.recipes.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller để điều hướng khi người dùng click trên thanh navbar.
 * Mọi link trên navbar đều point tới /nav/{action},
 * và ở đây sẽ redirect tiếp tới URL tương ứng trong ứng dụng.
 */
@Controller
@RequestMapping("/nav")
public class NavBarController {

    /** Click "Home" → quay về trang chủ ("/") */
    @GetMapping("/home")
    public String goHome() {
        return "redirect:/home/feed";
    }

    /** Click "Rank" → chuyển tới trang xếp hạng ("/rank") */
    @GetMapping("/rank")
    public String goRank() {
        return "redirect:/rank";
    }

    /** Click "Add Recipe" → mở form tạo mới công thức ("/recipes/new") */
    @GetMapping("/add")
    public String goAddRecipe() {
        return "redirect:/recipes/new";
    }

    /**
     * Click vào avatar → chuyển tới trang profile của chính user hiện tại.
     * Lấy username từ Principal (hoặc from UserDetails nếu bạn dùng Spring Security).
     */
    @GetMapping("/profile")
    public String goMyProfile(Principal principal) {
        String username = principal.getName();
        return "redirect:/users/" + username;
    }

    /**
     * Click nút tìm kiếm (nếu trên navbar bạn submit form GET tới /nav/search)
     * Ví dụ form: <form action="/nav/search" method="get"><input name="q"/><input name="type"/></form>
     */
    @GetMapping("/search")
    public String doSearch(@RequestParam("q") String query,
                           @RequestParam(value = "type", defaultValue = "recipe") String type) {
        // Redirect về controller /search chuẩn, kèm param type & q
        return "redirect:/search?type=" + type + "&q=" + query;
    }
}