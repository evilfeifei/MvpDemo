package com.frankli.mvp.movie;

import com.frankli.mvp.BasePresenter;
import com.frankli.mvp.BaseView;
import com.frankli.mvp.beans.Movie;

import java.util.List;

/**
 * Created by Justy on 2017/11/15.
 */

public interface MoviesContract {
    interface View extends BaseView<Presenter>{
        void showMovies(List<Movie> movies);
        void showMovies();
        void setLoadingIndicator(boolean active);
    }
    interface Presenter extends BasePresenter{
        void loadMovies(boolean forceUpdate);
    }
}
