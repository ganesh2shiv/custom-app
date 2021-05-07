package com.network.app.model

data class ErrorResponse(
        var errorCode: Int?,
        var statusCode: Int?,
        var error: String?,
        var success: String?,
        var message: String?
)