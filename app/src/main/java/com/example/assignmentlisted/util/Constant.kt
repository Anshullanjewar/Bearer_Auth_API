package com.example.assignmentlisted.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

fun <T> toResultFlow(call: suspend ()-> Response<T>) : Flow<ApiState<T>> = flow{

    emit(ApiState.Loading)

    val response = call()

try {
    if(response.isSuccessful){
        response.body()?.let { data ->
            emit(ApiState.Success(data))
        }
    }
    else{
        response.errorBody()?.let { error ->
            error.close()
            emit(ApiState.Failure(error.toString()))
        }
    }
}catch (e:Exception){
    emit(ApiState.Failure(e.message.toString()))
}
}