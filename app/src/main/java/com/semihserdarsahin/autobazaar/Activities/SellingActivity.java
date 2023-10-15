package com.semihserdarsahin.autobazaar.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.semihserdarsahin.autobazaar.R;
import com.semihserdarsahin.autobazaar.databinding.ActivitySellingBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SellingActivity extends AppCompatActivity {
    private ActivitySellingBinding binding;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;
    String postTitle,price,year,brand,fuel,hp,km;
    ArrayAdapter<String> adapterItems;
    List<String> hpList=new ArrayList<>();
    int imageNumber;

    AutoCompleteTextView autoCompleteTextViewBrands;
    AutoCompleteTextView autoCompleteTextViewYears;
    AutoCompleteTextView autoCompleteTextViewFuel;
    AutoCompleteTextView autoCompleteTextViewHp;

    String[] fuelType={"Gasoline","Diesel","Hybrid","Electric"};
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    FirebaseAuth mAuth;
    StorageReference storageReference;
    ProgressBar progressBar;

    String[] years={"2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015",
            "2014", "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006",
            "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997",
            "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988",
            "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979",
            "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970",
            "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960"};

    String[] brands = {
            "Acura",
            "Alfa Romeo",
            "Aston Martin",
            "Audi",
            "Bentley",
            "BMW",
            "Bugatti",
            "Buick",
            "Cadillac",
            "Chevrolet",
            "Chrysler",
            "Citroën",
            "Dodge",
            "Ferrari",
            "Fiat",
            "Ford",
            "Genesis",
            "GMC",
            "Honda",
            "Hyundai",
            "Infiniti",
            "Jaguar",
            "Jeep",
            "Kia",
            "Lamborghini",
            "Land Rover",
            "Lexus",
            "Lincoln",
            "Maserati",
            "Mazda",
            "McLaren",
            "Mercedes-Benz",
            "Mini",
            "Mitsubishi",
            "Nissan",
            "Pagani",
            "Porsche",
            "Ram",
            "Rolls-Royce",
            "Saab",
            "Subaru",
            "Tesla",
            "Toyota",
            "Volkswagen",
            "Volvo"
    };

    Uri uri;
    Uri firstUri;
    Uri secondUri;
    Uri thirdUri;
    String firstlink;
    String secondlink;
    String thirdlink;
    String phoneNumber;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySellingBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        progressBar=binding.progressBar3;
        progressBar.setVisibility(View.GONE);
        button=binding.button;
        button.setEnabled(true);

        firebaseStorage=FirebaseStorage.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        storageReference=firebaseStorage.getReference();

        imageNumber=0;
        registerLauncher();

        for (int i = 60; i <= 600; i++) {
            hpList.add(String.valueOf(i)+"HP");
        }

        autoCompleteTextViewBrands=binding.getRoot().findViewById(R.id.auto_complete_txt1);
        adapterItems=new ArrayAdapter<>(this,R.layout.list_item,brands);

        autoCompleteTextViewBrands.setAdapter(adapterItems);
        autoCompleteTextViewBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                brand=item;
            }
        });


        autoCompleteTextViewYears=binding.getRoot().findViewById(R.id.auto_complete_txt2);
        adapterItems=new ArrayAdapter<>(this,R.layout.list_item,years);

        autoCompleteTextViewYears.setAdapter(adapterItems);
        autoCompleteTextViewYears.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                year=item;
            }
        });

        autoCompleteTextViewFuel=binding.getRoot().findViewById(R.id.auto_complete_txt3);
        adapterItems=new ArrayAdapter<>(this,R.layout.list_item,fuelType);

        autoCompleteTextViewFuel.setAdapter(adapterItems);
        autoCompleteTextViewFuel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                fuel=item;
            }
        });

        autoCompleteTextViewHp=binding.getRoot().findViewById(R.id.auto_complete_txt4);
        adapterItems=new ArrayAdapter<>(this,R.layout.list_item,hpList);

        autoCompleteTextViewHp.setAdapter(adapterItems);
        autoCompleteTextViewHp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item=adapterView.getItemAtPosition(i).toString();
                hp=item;
            }
        });

    }

    public void share(View view){

        progressBar.setVisibility(View.VISIBLE);
        button.setEnabled(false);
        button.setText("Share");

        postTitle=binding.PostTitle.getText().toString();
        price=binding.carPrice.getText().toString();
        km=binding.miles.getText().toString();
        phoneNumber=binding.phone.getText().toString();

        UUID uuid1=UUID.randomUUID();
        UUID uuid2=UUID.randomUUID();
        UUID uuid3=UUID.randomUUID();

        String firstName="images/"+uuid1+".png";
        String secondName="images/"+uuid2+".png";
        String thirdName="images/"+uuid3+".png";



        if (firstUri!=null||secondUri!=null||thirdUri!=null){
            if(!postTitle.equals("")&&!price.equals("")&&!km.equals("")&&!brand.equals("")&&!year.equals("")){
                Toast.makeText(this, "Clicked! Please Wait and Don't Press Anything!", Toast.LENGTH_SHORT).show();
                storageReference.child(firstName).putFile(firstUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference newReference=firebaseStorage.getReference(firstName);
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                firstlink=uri.toString();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SellingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                storageReference.child(secondName).putFile(secondUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference newReference=firebaseStorage.getReference(secondName);
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                secondlink=uri.toString();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SellingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                storageReference.child(thirdName).putFile(thirdUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        StorageReference newReference=firebaseStorage.getReference(thirdName);
                        newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                thirdlink=uri.toString();

                                HashMap<String,Object> postData=new HashMap<>();
                                postData.put("title",postTitle);
                                postData.put("price",price);
                                postData.put("km",km);
                                postData.put("brand",brand);
                                postData.put("year",year);
                                postData.put("fuel",fuel);
                                postData.put("enginepower",hp);
                                postData.put("phone",phoneNumber);
                                postData.put("firsturl",firstlink);
                                postData.put("secondurl",secondlink);
                                postData.put("thirdurl",thirdlink);
                                postData.put("date", FieldValue.serverTimestamp());

                                firebaseFirestore.collection("Posts/").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent=new Intent(SellingActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SellingActivity.this, "has a problem", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SellingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(this, "Please Fiil The All Blanks", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Please Fiil The All Blanks", Toast.LENGTH_SHORT).show();
        }
    }
    public void firstImage(View view){
        imageNumber=1;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(view,"Need Permission To Upload Pictures From Your Gallery",Snackbar.LENGTH_INDEFINITE).
                            setAction("Give Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //request permission
                                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                                }
                            }).show();
                }
                else{
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                }

            }
            else{
                //gallery
                Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
        else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view,"Need Permission To Upload Pictures From Your Gallery",Snackbar.LENGTH_INDEFINITE).
                            setAction("Give Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //request permission
                                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                }
                            }).show();
                }
                else{
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

            }
            else{
                //gallery
                Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
    }
    public void secondImage(View view){
        imageNumber=2;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(view,"Need Permission To Upload Pictures From Your Gallery",Snackbar.LENGTH_INDEFINITE).
                            setAction("Give Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //request permission
                                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                                }
                            }).show();
                }
                else{
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                }

            }
            else{
                //gallery
                Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
        else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view,"Need Permission To Upload Pictures From Your Gallery",Snackbar.LENGTH_INDEFINITE).
                            setAction("Give Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //request permission
                                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                }
                            }).show();
                }
                else{
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

            }
            else{
                //gallery
                Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
    }
    public void thirdImage(View view){
        imageNumber=3;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(view,"Need Permission To Upload Pictures From Your Gallery",Snackbar.LENGTH_INDEFINITE).
                            setAction("Give Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //request permission
                                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                                }
                            }).show();
                }
                else{
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                }

            }
            else{
                //gallery
                Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
        else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view,"Need Permission To Upload Pictures From Your Gallery",Snackbar.LENGTH_INDEFINITE).
                            setAction("Give Permission", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //request permission
                                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                }
                            }).show();
                }
                else{
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }

            }
            else{
                //gallery
                Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
    }
    private void registerLauncher(){

        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode()==RESULT_OK){
                    Intent intentFromResult=o.getData();
                    if (intentFromResult!=null){
                        uri=intentFromResult.getData();
                        if (imageNumber==1){
                            binding.imageView1.setImageURI(uri);
                            firstUri=uri;
                        }
                        else if(imageNumber==2){
                            binding.imageView2.setImageURI(uri);
                            secondUri=uri;
                        }
                        else if(imageNumber==3){
                            binding.imageView3.setImageURI(uri);
                            thirdUri=uri;
                        }

                    }
                }
            }
        });

        permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                if (o){
                    //permission granted
                    Intent intentToGallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }
                else{
                    Toast.makeText(SellingActivity.this, "Permission Needed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}