// ======= STAR RATING =======
const stars = document.querySelectorAll('.star');
const result = document.getElementById('rating-result');
let selectedRating = 0;

stars.forEach(star => {
    star.addEventListener('mouseenter', () => {
        const value = +star.dataset.value;
        highlightStars(value);
    });
    star.addEventListener('mouseleave', () => {
        highlightStars(selectedRating);
    });
    star.addEventListener('click', () => {
        selectedRating = +star.dataset.value;
        result && (result.textContent = selectedRating);
        highlightStars(selectedRating);
    });
});

function highlightStars(count) {
    stars.forEach(star => {
        const val = +star.dataset.value;
        star.classList.toggle('hover', val <= count);
        star.classList.toggle('selected', val <= count);
    });
}

// ======= IMAGE CAROUSEL =======
const imageList = ["images/dish.jpg", "images/dish2.png", "images/dish3.png"];
let currentImage = 0;
const dishImage = document.getElementById("dishImage");
const leftBtn = document.querySelector(".left-buttons");
const rightBtn = document.querySelector(".right-buttons");

if (leftBtn && rightBtn && dishImage) {
    leftBtn.onclick = () => {
        currentImage = (currentImage - 1 + imageList.length) % imageList.length;
        dishImage.src = imageList[currentImage];
    };

    rightBtn.onclick = () => {
        currentImage = (currentImage + 1) % imageList.length;
        dishImage.src = imageList[currentImage];
    };
}

// ======= SAVE BUTTON TOGGLE =======
const savePost = document.getElementById("savePost");
let isSaved = false;

if (savePost) {
    savePost.onclick = () => {
        isSaved = !isSaved;
        if (isSaved) {
            savePost.classList.add("selected");
            savePost.style.backgroundImage = 'url("images/mark-save.png")';
        } else {
            savePost.classList.remove("selected");
            savePost.style.backgroundImage = 'url("images/bookmark.png")';
        }
    };
}