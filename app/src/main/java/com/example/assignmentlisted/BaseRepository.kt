package com.example.assignmentlisted

import com.example.assignmentlisted.util.ApiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.io.IOException
//
//class BaseRepository {
//    suspend fun <T> safeApiCall(
//        dispatcher: CoroutineDispatcher = Dispatchers.IO,
//        apiCall:suspend ()-> Response<T>
//    ): Flow<ApiState<T>> = flow {
//        emit(ApiState.Loading)
//        val response = apiCall()
//        if (response.isSuccessful){
//            val data = response.body()
//            if(data != null){
//                emit(ApiState.Success(data))
//            }else{
//                val error = response.errorBody()
//                if(error != null){
//                    emit(ApiState.Failure(IOException(error.toString())))
//                }else{
//                    emit(ApiState.Failure(IOException("something went wrong")))
//                }
//            }
//        }else{
//            emit(ApiState.Failure(Throwable(response.errorBody().toString()) as IOException))
//        }
//    }.catch { e->
//        e.printStackTrace()
//        emit(ApiState.Failure(Exception(e) as IOException))
//    }.flowOn(dispatcher)
//
//}