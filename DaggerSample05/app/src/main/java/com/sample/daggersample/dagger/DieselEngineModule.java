package com.sample.daggersample.dagger;

import com.sample.daggersample.car.DieselEngine;
import com.sample.daggersample.car.Engine;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class DieselEngineModule {


  /*  @Provides
    PetrolEngine provideEngine(PetrolEngine petrolEngine){
        return petrolEngine;
    }

   */

    @Binds
    abstract Engine bindEngine(DieselEngine dieselEngine);

}
