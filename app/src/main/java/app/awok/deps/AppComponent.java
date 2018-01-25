package app.awok.deps;

import javax.inject.Singleton;

import app.awok.ui.presenter.TopRatedPresenter;
import dagger.Component;
import app.awok.ui.presenter.PopularPresenter;

/**
 * Created by shweta on 15/06/2017.
 */

@Singleton
@Component(modules = {AppModule.class, TMBDModule.class})
public interface AppComponent {

    void inject(PopularPresenter target);
    void inject(TopRatedPresenter target);

}