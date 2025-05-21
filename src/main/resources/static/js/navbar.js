document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById('searchInput');
    if (!input) return;

    input.addEventListener('keydown', e => {
        if (e.key === 'Enter') {
            e.preventDefault();
            const q = input.value.trim();
            if (q) {
                // chuyển hướng đúng theo controller
                window.location.href = `/search?q=${encodeURIComponent(q)}`;
            }
        }
    });
});