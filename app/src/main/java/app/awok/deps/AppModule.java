package app.awok.deps;

import android.app.Application;

import javax.inject.Singleton;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shweta 15/06/2017.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

}
