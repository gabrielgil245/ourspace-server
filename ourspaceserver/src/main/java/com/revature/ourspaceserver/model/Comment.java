package com.revature.ourspaceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="comments")
@AssociationOverrides({
        @AssociationOverride(name="primaryKey.user",
        joinColumns = @JoinColumn(name="user_id")),
        @AssociationOverride(name="primaryKey.post",
        joinColumns = @JoinColumn(name="post_id"))
})
public class Comment {
    //Composite key
    @EmbeddedId
    private CompositeKey primaryKey = new CompositeKey();
    @Transient
    private User user;
    @Transient
    private Post post;
    @Column(name="comment_descr", nullable = false)
    private String commentDescr;
    @CreationTimestamp
    @Column(name="comment_submitted", nullable = false)
    private Timestamp commentSubmitted;

}
