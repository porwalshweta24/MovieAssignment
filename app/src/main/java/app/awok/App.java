package app.awok;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.drawee.backends.pipeline.Fresco;

import app.awok.deps.AppComponent;
import app.awok.deps.AppModule;
import app.awok.deps.DaggerAppComponent;
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
