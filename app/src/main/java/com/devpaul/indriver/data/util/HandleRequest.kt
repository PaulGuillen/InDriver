package com.devpaul.indriver.data.util

import com.devpaul.indriver.domain.util.ErrorHelper
import com.devpaul.indriver.domain.util.Resource
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

object HandleRequest {

    fun <T> send(result: Response<T>, tag: String = "HandleRequest"): Resource<T> {
        return try {
            if (result.isSuccessful) {
                result.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error(500, "Error: $tag ${result.message()}")
            } else {
                val errorResponse = ErrorHelper.handleError(result.errorBody())
                errorResponse?.let {
                    Resource.Error(errorResponse.statusCode, errorResponse.message)
                } ?: Resource.Error(result.code(), "Error: $tag ${result.message()}")
            }
        } catch (e: HttpException) {
            Timber.d("Error HttpException : $tag ${e.message}")
            Resource.Error(500, "Error: ${e.message}")
        } catch (e: IOException) {
            Timber.d("Error IOException : $tag ${e.message}")
            Resource.Error(500, "Verify your internet connection")
        } catch (e: Exception) {
            Timber.d("Error Exception : $tag ${e.message}")
            Resource.Error(500, "Error: ${e.message}")
        }
    }
}