package com.example.healthyapp.healthy.Post.Comment;

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

public class CommentAdapter extends ArrayAdapter{

    private Context context;
    private TextView _postId, _commentId, _body, _name, _email;
    List<Comment> comments = new ArrayList<Comment>();

    public CommentAdapter(@NonNull Context context, int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.comments = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View _commentItem = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);

        _postId = _commentItem.findViewById(R.id.comment_item_post_id);
        _commentId = _commentItem.findViewById(R.id.comment_item_id);
        _body = _commentItem.findViewById(R.id.comment_item_body);
        _name = _commentItem.findViewById(R.id.comment_item_name);
        _email = _commentItem.findViewById(R.id.comment_item_email);

        Comment _row = comments.get(position);

        _postId.setText(_row.getPostId());
        _commentId.setText(_row.getId());
        _body.setText(_row.getBody());
        _name.setText(_row.getName());
        _email.setText("(" + _row.getEmail() + ")");

        return _commentItem;
    }
}
