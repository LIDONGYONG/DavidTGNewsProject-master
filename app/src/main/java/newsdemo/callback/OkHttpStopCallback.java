package newsdemo.callback;
import okhttp3.Request;
import okhttp3.Response;

public abstract class OkHttpStopCallback<T> extends OkHttpBaseCallback<T> {
    @Override
    public void onBeforeRequest(Request request) {

    }

    @Override
    public void onFailure(Request request, Exception e) {

    }

    @Override
    public  void onResponse(Response response){

    }
    @Override
    public abstract void onSuccess(Response response, T t);

    @Override
    public void onError(Response response, int code, Exception e) {

    }
}
