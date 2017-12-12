package presenter;

import view.LoginView;

/**
 * Created by smkko on 12/11/2017.
 */

public class LoginPresenter implements LoginPresenterImp {
    LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void checkLogin(String userName, String password) {
        if (userName.equals("minhpvn") && password.equals("Aloal01234")) {
            loginView.onLoginSuccess(userName,password);
        } else {
            loginView.onLoginError();
        }
    }
}
