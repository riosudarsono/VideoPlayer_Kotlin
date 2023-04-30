package com.rio.videoplayer.data.response

data class BaseResponse<T>(
    val resultCount: Int?,
    val results: T?,
)
