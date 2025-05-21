package org.example.recipes.controller;

import org.example.recipes.news_feed.RecipeFeedItem;
import org.example.recipes.entity.Recipes;
import org.example.recipes.recipe.RecipeService;
import org.example.recipes.follow.FollowService;
import org.example.recipes.like.LikeService;
import org.example.recipes.comment.CommentService;
import org.example.recipes.save.SaveService;
import org.example.recipes.rate.RateService;
import org.example.recipes.media.MediaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/home/feed")
public class NewsFeedController {
    private final RecipeService   recipeService;
    private final FollowService   followService;
    private final LikeService     likeService;
    private final CommentService  commentService;
    private final SaveService     saveService;
    private final RateService     rateService;
    private final MediaService    mediaService;

    public NewsFeedController(
            RecipeService recipeService,
            FollowService followService,
            LikeService likeService,
            CommentService commentService,
            SaveService saveService,
            RateService rateService,
            MediaService mediaService
    ) {
        this.recipeService  = recipeService;
        this.followService  = followService;
        this.likeService    = likeService;
        this.commentService = commentService;
        this.saveService    = saveService;
        this.rateService    = rateService;
        this.mediaService   = mediaService;
    }

    /**
     * Lấy news-feed:
     * 1) Các bài của người dùng đang theo dõi, sau đó mới đến hot posts.
     * 2) Hỗ trợ infinite scroll: truy vấn theo cursor (before=timestamp).
     *
     * @param userId  ID người hiện tại (có thể lấy từ security context)
     * @param before  timestamp để phân trang (null lần đầu)
     * @param limit   số lượng phần tử/trang
     */
    @GetMapping
    public InfiniteScrollResponse getFeed(
            @RequestParam("userId") String userId,
            @RequestParam(name = "before", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        // 1) Lấy batch công thức
        List<Recipes> batch = (before == null)
                ? recipeService.getInitialRecipes(limit)
                : recipeService.getMoreRecipes(before, limit);

        // 2) Map sang DTO bao gồm tất cả thông tin cần thiết
        List<RecipeFeedItem> items = batch.stream().map(r -> RecipeFeedItem.builder()
                .recipeId(r.getRecipeId())
                .name(r.getName())
                .description(r.getDescription())
                .category(r.getCategory())
                .authorId(r.getAuthorId())
                .authorName(r.getAuthorName())
                // .authorAvatarUrl(mediaService.getAvatarUrl(r.getAuthorId())) // nếu có
                .followingAuthor(followService.isFollowing(userId, r.getAuthorId()))
                .mediaUrls(mediaService.getMediaUrls(r.getRecipeId()))
                .likeCount(likeService.countLikes(r.getRecipeId()))
                .commentCount(commentService.countComments(r.getRecipeId()))
                .saveCount(saveService.countSaves(r.getRecipeId()))
                .rateCount(rateService.countRating(r.getRecipeId()))
                .averageRating(rateService.getAverageRating(r.getRecipeId()))
                .likedByMe(likeService.hasLiked(userId, r.getRecipeId()))
                .savedByMe(saveService.hasSaved(userId, r.getRecipeId()))
                .myRating(rateService.getUserRating(userId, r.getRecipeId()))
                .createdAt(r.getCreatedAt())
                .build()
        ).collect(Collectors.toList());

        boolean hasMore = items.size() == limit;
        LocalDateTime nextBefore = hasMore
                ? items.get(items.size() - 1).getCreatedAt()
                : null;

        return new InfiniteScrollResponse(items, hasMore, nextBefore);
    }

    public static class InfiniteScrollResponse {
        private List<RecipeFeedItem> items;
        private boolean hasMore;
        private LocalDateTime nextBefore;

        public InfiniteScrollResponse(List<RecipeFeedItem> items,
                                      boolean hasMore,
                                      LocalDateTime nextBefore) {
            this.items      = items;
            this.hasMore    = hasMore;
            this.nextBefore = nextBefore;
        }

        public List<RecipeFeedItem> getItems() {
            return items;
        }
        public void setItems(List<RecipeFeedItem> items) {
            this.items = items;
        }

        public boolean isHasMore() {
            return hasMore;
        }
        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }

        public LocalDateTime getNextBefore() {
            return nextBefore;
        }
        public void setNextBefore(LocalDateTime nextBefore) {
            this.nextBefore = nextBefore;
        }
    }
}
