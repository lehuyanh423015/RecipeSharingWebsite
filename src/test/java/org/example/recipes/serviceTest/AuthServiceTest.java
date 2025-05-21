package org.example.recipes.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.example.recipes.exception.EmailExistsException;
import org.example.recipes.exception.UsernameExistsException;
import org.example.recipes.login.AuthService;
import org.example.recipes.login.AuthService.LoginResult;
import org.example.recipes.login.IdGeneratorService;
import org.example.recipes.user.UserRepository;
import org.example.recipes.entity.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthServiceTest {

    @Mock private UserRepository userRepo;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private IdGeneratorService idGeneratorService;
    @InjectMocks private AuthService authService;

    private Users user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new Users();
        user.setUsername("alice");
        user.setEmail("alice@example.com");
        user.setPassword("$2a$10$hash…"); // giả lập mật khẩu mã hoá
    }

    @Test
    void loginByUsername_success() {
        when(userRepo.findByUsernameOrEmail("alice","alice"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("raw", user.getPassword()))
                .thenReturn(true);

        LoginResult result = authService.login("alice","raw");
        assertThat(result).isEqualTo(LoginResult.SUCCESS);
    }

    @Test
    void loginByEmail_success() {
        when(userRepo.findByUsernameOrEmail("alice@example.com","alice@example.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("raw", user.getPassword()))
                .thenReturn(true);

        LoginResult result = authService.login("alice@example.com","raw");
        assertThat(result).isEqualTo(LoginResult.SUCCESS);
    }

    @Test
    void login_notFound_returnsUserNotFound() {
        when(userRepo.findByUsernameOrEmail(anyString(), anyString()))
                .thenReturn(Optional.empty());

        LoginResult result = authService.login("unknown","whatever");
        assertThat(result).isEqualTo(LoginResult.USER_NOT_FOUND);
    }

    @Test
    void login_wrongPassword_returnsInvalidPassword() {
        when(userRepo.findByUsernameOrEmail("alice","alice"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("bad", user.getPassword()))
                .thenReturn(false);

        LoginResult result = authService.login("alice","bad");
        assertThat(result).isEqualTo(LoginResult.INVALID_PASSWORD);
    }

    @Test
    void register_newUser_succeeds() {
        when(userRepo.existsByUsername("bob")).thenReturn(false);
        when(userRepo.existsByEmail("b@e.com")).thenReturn(false);

        authService.register("bob","b@e.com","rawPass");
        verify(userRepo).save(ArgumentMatchers.any(Users.class));
    }

    @Test
    void register_duplicateUsername_throwsUsernameExists() {
        when(userRepo.existsByUsername("alice")).thenReturn(true);
        assertThrows(UsernameExistsException.class,
                () -> authService.register("alice","x@y.com","pwd"));
    }

    @Test
    void register_duplicateEmail_throwsEmailExists() {
        when(userRepo.existsByUsername("bob")).thenReturn(false);
        when(userRepo.existsByEmail("e@e.com")).thenReturn(true);
        assertThrows(EmailExistsException.class,
                () -> authService.register("bob","e@e.com","pwd"));
    }
}
