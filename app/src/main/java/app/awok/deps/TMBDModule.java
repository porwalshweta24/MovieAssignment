package app.awok.deps;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import app.awok.network.RestClient;

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
