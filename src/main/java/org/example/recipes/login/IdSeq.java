// src/main/java/org/example/recipes/login/IdSeq.java
package org.example.recipes.login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "id_seq")
public class IdSeq {
    @Id
    @Column(name = "id", length = 10, nullable = false, unique = true)
    private String id;

    public IdSeq() {}

    public IdSeq(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
