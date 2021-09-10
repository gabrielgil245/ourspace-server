package com.revature.ourspaceserver.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
//In the event we need a composite key for our likes/comments table
/*@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user",
                joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.post",
                joinColumns = @JoinColumn(name = "post_id"))
})*/
public class CompositeKey implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    private Post post;

    //To be inserted in the likes/comments table to achieve (many to many relationship)
    /*@EmbeddedId
    private CompositeKey primaryKey = new CompositeKey();
    @Transient
    private User user;
    @Transient
    private Post post;*/
}
