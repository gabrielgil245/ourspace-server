package com.revature.ourspaceserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Integer postId;
    @CreationTimestamp
    @Column(name="post_submitted", nullable = false)
    private Timestamp postSubmitted;
    @Column(name="post_descr", nullable = false)
    private String postDescription;
    @Column(name="post_image")
    private String postImage;
    @Column(name="post_youtube_url")
    private String postYoutubeUrl;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
}
