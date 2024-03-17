package io.bloco.core.commons.custom.error

sealed class CustomError{
    object NoNetworkConnection : CustomError()
    object AnotherSpecificError : CustomError()
    data class GeneralError(val message: String?) : CustomError()
}

sealed class CustomException(
    message: String? = null
):Exception(message){
    object NoNetworkConnectionException : CustomException()
    object AnotherSpecificException : CustomException()

    fun mapToCustomError(): CustomError{
        return when(this){
            is NoNetworkConnectionException -> {
                CustomError.NoNetworkConnection
            }
            is AnotherSpecificException -> {
                CustomError.AnotherSpecificError
            }
            else -> CustomError.GeneralError(this.message)
        }
    }
}

data class MilkyMoException(override var message: String, val statusCode: Long) : Exception(message)
