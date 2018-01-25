package app.movies;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import app.movies.deps.AppComponent;
import app.movies.deps.AppModule;
import app.movies.deps.DaggerAppComponent;
import io.fabric.sdk.android.Fabric;

/**
 * Created by shweta 15/06/2017.
 */

public class App extends Application {

    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        appComponent = initDagger(this);
        Fresco.initialize(this);

    }

    protected AppComponent initDagger(App application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }
}
