package org.example.recipes.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "category_name", length = 100)
    private String name;

    @Column(name = "usage_count", nullable = false)
    private long usageCount;

    @Column(name = "category_id", length = 10)
    private String categoryId;

    public Category() {}

    public Category(String name) {
        this.name = name;
        this.usageCount = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(long usageCount) {
        this.usageCount = usageCount;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Increment the usage count when this category is used.
     */
    public void increment() {
        this.usageCount++;
    }
}
