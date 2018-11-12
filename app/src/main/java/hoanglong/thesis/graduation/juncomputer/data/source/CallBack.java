package hoanglong.thesis.graduation.juncomputer.data.source;

public interface CallBack<T> {
    void getDataSuccess(T data);

    void getDataError(String error);
}