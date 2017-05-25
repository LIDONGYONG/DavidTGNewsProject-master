package newsdemo.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import newsdemo.callback.OkHttpBaseCallback;
import newsdemo.callback.OnGetByteArrayListener;
import newsdemo.callback.OnGetJsonObjectListener;
import newsdemo.callback.OnGetOkhttpStringListener;
import newsdemo.constants.Constants;
import newsdemo.enums.HttpMethodType;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {
    private static OkHttpClient client;
    private volatile static OkHttpUtils mOkHttputils;
    private final String TAG = OkHttpUtils.class.getSimpleName();
    private static Handler handler;
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");

    private static Gson gson;

    private OkHttpUtils() {
        client = new OkHttpClient().newBuilder().readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS).build();
        handler = new Handler(Looper.getMainLooper());
        gson = new Gson();
    }

    public static OkHttpUtils getInstance() {

        if (mOkHttputils == null) {
            synchronized (OkHttpUtils.class) {
                if (mOkHttputils == null) {
                    mOkHttputils = new OkHttpUtils();
                }
            }
        }
        return mOkHttputils;
    }

    public void asyncJsonStringByURL(String url, final OnGetOkhttpStringListener callBack) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonStringMethod(response.body().string(), callBack);
                }
            }
        });
    }

    public void asyncJsonObjectByURL(String url, final OnGetJsonObjectListener callBack) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }
        });
    }

    public void asyncGetByteByURL(String url, final OnGetByteArrayListener callBack) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessByteMethod(response.body().bytes(), callBack);
                }
            }
        });
    }

    public void sendComplexForm(String url, Map<String, String> params, final OnGetJsonObjectListener callBack) {
        RequestBody request_body = builderFormData(params);//表单对象，包含以input开始的对象，以html表单为主
        Request request = new Request.Builder().url(url).post(request_body).build();//采用post方式提交
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }
        });
    }

    public void sendStringByPostMethod(String url, String content, final OnGetJsonObjectListener callBack) {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_MARKDOWN, content)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response != null && response.isSuccessful()) {
                    onSuccessJsonObjectMethod(response.body().string(), callBack);
                }
            }
        });
    }


    private void onSuccessJsonStringMethod(final String jsonValue, final OnGetOkhttpStringListener callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(jsonValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void onSuccessByteMethod(final byte[] data, final OnGetByteArrayListener callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onResponse(data);
                }
            }
        });
    }

    private void onSuccessJsonObjectMethod(final String jsonValue, final OnGetJsonObjectListener callBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    try {
                        callBack.onResponse(new JSONObject(jsonValue));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public <T> void get(String url, Class<T> clazz, OkHttpBaseCallback callback) {


        Request request = buildGetRequest(url);

        request(request, clazz, callback);

    }

    public <T> void post(String url, Map<String, String> param, Class<T> clazz, OkHttpBaseCallback callback) {

        Request request = buildPostRequest(url, param);
        request(request, clazz, callback);
    }


    public <T> void request(final Request request, final Class<T> clazz, final OkHttpBaseCallback callback) {

        callback.onBeforeRequest(request);

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                callbackFailure(callback, request, e);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(response);
                callbackResponse(callback, response);

                if (response.isSuccessful()) {

                    String resultStr = response.body().string();

                    LogUtil.E("okhttp_result " + resultStr);
                    if (callback.mType == String.class) {
                        callbackSuccess(callback, response, resultStr);
                    } else {
                        try {
                            Object obj = gson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback, response, obj);
                        } catch (com.google.gson.JsonParseException e) {
                            callback.onError(response, response.code(), e);
                        }
                    }
                } else {
                    callbackError(callback, response, null);
                }
            }

        });


    }


    private void callbackSuccess(final OkHttpBaseCallback callback, final Response response, final Object obj) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }


    private void callbackError(final OkHttpBaseCallback callback, final Response response, final Exception e) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }


    private void callbackFailure(final OkHttpBaseCallback callback, final Request request, final IOException e) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }


    private void callbackResponse(final OkHttpBaseCallback callback, final Response response) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }


    private Request buildPostRequest(String url, Map<String, String> params) {

        return buildRequest(url, HttpMethodType.POST, params);
    }

    private Request buildGetRequest(String url) {

        return buildRequest(url, HttpMethodType.GET, null);
    }

    private Request buildRequest(String url, HttpMethodType methodType, Map<String, String> params) {

        Request.Builder builder = new Request.Builder()
                .url(url).addHeader(Constants.API_KEY, Constants.API_KEY_SECRET);

        if (methodType == HttpMethodType.POST) {
            RequestBody body = builderFormData(params);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }


        return builder.build();
    }


    private RequestBody builderFormData(Map<String, String> params) {


        FormBody.Builder form_builder = new FormBody.Builder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {

                } else {
                    form_builder.add(entry.getKey(), entry.getValue());
                }
            }
        }
        return form_builder.build();

    }

    public String syncGetByURL(String url) {
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
