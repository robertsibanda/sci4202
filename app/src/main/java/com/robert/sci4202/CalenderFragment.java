package com.robert.sci4202;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;
import com.robert.sci4202.objects.CalenderItem;
import com.robert.sci4202.recyclerviews.CalenderRecyclerviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CalenderFragment extends Fragment {


    public CalenderFragment() {
        // Required empty public constructor
    }

    public static CalenderFragment newInstance(String param1,
                                               String param2) {
        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calender,
                container, false);

        RecyclerView recyclerView =
                view.findViewById(R.id.recviewCarlender);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());

        List<UserData> userData =
                userDatabase.userDataDAO().getAllUserData();

        String userType = userData.get(0).userType;

        CalendarView calendarView = view.findViewById(R.id.datePicker);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int day) {

                Calendar c = Calendar.getInstance();
                c.set(year, month, day);

                JSONObject params = new JSONObject();


                try {
                    params.put("year", year);
                    params.put("day", day);
                    params.put("month", month);
                    params.put("user_type", userType);
                    params.put("authorization",
                            "Bearer " + userDatabase.userDataDAO().getAllUserData().get(0).accessToken);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                RequestQueue requestQueue =
                        Volley.newRequestQueue(view.getContext());
                String url = getString(R.string.endpoint) + "basic" +
                        "/appointments";
                JsonObjectRequest jsonObjectRequest =
                        new JsonObjectRequest(
                                Request.Method.POST,
                                url,
                                params,
                                response -> {
                                    try {
                                        System.out.println("REsponse for" +
                                                " calender : " + response);
                                        JSONArray appointments =
                                                response.getJSONArray(
                                                        "appointments");
                                        ArrayList<CalenderItem> events =
                                                new ArrayList<>();

                                        for (int num = 0; num < appointments.length(); num++) {
                                            JSONObject person =
                                                    appointments.getJSONObject(num);

                                            String day_ =
                                                    person.getString(
                                                            "day");
                                            String month_ =
                                                    person.getString(
                                                            "month");
                                            String year_ =
                                                    person.getString(
                                                            "year");
                                            String date =
                                                    day_ + "/" + month_ + "/" + year_;

                                            String minute =
                                                    person.getString(
                                                            "minute");
                                            String hour =
                                                    person.getString(
                                                            "hour");

                                            String time =
                                                    hour + ":" + minute;

                                            String title =
                                                    person.getString(
                                                            "description");
                                            String other_person;
                                            if (Objects.equals(userType,
                                                    "doctor")) {
                                                other_person =
                                                        person.getString(
                                                                "patient");

                                            } else {
                                                other_person =
                                                        "Dr " + person.getString("doctor");
                                            }
                                            events.add(new CalenderItem(title, date, time, other_person));
                                        }


                                        CalenderRecyclerviewAdapter calenderRecyclerviewAdapter1 =
                                                new CalenderRecyclerviewAdapter();
                                        calenderRecyclerviewAdapter1.setCalenderItems(events);

                                        recyclerView.setAdapter(calenderRecyclerviewAdapter1);
                                    } catch (Exception ex) {
                                        throw new RuntimeException(ex);
                                    }
                                },
                                error -> {
                                    System.out.println("Error : " + error);
                                });


                requestQueue.add(jsonObjectRequest);
            }
        });
        return view;
    }
}