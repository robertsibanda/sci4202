package com.robert.sci4202;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.makeramen.roundedimageview.RoundedImageView;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.MyDoctorItem;
import com.robert.sci4202.objects.Patient;
import com.robert.sci4202.recyclerviews.MyDoctorRecyclerviewAdapter;
import com.robert.sci4202.recyclerviews.PatientRecyclerviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

        System.out.println("Private Key: " + userData.publicKeyModulus);
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

                JSONObject params = new JSONObject();
                try {
                    params.put("search_string", search_string);
                    params.put("user_type", search_type);
                    params.put("authorization",
                            "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                RequestQueue requestQueue =
                        Volley.newRequestQueue(view.getContext());
                String url = getString(R.string.endpoint) + "basic/search";
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                params,
                                response -> {
                                    if (userDatabase.userDataDAO().getAllUserData().get(0).userType.equals("patient")) {
                                        try {
                                            JSONArray people =
                                                    response.getJSONArray(
                                                            "people");
                                            ArrayList<MyDoctorItem> myDoctorItems = new ArrayList<>();

                                            for (int num = 0; num < people.length(); num++) {
                                                JSONObject person =
                                                        people.getJSONObject(num);
                                                String name =
                                                        person.getString(
                                                                "name");
                                                String hospital =
                                                        person.getString(
                                                                "hospital");
                                                String work =
                                                        person.getString(
                                                                "work");
                                                String username =
                                                        person.getString(
                                                                "username");
                                                String approver =
                                                        person.getString(
                                                                "approver");
                                                boolean approved =
                                                        person.getBoolean(
                                                                "approved");
                                                boolean requested =
                                                        person.getBoolean(
                                                                "requested");

                                                myDoctorItems.add(new MyDoctorItem
                                                        (name, "", "",
                                                                work,
                                                                hospital,
                                                                username,
                                                                approver,
                                                                requested,
                                                                approved));
                                            }


                                            MyDoctorRecyclerviewAdapter myDoctorRecyclerviewAdapter =
                                                    new MyDoctorRecyclerviewAdapter();

                                            myDoctorRecyclerviewAdapter.url =
                                                    getString(R.string.endpoint);
                                            myDoctorRecyclerviewAdapter.setMyDoctorItems(myDoctorItems);
                                            recyclerView.setAdapter(myDoctorRecyclerviewAdapter);
                                            System.out.println("People " +
                                                    "from " +
                                                    "server  : " + people);
                                        } catch (JSONException e) {
                                            System.out.println("Error at" +
                                                    " search " +
                                                    ": " + e.getMessage());
                                            try {
                                                System.out.println(
                                                        "Error from " +
                                                                "server " +
                                                                ": " + response.get("error"));
                                            } catch (JSONException ex) {
                                                System.out.println(
                                                        "Error at " +
                                                                "search " +
                                                                "2: " + ex.getMessage());
                                            }
                                        }
                                    } else {
                                        try {
                                            System.out.println("Response" +
                                                    " from " +
                                                    "server " + response);
                                            JSONArray people =
                                                    response.getJSONArray(
                                                            "people");
                                            ArrayList<Patient> patients =
                                                    new ArrayList<>();

                                            for (int num = 0; num < people.length(); num++) {
                                                JSONObject person =
                                                        people.getJSONObject(num);
                                                String name =
                                                        person.getString(
                                                                "name");
                                                String contact = "contact";
                                                String username =
                                                        person.getString(
                                                                "username");
                                                String approver =
                                                        person.getString(
                                                                "approver");
                                                boolean approved =
                                                        person.getBoolean(
                                                                "approved");
                                                boolean requested =
                                                        person.getBoolean(
                                                                "requested");

                                                patients.add(new Patient("",
                                                        contact,
                                                        username, name,
                                                        approver,
                                                        requested,
                                                        approved));
                                            }


                                            PatientRecyclerviewAdapter patientRecyclerviewAdapter =
                                                    new PatientRecyclerviewAdapter(view.getContext(), getParentFragmentManager());

                                            patientRecyclerviewAdapter.url =
                                                    getString(R.string.endpoint);
                                            patientRecyclerviewAdapter.setPatients(patients);
                                            recyclerView.setAdapter(patientRecyclerviewAdapter);
                                            System.out.println("People " +
                                                    "from " +
                                                    "server  : " + people);
                                        } catch (JSONException e) {
                                            System.out.println("Error at" +
                                                    " search " +
                                                    ": " + e.getMessage());
                                            try {
                                                System.out.println(
                                                        "Error from " +
                                                                "server " +
                                                                ": " + response.get("error"));
                                            } catch (JSONException ex) {
                                                System.out.println(
                                                        "Error at " +
                                                                "search " +
                                                                "2: " + ex.getMessage());
                                            }
                                        }
                                    }


                                },
                                error -> {
                                    System.out.println("Error : " + error.getMessage());
                                });


                requestQueue.add(jsonObjectRequest);
                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.HORIZONTAL, false));

        RequestQueue requestQueue =
                Volley.newRequestQueue(view.getContext());

        JSONObject notifiParams = new JSONObject();
        try {
            notifiParams.put("authorization",
                    "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return view;
    }
}