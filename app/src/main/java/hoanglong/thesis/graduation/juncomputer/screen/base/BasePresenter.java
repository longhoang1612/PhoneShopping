package hoanglong.thesis.graduation.juncomputer.screen.base;

public interface BasePresenter<T> {
    void setView(T view);

    void onStart();

    void onStop();
}
