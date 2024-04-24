package com.robert.sci4202;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.MyDoctorItem;
import com.robert.sci4202.objects.Patient;
import com.robert.sci4202.recyclerviews.MyDoctorRecyclerviewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container,
                false);
        EditText etSearchText = view.findViewById(R.id.etSearchText);


        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());
        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        System.out.println("Private Key: " + userData.userID);
        System.out.println("Public Key: " + userData.publicKeyExponent);

        String fullName = userData.fullName;

        if (Objects.equals(userData.userType, "doctor")) {
            etSearchText.setHint("search patient");

            TextView txtFullName = view.findViewById(R.id.txtFullName);
            String txt = "Welcome Dr " + fullName;
            txtFullName.setText(txt);
        } else {
            TextView txtFullName = view.findViewById(R.id.txtFullName);
            String txt = "Welcome " + fullName;
            txtFullName.setText(txt);
        }


        RoundedImageView roundedImageView =
                view.findViewById(R.id.imgUserProfile);

      /*  Glide.with(view)
                .asBitmap()
                .load(getString(R.string.endpoint) + "image/user/" +
                userData.get(0).userName) //TODO fix this in backed code
                .into(roundedImageView);
                */

        RecyclerView recyclerView = view.findViewById(R.id.recviewHome);
        etSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i,
                                          KeyEvent keyEvent) {

                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                String search_string =
                        String.valueOf(etSearchText.getText());
                String search_type = null;
                UserDatabase userDatabase =
                        UserDatabase.getINSTANCE(view.getContext());
                String userType =
                        userDatabase.userDataDAO().getAllUserData().get(0).userType;

                if (Objects.equals(userType, "doctor")) {
                    search_type = "patient";
                } else {
                    search_type = "doctor";
                }

                Map<String, String> params = new HashMap<>();
                try {
                    params.put("search_string", search_string);
                    params.put("user_type", search_type);
                    params.put("signed", "signed");
                    params.put("userid", userData.userID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                String finalSearch_type = search_type;
                MyDoctorRecyclerviewAdapter myDoctorRecyclerviewAdapter
                        = new MyDoctorRecyclerviewAdapter();

                new Thread(() -> {
                    try {
                        ServerResult result = RPCRequests.sendRequest(
                                "search_person",
                                params);
                        System.out.println("Result : " + result.getResult());
                        try {
                            result.getResult().get("success");
                        } catch (Exception ex) {
                            try {
                                Toast.makeText(view.getContext(),
                                        result.getResult().get("error").toString(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(view.getContext(),
                                        e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        System.out.println(result.getResult());
                        JSONArray people =
                                result.getResult().getJSONArray("success");
                        if (finalSearch_type.equals("doctor")) {
                            //TODO create doctor recyclerview
                            ArrayList<MyDoctorItem> myDoctorItems =
                                    new ArrayList<>();
                            for (int num = 0; num < people.length(); num++) {
                                JSONObject person =
                                        people.getJSONObject(num);
                                String fullname = person.getString(
                                        "fullname");
                                String contact = person.getString(
                                        "contact");

                                String organistion = person.getString(
                                        "organisation");
                                boolean canView = person.getBoolean(
                                        "view");
                                boolean canUpdate = person.getBoolean(
                                        "update");

                                String occupation = person.getString(
                                        "occupation");

                                String userid = person.getString("userid");

                                myDoctorItems.add(new MyDoctorItem(fullname, "", contact, occupation, organistion, userid, canView, canUpdate));
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    //Do something on UiThread
                                    myDoctorRecyclerviewAdapter.setMyDoctorItems(myDoctorItems);
                                    recyclerView.setAdapter(myDoctorRecyclerviewAdapter);
                                }
                            });


                        } else {
                            ArrayList<Patient> patients =
                                    new ArrayList<>();
                            for (int num = 0; num < people.length(); num++) {
                                JSONObject patient =
                                        people.getJSONObject(num);
                                String fullname = patient.getString(
                                        "fullname");
                                String contact = patient.getString(
                                        "contact");
                                String userid = patient.getString(
                                        "userid");
                                //patients.add(new Patient())

                            }

                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();

                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        //TODO get user notifications and messages

        return view;
    }
}