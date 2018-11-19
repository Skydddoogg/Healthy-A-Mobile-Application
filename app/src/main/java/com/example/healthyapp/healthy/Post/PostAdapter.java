package com.example.healthyapp.healthy.Post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.healthyapp.healthy.R;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends ArrayAdapter{

    private TextView _id, _title, _body;
    private Context context;
    List<Post> posts = new ArrayList<Post>();

    public PostAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        this.context = context;
        this.posts = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View _postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);
        _id = _postItem.findViewById(R.id.post_item_id);
        _title = _postItem.findViewById(R.id.post_item_title);
        _body = _postItem.findViewById(R.id.post_item_body);

        Post _row = posts.get(position);

        _id.setText(_row.getId());
        _title.setText(_row.getTitle());
        _body.setText(_row.getBody());

        return _postItem;

    }
}
