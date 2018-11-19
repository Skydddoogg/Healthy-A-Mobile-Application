package com.example.healthyapp.healthy.Post.Comment;

public class Comment {

    private String postId, id, body, name, email;

    public Comment(String postId, String id, String body, String name, String email){

        this.postId = postId;
        this.id = id;
        this.body = body;
        this.name = name;
        this.email = email;

    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
