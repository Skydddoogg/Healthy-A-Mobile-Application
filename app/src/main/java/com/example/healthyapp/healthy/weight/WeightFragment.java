package com.example.healthyapp.healthy.weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.healthyapp.healthy.MenuFragment;
import com.example.healthyapp.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class WeightFragment extends Fragment{

    private ArrayList<Weight> weights = new ArrayList<>();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAddButton();
        initBackButton();

        String _uid = mAuth.getCurrentUser().getUid();

        ListView _weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter _weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_weight_history_item,
                weights
        );
        _weightList.setAdapter(_weightAdapter);
        weights.clear();
        mDb.collection("myfitness").document(_uid)
                .collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    weights.add(doc.toObject(Weight.class));
                    _weightAdapter.notifyDataSetChanged();
                }

                Collections.reverse(weights);
                _weightAdapter.notifyDataSetChanged();

            }
        });
    }

    void initBackButton(){
        Button _backBtn = getView().findViewById(R.id.weight_history_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .commit();
            }
        });
    }

    void initAddButton(){
        Button _addBtn = getView().findViewById(R.id.weight_history_add_btn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .commit();
            }
        });
    }
}
