package app.movies.deps;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import app.movies.network.RestClient;

/**
 * Created by shweta 15/06/2017.
 */

@Module
class TMBDModule {

    @Provides
    @Singleton
    RestClient.TMDBService provideTMBDService() {
        return RestClient.getTMBDService();
    }
}
