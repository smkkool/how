package view;

/**
 * Created by smkko on 12/11/2017.
 */

public interface LoginView {
    void onLoginSuccess(String userName, String password);
    void onLoginError();
}
