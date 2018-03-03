package com.frankli.mvp.movie;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.frankli.mvp.R;
import com.frankli.mvp.api.DoubanManager;
import com.frankli.mvp.api.IDoubanService;
import com.frankli.mvp.beans.HotMoviesInfo;
import com.frankli.mvp.beans.Movie;
import com.frankli.mvp.movie.moviedetails.MovieDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements MoviesContract.View{

    private static final String TAG = MoviesFragment.class.getSimpleName();
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private MoivesAdapter moivesAdapter;
    private MoviesContract.Presenter mPresenter;


    public MoviesFragment() {
    }
    public static MoviesFragment newInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(mPresenter!=null){
            mPresenter.start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_hot_movies);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mRecyclerView!=null){
            mRecyclerView.setHasFixedSize(true);
            final GridLayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
            mRecyclerView.setLayoutManager(layoutManager);
            moivesAdapter = new MoivesAdapter(movieList,getContext(),R.layout.item_movies);
            mRecyclerView.setAdapter(moivesAdapter);
        }
    }

    private void loadMovies(Callback<HotMoviesInfo> callback){
        IDoubanService movieService = DoubanManager.createDoubanService();
        movieService.searchHotMovices().enqueue(callback);
    }

    @Override
    public void setPresenter(MoviesContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showMovies(List<Movie> movies) {
        moivesAdapter.replaceData(movies);
    }

    @Override
    public void showMovies() {

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(getView()==null)return;
        final ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.pgb_loading);
        if(active){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    static class MoivesAdapter extends RecyclerView.Adapter<MoviesViewHolder>{

        private List<Movie> movies;
        private Context context;

        @LayoutRes
        private int layoutResId;

        public MoivesAdapter(List<Movie> movies, Context context, int layoutResId) {
            setList(movies);
            this.context = context;
            this.layoutResId = layoutResId;
        }

//        public void setData(List<Movie> movies){
//            this.movies = movies;
//            notifyDataSetChanged();
//        }

        private void setList(List<Movie> movies){
            this.movies = movies;
        }
        public void replaceData(List<Movie> movies){
            setList(movies);
            notifyDataSetChanged();
        }

        @Override
        public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(layoutResId,parent,false);
            return new MoviesViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MoviesViewHolder holder, int position) {
            if (holder==null) return;
            holder.updateMovie(movies.get(position));
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }


    static class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mMovieImage;
        TextView mMoiveTitle;
        TextView mMovieAverage;
        RatingBar ratingBar;
        Movie movie;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            mMovieImage = (ImageView) itemView.findViewById(R.id.movie_cover);
            mMoiveTitle = (TextView) itemView.findViewById(R.id.movie_title);
            mMovieAverage = (TextView) itemView.findViewById(R.id.movie_average);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating_star);
            itemView.setOnClickListener(this);
        }

        public void updateMovie(Movie movie){
            if(movie==null)return;
            this.movie = movie;

            Context context = itemView.getContext();
            Picasso.with(context)
                    .load(movie.getImages().getLarge())
                    .placeholder(context.getResources().getDrawable(R.mipmap.ic_launcher))
                    .into(mMovieImage);
            mMoiveTitle.setText(movie.getTitle());
            final double average = movie.getRating().getAverage();
            if(average == 0.0){
                ratingBar.setVisibility(View.GONE);
                mMovieAverage.setText("暂无评分");
            }else{
                ratingBar.setVisibility(View.VISIBLE);
                mMovieAverage.setText(String.valueOf(average));
                ratingBar.setStepSize(0.5f);
                ratingBar.setRating((float)(movie.getRating().getAverage()/2));
            }
        }

        @Override
        public void onClick(View v) {
            if(movie==null)return;
            if(itemView==null)return;
            Context context = itemView.getContext();
            if(context==null)return;
            Intent intent = new Intent(context,MovieDetailsActivity.class);

            if(context instanceof Activity){
                Activity activity = (Activity) context;
                 Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,mMovieImage,"cover").toBundle();
                ActivityCompat.startActivity(activity,intent,bundle);
            }

        }
    }





}
