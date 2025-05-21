document.addEventListener('DOMContentLoaded', () => {
    // Chuyển hướng khi click vào small user
    document.querySelectorAll('.small-user').forEach(el => {
        el.style.cursor = 'pointer';
        el.addEventListener('click', () => {
            const username = el.dataset.username;
            if (username) {
                window.location.href = `/profile/${encodeURIComponent(username)}`;
            }
        });
    });

    // Chuyển hướng khi click vào small recipe
    document.querySelectorAll('.small-recipe').forEach(el => {
        el.style.cursor = 'pointer';

        el.addEventListener('click', () => {
            const recipeId = el.dataset.recipeId;

            console.log('Recipe Info:', {
                recipeId
            });

            // Redirect nếu có ID
            if (recipeId) {
                window.location.href = `http://localhost:8080/recipeDetails/${recipeId}`;

            }
        });
    });
});