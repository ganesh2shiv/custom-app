package com.core.app.http;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends RuntimeException {

    static RetrofitException socketError(SocketTimeoutException exception) {
        String message = "Socket connection timeout!";
        return new RetrofitException(message, null, null, Kind.SOCKET, exception, null);
    }

    static RetrofitException networkError(IOException exception) {
        String message = "Network connection error!";
        return new RetrofitException(message, null, null, Kind.IO, exception, null);
    }

    static RetrofitException unknownError(Throwable exception) {
        String message = "Unknown error occurred!";
        return new RetrofitException(message, null, null, Kind.UNKNOWN, exception, null);
    }

    public enum Kind {
        SOCKET,
        IO,
        UNKNOWN
    }

    private final String url;
    private final Response response;
    private final Kind kind;
    private final Retrofit retrofit;

    private RetrofitException(String message, String url, Response response, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
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
}