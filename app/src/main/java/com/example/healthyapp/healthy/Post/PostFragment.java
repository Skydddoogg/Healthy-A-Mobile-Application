package com.example.healthyapp.healthy.Post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthyapp.healthy.MenuFragment;
import com.example.healthyapp.healthy.Post.Comment.CommentFragment;
import com.example.healthyapp.healthy.R;
import com.example.healthyapp.healthy.utils.NetworkService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends Fragment{

    private String TAG = "PostFragment";

    private ListView _postList;
    private Button _backBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initPosts();
        initBackButton();
    }

    void initViews(){

        _backBtn = getView().findViewById(R.id.post_back_btn);
        _postList = getView().findViewById(R.id.post_list);

    }

    void initBackButton(){
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .commit();
                Log.d(TAG, "BACK TO MENU");
            }
        });
    }

    void initPosts(){



        // Get all posts
        NetworkService.getInstance()
                .getAllPosts().getAllPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                PostAdapter postAdapter = new PostAdapter(getActivity(), R.layout.fragment_post_item, response.body());
                _postList.setAdapter(postAdapter);
                _postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Post _clickedItem = (Post) parent.getItemAtPosition(position);
                        Bundle bundle = new Bundle();
                        bundle.putString("postId", _clickedItem.getId());

                        CommentFragment commentFragment = new CommentFragment();
                        commentFragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, commentFragment).commit();
                        Log.d(TAG, "GO TO COMMENT FRAGMENT");
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(
                        getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT
                ).show();
                Log.d(TAG, "FAILED TO FETCH POST DATA");
            }
        });


    }
}
