package com.spoton.com.data

import com.spoton.com.data.Resource.Status.EMPTY
import com.spoton.com.data.Resource.Status.ERROR
import com.spoton.com.data.Resource.Status.LOADING
import com.spoton.com.data.Resource.Status.SUCCESS

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        SUCCESS, ERROR, LOADING, EMPTY
    }
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                SUCCESS,
                data,
                null
            )
        }
        fun <T> error(message: String): Resource<T> {
            return Resource(
                ERROR,
                null,
                message
            )
        }
        fun <T> loading(): Resource<T> {
            return Resource(
                LOADING,
                null,
                null
            )
        }
        fun <T> empty(): Resource<T> {
            return Resource(
                EMPTY,
                null,
                null
            )
        }
    }
}