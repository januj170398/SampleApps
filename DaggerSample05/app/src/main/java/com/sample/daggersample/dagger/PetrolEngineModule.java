package com.sample.daggersample.dagger;

import com.sample.daggersample.car.Engine;
import com.sample.daggersample.car.PetrolEngine;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PetrolEngineModule {


  /*  @Provides
    PetrolEngine provideEngine(PetrolEngine petrolEngine){
        return petrolEngine;
    }

   */

    @Binds
    abstract Engine bindEngine(PetrolEngine petrolEngine);

}
