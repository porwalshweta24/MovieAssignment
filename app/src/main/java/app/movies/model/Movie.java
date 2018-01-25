package app.movies.model;

import com.google.gson.annotations.SerializedName;
import app.movies.utils.Constants;
import app.movies.utils.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Represents a movie from The Movie Database.
 *
 * Created by Mauricio.n 23/01/2018.
 */

public class Movie {

    private final String title;
    private final String overview;
    @SerializedName("release_date")
    private final String releaseDate;
    @SerializedName("poster_path")
    private final String postPath;

    public Movie(final String title,
                 final String overview,
                 final String releaseDate,
                 final String postPath) {
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.postPath = postPath;
    }

    public String getTitle() {
        return StringUtils.processHtmlString(this.title);
    }

    public String getOverview() {
        return StringUtils.processHtmlString(this.overview);
    }

    /**
     * Formats the movie release date from the TMDB API and returns its year as an int value.
     * If the date is not available, returns the current year.
     *
     * @return int with the release year of the movie.
     */
    public int getYear() {
        final SimpleDateFormat sdf = new SimpleDateFormat(
                Constants.DATE_FORMAT,
                Locale.getDefault());
        final Calendar calendar = new GregorianCalendar();
        try {
            final Date date = sdf.parse(this.releaseDate);
            calendar.setTime(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Generates the movie poster URL by adding the root URL to the movie poster path.
     * If there is not any movie poster path available, returns an empty String.
     *
     * @return String with the movie poster URL.
     */
    public String getPosterURL() {
        if (postPath == null) {
            return "";
        }
        return String.format("%s%s", Constants.POSTER_URL, postPath);
    }
}
