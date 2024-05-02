package com.robert.sci4202;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.robert.sci4202.comm.RPCRequests;
import com.robert.sci4202.comm.ServerResult;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;


public class ShareRecords extends Fragment {


    public ShareRecords() {
        // Required empty public constructor
    }


    public static ShareRecords newInstance(String param1, String param2) {
        ShareRecords fragment = new ShareRecords();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_records,
                container, false);

        UserDatabase userDatabase =
                UserDatabase.getINSTANCE(view.getContext());

        UserData userData =
                userDatabase.userDataDAO().getAllUserData().get(0);

        EditText etPAsscode = view.findViewById(R.id.etPAsscode);
        String passcode = etPAsscode.getText().toString();

        String userIinfor = userData.userID + "," + userData.fullName +
                "," + userData.contact + "," + passcode;


        ImageView imageCode =
                view.findViewById(R.id.imgQRGenerated);

        Button btnGenerate =
                view.findViewById(R.id.btnGenerateQR);

        btnGenerate.setOnClickListener(l -> {
            MultiFormatWriter multiFormatWriter =
                    new MultiFormatWriter();
            try {

                BitMatrix bitMatrix =
                        multiFormatWriter.encode(userIinfor,
                                BarcodeFormat.QR_CODE,
                                400, 200);
                BarcodeEncoder barcodeEncoder =
                        new BarcodeEncoder();
                Bitmap bitmap =
                        barcodeEncoder.createBitmap(bitMatrix);
                imageCode.setImageBitmap(bitmap);

                new Thread(() -> {
                    // create temporary access of only 1 day

                    Map<String, String> params = new HashMap<>();

                    params.put("patient", userData.userID);
                    params.put("passcode", passcode);

                    try {
                        ServerResult result = RPCRequests.sendRequest(
                                "temporary_permission",
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

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                //Do something on UiThread


                            }
                        });
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }).start();

            } catch (WriterException e) {
                throw new RuntimeException(e);
            }


        });


        return view;
    }
}