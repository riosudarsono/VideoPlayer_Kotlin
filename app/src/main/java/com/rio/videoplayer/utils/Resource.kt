package com.rio.videoplayer.utils

import com.rio.videoplayer.utils.Status.*

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> =
            Resource(SUCCESS, data, null)

        fun <T> expired(data: T?, msg: String?): Resource<T> =
            Resource(EXPIRED, data, msg)

        fun <T> error(data: T?, msg: String?): Resource<T> =
            Resource(ERROR, data, msg)

        fun <T> loading(data: T?): Resource<T> =
            Resource(LOADING, data, null)
    }
}
