package com.example.healthyapp.healthy.utils;

import com.example.healthyapp.healthy.Post.Comment.Comment;
import com.example.healthyapp.healthy.Post.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class NetworkService {

    private Retrofit mRetrofit;
    private static NetworkService mInstance;
    private String BASE_URL = "https://jsonplaceholder.typicode.com";

    public NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public PostSevice getAllPosts(){
        return mRetrofit.create(PostSevice.class);
    }
    public CommentService getComments(){
        return mRetrofit.create(CommentService.class);
    }

    public interface PostSevice {
        @GET("/posts")
        public Call<List<Post>> getAllPosts();
    }

    public interface CommentService {
        @GET("/posts/{id}/comments")
        Call<List<Comment>> getComments(@Path("id") String id);
    }

}
