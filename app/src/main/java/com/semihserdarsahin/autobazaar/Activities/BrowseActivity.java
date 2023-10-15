package com.semihserdarsahin.autobazaar.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.semihserdarsahin.autobazaar.Adapters.Adapter;
import com.semihserdarsahin.autobazaar.Objects.Car;
import com.semihserdarsahin.autobazaar.databinding.ActivityBrowseBinding;

import java.util.ArrayList;
import java.util.Map;

public class BrowseActivity extends AppCompatActivity {
    private ActivityBrowseBinding binding;
    Adapter adapter;
    FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    ArrayList<Car> carArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBrowseBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        carArrayList=new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new Adapter(carArrayList);
        binding.recyclerView.setAdapter(adapter);



    }
    private void getData(){
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value,FirebaseFirestoreException error) {
                if (error!=null){
                    Toast.makeText(BrowseActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value!=null){
                    for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                        Map<String,Object> data=documentSnapshot.getData();

                        String title=(String) data.get("title");
                        String km=(String) data.get("km");
                        String price=(String) data.get("price");
                        String brand=(String) data.get("brand");
                        String fuel=(String) data.get("fuel");
                        String phone=(String) data.get("phone");
                        String year=(String) data.get("year");
                        String enginepower=(String) data.get("enginepower");
                        String firstLink=(String) data.get("firsturl");
                        String secondLink=(String) data.get("secondurl");
                        String thirdLink=(String) data.get("thirdurl");

                        Car car=new Car(title,km,price,brand,fuel,phone,year,enginepower,firstLink,secondLink,thirdLink);
                        carArrayList.add(car);


                    }

                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

}