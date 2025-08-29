package org.example.miniproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookmarks")
@Builder
@AttributeOverride(name = "id", column = @Column(name = "bookmark_id"))
public class BookMark extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "article_id",unique = true)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;
}
