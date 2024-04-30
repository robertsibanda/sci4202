package com.robert.sci4202;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.robert.sci4202.data.UserData;
import com.robert.sci4202.data.UserDatabase;

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

        String userIinfor = userData.userID + "," + userData.fullName +
                "," + userData.contact;
        ImageView imageCode = view.findViewById(R.id.imgQRGenerated);

        Button btnGenerate = view.findViewById(R.id.btnGenerateQR);

        btnGenerate.setOnClickListener(l -> {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix =
                        multiFormatWriter.encode(userIinfor,
                                BarcodeFormat.QR_CODE, 400, 200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageCode.setImageBitmap(bitmap);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }


        });
        return view;
    }
}