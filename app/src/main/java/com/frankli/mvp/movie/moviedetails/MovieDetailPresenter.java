package com.frankli.mvp.movie.moviedetails;

import android.app.Activity;
import android.content.res.Resources;

import com.frankli.mvp.R;
import com.frankli.mvp.beans.Movie;

/**
 * Created by Justy on 2017/11/15.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

   private static final String TAG = MovieDetailPresenter.class.getSimpleName();
    private Movie movie;
    private MovieDetailContract.View mMovieDetailView;

    public MovieDetailPresenter(Movie movie, MovieDetailContract.View mMovieDetailView) {
        this.movie = movie;
        this.mMovieDetailView = mMovieDetailView;
        this.mMovieDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        showMovieDetail();
    }

    private void showMovieDetail(){
        mMovieDetailView.showCollapsingToolbarTitls(movie.getTitle());
        mMovieDetailView.showPicassoImage(movie.getImages().getLarge());
    }

    @Override
    public void loadMovieInfo() {
        //拼接影片信息, 导演， 主演，又名， 上映时间， 类型， 片长，地区， 语言，IMDB
        StringBuilder movieBuilder = new StringBuilder();
        Resources resources = ((Activity)mMovieDetailView).getResources();

        movieBuilder.append(resources.getString(R.string.movie_directors));
        for (Movie.DirectorsBean director : movie.getDirectors()) {
            movieBuilder.append(director.getName());
            movieBuilder.append(" ");
        }
        movieBuilder.append("\n");

        //主演
        movieBuilder.append(resources.getString(R.string.movie_casts));
        for (Movie.CastsBean cast : movie.getCasts()) {
            movieBuilder.append(cast.getName());
            movieBuilder.append(" ");
        }
        movieBuilder.append("\n");

        //又名
        movieBuilder.append(resources.getString(R.string.movie_aka));
        movieBuilder.append(movie.getOriginal_title());
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_year));
        movieBuilder.append(movie.getYear());
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_genres));
        for (int index = 0; index < movie.getGenres().size(); index++) {
            movieBuilder.append(movie.getGenres().get(index));
            movieBuilder.append(" / ");
        }
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_during));
        movieBuilder.append(resources.getString(R.string.movie_not_find));
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_countries));
        movieBuilder.append(resources.getString(R.string.movie_not_find));
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_languages));
        movieBuilder.append(resources.getString(R.string.movie_not_find));
        movieBuilder.append("\n");

        movieBuilder.append(resources.getString(R.string.movie_imdb));
        movieBuilder.append(resources.getString(R.string.movie_not_find));
        movieBuilder.append("\n");

        mMovieDetailView.setMovieAltToFragment(movieBuilder.toString());
    }

    @Override
    public void loadMovieAlt() {
        mMovieDetailView.setMovieAltToFragment(movie.getAlt());

    }
}
