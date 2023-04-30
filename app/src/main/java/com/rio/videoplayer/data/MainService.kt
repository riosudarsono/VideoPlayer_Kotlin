package com.rio.videoplayer.data

import com.rio.videoplayer.data.response.BaseResponse
import com.rio.videoplayer.data.response.ResultResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainService {
    @GET("search")
    fun search(@Query("term") term: String,
               @Query("entity") entity: String): Observable<BaseResponse<MutableList<ResultResponse>>>
}