package org.example.recipes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.example.recipes.category.CategoryService;
import org.example.recipes.exception.GlobalExceptionHandler;
import org.example.recipes.controller.RecipeController;
import org.example.recipes.recipe.RecipeService;
import org.example.recipes.entity.Recipes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RecipeController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({ RecipeController.class, GlobalExceptionHandler.class, RecipeControllerTest.TestConfig.class })
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void resetMocks() {
        reset(recipeService, categoryService);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RecipeService recipeService() {
            return mock(RecipeService.class);
        }
        @Bean
        public CategoryService categoryService() {
            return mock(CategoryService.class);
        }
    }

    @Test
    void showCreateForm_shouldDisplayNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/new"))
                .andExpect(model().attributeExists("recipeForm"));
    }

    @Test
    void createRecipe_shouldCallServiceAndRedirectHome() throws Exception {
        // stub return object since create() returns Recipe
        when(recipeService.create(any(Recipes.class))).thenReturn(new Recipes());

        mockMvc.perform(post("/recipes/new")
                        .param("name", "Pancake")
                        .param("description", "Delicious")
                        .param("instruction", "Mix and cook")
                        .param("ingredients", "Flour, Milk")
                        .param("category", "Breakfast")
                        .param("authorId", "user1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(recipeService).create(any(Recipes.class));
    }

    @Test
    void showEditForm_shouldPopulateFormForGivenId() throws Exception {
        Recipes sample = new Recipes();
        sample.setRecipeId("r1");
        sample.setName("Omelette");
        sample.setDescription("Yummy");
        sample.setCategory("Breakfast");
        sample.setAuthorId("user1");
        sample.setInstruction("Beat eggs");
        sample.setIngredients("Eggs, Salt");

        when(recipeService.findById("r1")).thenReturn(Optional.of(sample));

        mockMvc.perform(get("/recipes/r1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/edit"))
                .andExpect(model().attributeExists("recipeForm"))
                .andExpect(model().attribute("id", "r1"));
    }

    @Test
    void updateRecipe_shouldCallServiceAndRedirectToDetail() throws Exception {
        Recipes form = new Recipes();
        form.setName("Omelette");
        form.setDescription("Yummy");
        form.setInstruction("Beat eggs");
        form.setIngredients("Eggs, Salt");
        form.setCategory("Breakfast");
        form.setAuthorId("user1");
        when(recipeService.update(eq("r1"), any(Recipes.class))).thenReturn(new Recipes());

        mockMvc.perform(post("/recipes/r1/edit")
                        .param("name", form.getName())
                        .param("description", form.getDescription())
                        .param("instruction", form.getInstruction())
                        .param("ingredients", form.getIngredients())
                        .param("category", form.getCategory())
                        .param("authorId", form.getAuthorId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recipes/r1"));

        verify(recipeService).update(eq("r1"), any(Recipes.class));
    }

    @Test
    void deleteRecipe_shouldCallServiceAndRedirectHome() throws Exception {
        doNothing().when(recipeService).delete("r1");

        mockMvc.perform(post("/recipes/r1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(recipeService).delete("r1");
    }

    @Test
    void suggestCategories_shouldReturnJsonList() throws Exception {
        when(categoryService.suggest("Br")).thenReturn(List.of("Breakfast", "Brunch"));

        mockMvc.perform(get("/recipes/categories/suggest").param("q", "Br"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"Breakfast\",\"Brunch\"]"));
    }
}
