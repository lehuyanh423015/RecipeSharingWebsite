package org.example.recipes.login;

import java.util.Optional;

import org.example.recipes.exception.EmailExistsException;
import org.example.recipes.exception.UsernameExistsException;
import org.example.recipes.user.UserRepository;
import org.example.recipes.entity.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    public enum LoginResult {
        SUCCESS,
        USER_NOT_FOUND,
        INVALID_PASSWORD
    }

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final IdGeneratorService idGenerator;

    public AuthService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       IdGeneratorService idGenerator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.idGenerator = idGenerator;
    }

    /**
     * Kiểm tra đăng nhập bằng username hoặc email, trả về mã lỗi cụ thể.
     */
    public LoginResult login(String principal, String rawPassword) {
        Optional<Users> userOpt = userRepo.findByUsernameOrEmail(principal, principal);
        if (userOpt.isEmpty()) {
            return LoginResult.USER_NOT_FOUND;
        }
        Users user = userOpt.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            return LoginResult.INVALID_PASSWORD;
        }
        return LoginResult.SUCCESS;
    }

    public Users findUser(String principal, String rawPassword) {
        Optional<Users> userOpt = userRepo.findByUsernameOrEmail(principal, principal);
        return userOpt.get();
    }
    /**
     * Đăng ký user mới. Ném UsernameExistsException hoặc EmailExistsException nếu trùng.
     */
    @Transactional
    public void register(String username, String email, String rawPassword) {
        if (userRepo.existsByUsername(username)) {
            throw new UsernameExistsException();
        }
        if (userRepo.existsByEmail(email)) {
            throw new EmailExistsException();
        }
        String newId = idGenerator.generateId();
        Users u = new Users();
        u.setId(newId);
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setRole("USER");
        userRepo.save(u);
    }
}
