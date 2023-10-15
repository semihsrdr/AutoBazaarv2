package com.semihserdarsahin.autobazaar.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.semihserdarsahin.autobazaar.Adapters.SliderAdapter;
import com.semihserdarsahin.autobazaar.Objects.Car;
import com.semihserdarsahin.autobazaar.databinding.ActivityDetailsBinding;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    Car selectedCar;
    String[] imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        DecimalFormat df = new DecimalFormat("#,###");

        Intent intent=getIntent();
        selectedCar=(Car) intent.getSerializableExtra("selectedCar");

        int km=Integer.parseInt(selectedCar.km);
        String customizedKm=df.format(km);

        int price=Integer.parseInt(selectedCar.price);
        String customizedPrice=df.format(price);

        String telefonNumarasi = selectedCar.phone; // Düzenlenecek telefon numarası
        String customizedPhone = duzenleTelefonNumarasi(telefonNumarasi);

        binding.textViewBrand2.setText(selectedCar.brand);
        binding.textViewfuel2.setText(selectedCar.fuel);
        binding.textViewhp2.setText(selectedCar.enginepower);
        binding.textViewyear2.setText(selectedCar.year);
        binding.textViewmiles2.setText(customizedKm);
        binding.textViewPrice.setText(customizedPrice+"TL");
        binding.textViewPhone2.setText(customizedPhone);
        binding.textViewTitle.setText(selectedCar.title);


        String firstImage=selectedCar.firstLink;
        String secondImage=selectedCar.secondLink;
        String thirdImage=selectedCar.thirdLink;

        imageUrls=new String[3];
        imageUrls[0] = firstImage;
        imageUrls[1] = secondImage;
        imageUrls[2] = thirdImage;


        ViewPager viewPager=binding.imageSlider;
        SliderAdapter adapter=new SliderAdapter(this,imageUrls);
        viewPager.setAdapter(adapter);

    }
    public static String duzenleTelefonNumarasi(String numara) {
        // Numarayı boşluksuz bir şekilde alır ve düzenlenmiş hali oluşturur
        StringBuilder sb = new StringBuilder(numara);
        sb.insert(4, " "); // İlk boşluk, 3. karakterin sonrasına eklenir
        sb.insert(8, " "); // İkinci boşluk, 7. karakterin sonrasına eklenir
        sb.insert(11, " "); // Üçüncü boşluk, 11. karakterin sonrasına eklenir
        return sb.toString();
    }

}