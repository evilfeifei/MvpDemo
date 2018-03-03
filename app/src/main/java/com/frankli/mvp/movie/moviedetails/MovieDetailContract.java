package com.frankli.mvp.movie.moviedetails;

import com.frankli.mvp.BasePresenter;
import com.frankli.mvp.BaseView;
import com.frankli.mvp.beans.Movie;

import java.util.List;

/**
 * Created by Justy on 2017/11/15.
 */

public interface MovieDetailContract {
    interface View extends BaseView<Presenter>{
        void showCollapsingToolbarTitls(String title);
        void showPicassoImage(String largeImagePath);
        void setMovieInfoToFragment(String movieInfo);
        void setMovieAltToFragment(String movieAlt);
    }
    interface Presenter extends BasePresenter{
        void loadMovieInfo();
        void loadMovieAlt();
    }
}
