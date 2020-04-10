package com.network.app.http;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import timber.log.Timber;

public class RetrofitException extends RuntimeException {

    static RetrofitException httpError(HttpException exception, Retrofit retrofit) {
        try {
            ResponseBody body = exception.response().errorBody();
            if (body != null) {
                Converter<ResponseBody, Error> errorConverter =
                        retrofit.responseBodyConverter(Error.class, new Annotation[0]);
                Error error = errorConverter.convert(body);
                return new RetrofitException(error.message, null, null, Kind.HTTP, exception, null);
            }
        } catch (Exception e) {
            Timber.e(e);
        }

        return unknownError(exception);
    }

    static RetrofitException socketError(SocketTimeoutException exception) {
        String msg = "Socket connection timeout!";
        return new RetrofitException(msg, null, null, Kind.SOCKET, exception, null);
    }

    static RetrofitException networkError(IOException exception) {
        String msg = "Network connection error!";
        return new RetrofitException(msg, null, null, Kind.IO, exception, null);
    }

    static RetrofitException unknownError(Throwable exception) {
        String msg = "Unknown error occurred!";
        return new RetrofitException(msg, null, null, Kind.UNKNOWN, exception, null);
    }

    public enum Kind {
        HTTP,
        SOCKET,
        IO,
        UNKNOWN
    }

    private final String url;
    private final Response response;
    private final Kind kind;
    private final Retrofit retrofit;

    private RetrofitException(String msg, String url, Response response, Kind kind,
                              Throwable exception, Retrofit retrofit) {
        super(msg, exception);
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.retrofit = retrofit;
    }

    public String getUrl() {
        return url;
    }

    public Response getResponse() {
        return response;
    }

    public Kind getKind() {
        return kind;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (response == null || response.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(response.errorBody());
    }

    private class Error {
        int errorCode;
        int statusCode;
        String success;
        String message;
    }
}