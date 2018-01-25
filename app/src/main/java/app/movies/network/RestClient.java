package app.movies.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import app.movies.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Client of the TMDB API, groups all the methods related to the interaction with it.
 */

public class RestClient {

    private static TMDBService mTMBDService;

    public static TMDBService getTMBDService() {
        if (mTMBDService == null) {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mTMBDService = retrofit.create(TMDBService.class);
        }
        return mTMBDService;
    }

    public interface TMDBService {

        /**
         * Returns the most popular movies.
         *
         * @param apiKey String with the key of the TMDB API.
         * @param page int with the page number that the user want to see.
         * @return MovieResponse call with the list of the most popular at the desired page.
         */
        @Headers("Content-Type: application/json;charset=utf-8")
        @GET("movie/popular")
        Observable<MovieResponse> moviesByPopularity(@Query("api_key") String apiKey,
                                                     @Query("page") int page);

        /**
         * Returns the movies that more closely match to the query.
         *
         * @param apiKey String with the key of the TMDB API.
         * @param query String with the text that is necessary in the title at this search.
         * @param page int with the page number that the user want to see.
         * @return MovieResponse call with the list of the movies that more closely match to the
         * query at the desired page.
         */
        @Headers("Content-Type: application/json;charset=utf-8")
        @GET("search/movie")
        Observable<MovieResponse> moviesByQuery(@Query("api_key") String apiKey,
                                                @Query("query") String query,
                                                @Query("page") int page);


        @Headers("Content-Type: application/json;charset=utf-8")
        @GET("movie/top_rated")
        Observable<MovieResponse> moviesByTopRated(@Query("api_key") String apiKey,
                                                     @Query("page") int page);


        // fetch_string = "http://api.themoviedb.org/3/movie/" + arg + "?&append_to_response=trailers,reviews"

    }
}
