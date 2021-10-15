package com.james.movietmdb.data.utils

sealed class BaseResponse<T>(val data:T?=null,val throwable:Throwable?=null){
    class Success<T>(data:T): BaseResponse<T>(data)
    class Error<T>(throwable: Throwable?,data: T?=null): BaseResponse<T>(data,throwable)
    class Loading<T>: BaseResponse<T>()
    class Loaded<T>: BaseResponse<T>()
}
