package com.sample.daggersample.dagger;

import com.sample.daggersample.car.Rims;
import com.sample.daggersample.car.Tires;
import com.sample.daggersample.car.Wheels;

import dagger.Module;
import dagger.Provides;

@Module
public class WheelsModule {

    @Provides
    Rims provideRims(){
        return new Rims();
    }

    @Provides
    Tires provideTires(){
        Tires tires = new Tires();
        tires.inflate();
        return tires;
    }

    @Provides
    Wheels provideWheels(Rims rims, Tires tires){
        return new Wheels(rims, tires);
    }
}
