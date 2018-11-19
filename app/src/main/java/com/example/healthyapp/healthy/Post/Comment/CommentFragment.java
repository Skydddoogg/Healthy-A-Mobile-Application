package com.example.healthyapp.healthy.Post.Comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.healthyapp.healthy.Post.PostFragment;
import com.example.healthyapp.healthy.R;
import com.example.healthyapp.healthy.utils.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment{

    private String TAG = "CommentFragment";

    private Button _backBtn;
    private ListView _commentList;
    private String postId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initBackButton();
        initComments();
    }

    void initViews(){

        _backBtn = getView().findViewById(R.id.comment_back_btn);
        _commentList = getView().findViewById(R.id.comment_list);

    }

    void initBackButton(){
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new PostFragment())
                        .commit();
                Log.d(TAG, "BACK TO POST FRAGMENT");
            }
        });
    }

    void initComments(){

        // Get post Id
        Bundle bundle = getArguments();
        if (bundle != null){
            postId = bundle.getString("postId");
            Log.d(TAG, "POST ID = " + postId);
        }

        // Get comments
        NetworkService.getInstance()
                .getComments().getComments(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                CommentAdapter commentAdapter = new CommentAdapter(getActivity(), R.layout.fragment_post_item, response.body());
                _commentList.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(
                        getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT
                ).show();
                Log.d(TAG, "FAILED TO FETCH COMMENT DATA");
            }
        });
    }

}
