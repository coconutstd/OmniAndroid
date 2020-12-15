package com.example.omniandroid.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.omniandroid.DatabaseAccess;
import com.example.omniandroid.R;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

public class MonitoringFragment extends Fragment {

    DatabaseAccess db= new DatabaseAccess(getContext());
    Document document;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_monitoring, container, false);

        // document 안에 retrievedDoc을 통해서 얻은 데이터 값이 들어감
        document = (Document) db.retrievedDoc;

        TextView temp = (TextView)view.findViewById(R.id.temp);
        temp.setText(document.getTextContent().toString());

        return view;
    }

}