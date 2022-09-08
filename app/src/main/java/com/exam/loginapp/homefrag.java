package com.exam.loginapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ObjectClass.androidVersion;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homefrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homefrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View view = this.getView();
    private TextView nameDisplay, emailDisplay;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homefrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static homefrag newInstance(String param1, String param2) {
        homefrag fragment = new homefrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    RecyclerView rv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    TextView saveName, saveEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayList<String> Name = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        View viewObj = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sp = viewObj.getContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        rv = viewObj .findViewById(R.id.RecycleViewHome);

        db.collection("AndroidVersion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()
                                 ) {
//                                System.out.println("INI ID " + doc.getId());
                                Name.add(doc.getId());
//                                System.out.println("INi size Vector" + Name.size());
                            }

                            for (int i = 0; i < Name.size()-1; i++) {
                                for (int j = i+1; j < Name.size(); j++ ){
                                    double iNum = Double.valueOf(Name.get(i).substring(Name.get(i).indexOf(" ")+1,Name.get(i).length()));
                                    double jNum = Double.valueOf(Name.get(j).substring(Name.get(j).indexOf(" ")+1,Name.get(j).length()));

                                    if (iNum > jNum) {
                                        String temp = Name.get(i);
                                        Name.set(i, Name.get(j));
                                        Name.set(j, temp);
                                    }
                                }
                            }


                            for (String i: Name
                                 ) {
                                Log.d(i, "Vector sorted ");
                            }
                            rv.setAdapter(new RecycleAdapter(Name));
                            rv.setLayoutManager(new LinearLayoutManager(viewObj.getContext()));
                        }
                    }
                });




//        saveName = viewObj.findViewById(R.id.nameViewHome);
//        saveEmail = viewObj.findViewById(R.id.emaillViewHome);
//
//
//
//        saveEmail.setText(sp.getString("email", "No email"));
//        saveName.setText(sp.getString("name", "No Name"));

//        Button b = viewObj.findViewById(R.id.button_testFragmentTrans);

//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment testFragment = new testFragment();
//                Bundle instanceState = new Bundle();
//                instanceState.putString("passedThrough", "Sukses");
//                testFragment.setArguments(instanceState);
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.HomeContainerId, testFragment).commit();
//                b.setVisibility(View.GONE);
//                rv.setVisibility(View.GONE);
//            }
//        });

//        Name.add("elvin");
//        Name.add("Jessy");
//        Name.add("Elvis");
//        Name.add("Budi");
//        db.waitForPendingWrites()

//        for (int i = 0; i <= 30; i++) {
//            if (!Name.isEmpty()) {
//                break;
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }

        return viewObj;

    }
}