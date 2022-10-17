package ru.nsu.fevent.dto

data class Response<T>(
    var data: T?,
    var errorStatus: ErrorStatus,
    var errorMessage: String?
) {
    companion object {
        fun <T> withData(data: T?) = Response(data, ErrorStatus.OK, null)
        fun withError(errorMessage: String?) = Response(null, ErrorStatus.ERROR, errorMessage)
    }
}