package org.example.recipes;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

import org.example.recipes.exception.GlobalExceptionHandler;
import org.example.recipes.exception.UsernameExistsException;
import org.example.recipes.login.AuthService;
import org.example.recipes.login.Login;
import org.example.recipes.login.Register;
import org.example.recipes.controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({ LoginController.class,
        GlobalExceptionHandler.class,
        LoginControllerTest.TestConfig.class })
public class LoginControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private AuthService authService;

    @BeforeEach
    void resetMock() {
        reset(authService);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean public AuthService authService() {
            return mock(AuthService.class);
        }
    }

    @Test
    void loginPageContainsLinkToRegister() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("href=\"/auth/register\"")));
    }

    @Test
    void showLoginPage_shouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/login"))
                .andExpect(model().attributeExists("loginForm"));
    }

    @Test
    void registerPageContainsLinkToLogin() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("href=\"/auth/login\"")));
    }

    @Test
    void showRegisterPage_shouldReturnRegisterView() throws Exception {
        mockMvc.perform(get("/auth/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("registerForm"));
    }

    @Test
    void whenValidRegistration_thenRedirectToLoginWithFlag() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .param("username","newuser")
                        .param("email","n@e.com")
                        .param("password","pwd123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/auth/login?registered"));
    }

    @Test
    void whenUsernameExists_thenShowRegisterWithError() throws Exception {
        doThrow(new UsernameExistsException("Username đã tồn tại."))
                .when(authService)
                .register(eq("dup"), eq("d@up.com"), eq("pwd"));

        mockMvc.perform(post("/auth/register")
                        .param("username","dup")
                        .param("email","d@up.com")
                        .param("password","pwd"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/register"))
                .andExpect(model().attributeExists("registerError"))
                .andExpect(content().string(
                        containsString("Username đã tồn tại")));
    }
}
