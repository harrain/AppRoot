package code_base;

public interface UI<D,T> {

    void setPresenter(T t);
    void onSuccess(D d);
    void onError(String tag, String error);
}
