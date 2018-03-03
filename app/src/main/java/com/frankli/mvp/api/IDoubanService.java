package com.frankli.mvp.api;

import com.frankli.mvp.beans.HotMoviesInfo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Justy on 2017/11/9.
 */

public interface IDoubanService {

    String BASE_URL = "https://api.douban.com/v2/";

    @GET("movie/in_theaters")
    Call<HotMoviesInfo> searchHotMovices();
}
