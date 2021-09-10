package com.revature.ourspaceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "likes")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user",
        joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.post",
        joinColumns = @JoinColumn(name = "post_id"))
})
public class Like {
    @EmbeddedId
    private CompositeKey primaryKey = new CompositeKey();
    @Transient
    private User user;
    @Transient
    private Post post;
}
