package com.sample.daggersample.dagger;

import com.sample.daggersample.MainActivity;
import com.sample.daggersample.car.Car;

import dagger.Component;

@Component (modules = { WheelsModule.class, DieselEngineModule.class})
public interface CarComponent {

    Car getCar();

    void inject(MainActivity mainActivity);
}
