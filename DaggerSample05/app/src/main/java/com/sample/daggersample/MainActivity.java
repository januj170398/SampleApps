package com.sample.daggersample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sample.daggersample.car.Car;
import com.sample.daggersample.dagger.CarComponent;
import com.sample.daggersample.dagger.DaggerCarComponent;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    @Inject
    Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarComponent carComponent = DaggerCarComponent.create();

        carComponent.inject(this);

       // car = carComponent.getCar();

        car.drive();
    }

}
