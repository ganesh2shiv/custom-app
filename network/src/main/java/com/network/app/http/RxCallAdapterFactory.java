package com.network.app.http;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import timber.log.Timber;

public class RxCallAdapterFactory extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory rxAdapter;

    private RxCallAdapterFactory() {
        rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    public static CallAdapter.Factory create() {
        return new RxCallAdapterFactory();
    }

    @Override
    public CallAdapter get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, rxAdapter.get(returnType, annotations, retrofit));
    }

    private static class RxCallAdapterWrapper implements CallAdapter {

        private final Retrofit retrofit;
        private final CallAdapter wrapped;

        RxCallAdapterWrapper(Retrofit retrofit, CallAdapter wrapped) {
            this.retrofit = retrofit;
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object adapt(Call call) {
            Object adaptedCall = wrapped.adapt(call);

            if (adaptedCall instanceof Completable) {
                return ((Completable) adaptedCall).onErrorResumeNext(throwable
                        -> Completable.error(asRetrofitException(throwable)));
            }

            if (adaptedCall instanceof Single) {
                return ((Single) adaptedCall).onErrorResumeNext(throwable
                        -> Single.error(asRetrofitException((Throwable) throwable)));
            }

            if (adaptedCall instanceof Observable)
                return ((Observable) adaptedCall).onErrorResumeNext(
                        (Function<? super Throwable, ? extends ObservableSource>) throwable
                                -> Observable.error(asRetrofitException(throwable)));

            return adaptedCall;
        }

        private RetrofitException asRetrofitException(Throwable throwable) {

            Timber.e(throwable);

            if (throwable instanceof HttpException) {
                return RetrofitException.httpError((HttpException) throwable, retrofit);
            }

            if (throwable instanceof SocketTimeoutException) {
                return RetrofitException.socketError((SocketTimeoutException) throwable);
            }

            if (throwable instanceof IOException) {
                return RetrofitException.networkError((IOException) throwable);
            }

            return RetrofitException.unknownError(throwable);
        }
    }
}