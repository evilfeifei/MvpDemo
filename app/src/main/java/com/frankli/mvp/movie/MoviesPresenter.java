package com.frankli.mvp.movie;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.frankli.mvp.api.IDoubanService;
import com.frankli.mvp.beans.HotMoviesInfo;
import com.frankli.mvp.beans.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Justy on 2017/11/15.
 */

public class MoviesPresenter implements MoviesContract.Presenter {
    private final static String TAG = MoviesPresenter.class.getSimpleName();
    private final MoviesContract.View mMoviesView;
    private final IDoubanService mIDoubanService;
    private boolean mFirstLoad = true;

    public MoviesPresenter(@NonNull IDoubanService mIDoubanService, @NonNull MoviesContract.View mMoviesView) {
        this.mIDoubanService = mIDoubanService;
        this.mMoviesView = mMoviesView;
        mMoviesView.setPresenter(this);
    }

    @Override
    public void loadMovies(boolean forceUpdate) {
        loadMovies(forceUpdate||mFirstLoad,true);
         mFirstLoad = false;
    }

    @Override
    public void start() {
        loadMovies(false);
    }

    private void loadMovies(boolean forceUpdate,final boolean showLoadingUI){
        if(showLoadingUI){
            mMoviesView.setLoadingIndicator(true);
        }
        if(forceUpdate){
            mIDoubanService.searchHotMovices().enqueue(new Callback<HotMoviesInfo>() {
                @Override
                public void onResponse(Call<HotMoviesInfo> call, Response<HotMoviesInfo> response) {
                    List<Movie> movieList = response.body().getMovies();
                    if(showLoadingUI){
                        mMoviesView.setLoadingIndicator(false);
                    }
                    processMovies(movieList);
                }

                @Override
                public void onFailure(Call<HotMoviesInfo> call, Throwable t) {

                    if(showLoadingUI){
                        mMoviesView.setLoadingIndicator(false);
                    }
                }
            });
        }
    }

    private void processMovies(List<Movie> movies){
        if(movies.isEmpty()){
            processEmptyTasks();
        }else{
            mMoviesView.showMovies(movies);
        }
    }

    private void processEmptyTasks(){
        mMoviesView.showMovies();
    }


}
