package minhpvn.com.swipedemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import presenter.LoginPresenter;
import view.LoginView;

/**
 * Created by smkko on 12/11/2017.
 */

public class LoginActivity extends AppCompatActivity implements LoginView,GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btnLogin)
    TextView btnLogin;
    LoginPresenter loginPresenter;
    @BindView(R.id.login_button)
    LoginButton fbLoginButton;
    CallbackManager callbackManager;
    String usernameFB;
    String idFB;
    //google login
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
//    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    @BindView(R.id.sign_in_button)
    SignInButton sign_in_button;
    //google login end
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.login);
        // making notification bar transparent
        changeStatusBarColor();
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();

        btnLogin.setOnClickListener(this);
        loginPresenter = new LoginPresenter(this);

        //https://developers.facebook.com/docs/facebook-login/permissions#reference
        fbLoginButton.setReadPermissions("email");
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "======Facebook login success======");
                Log.d(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
                Toast.makeText(LoginActivity.this, "Login Facebook success.", Toast.LENGTH_SHORT).show();

                getFbInfo();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Facebook cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "======Facebook login error======");
                Log.e(TAG, "Error: " + error.toString());
                Toast.makeText(LoginActivity.this, "Login Facebook error.", Toast.LENGTH_SHORT).show();
            }
        });

        //Google login
        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void getFbInfo() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                Log.i("Login: ", me.optString("name"));
                                Log.i("ID: ", me.optString("id"));
                                usernameFB = me.optString("name");
                                idFB = me.optString("id");
                                Toast.makeText(LoginActivity.this, "Name: " + me.optString("name"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(LoginActivity.this, "ID: " + me.optString("id"), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            parameters.putString("userNameFB", usernameFB);
            parameters.putString("idFB", idFB);
            request.setParameters(parameters);
            request.executeAsync();
            intent.putExtras(parameters);
            startActivity(intent);
        }

    }

    @Override
    public void onLoginSuccess(String userName, String password) {
        userName = this.userName.getText().toString();
        password = this.password.getText().toString();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("password", password);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(this, "Login successfully !", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoginError() {
        Toast.makeText(this, "Login false !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
//        String name = userName.getText().toString();
//        String pass = password.getText().toString();
//        loginPresenter.checkLogin(name, pass);
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.btnLogin:
                String name = userName.getText().toString();
                String pass = password.getText().toString();
                loginPresenter.checkLogin(name, pass);
                break;
        }
    }



//google
private void handleSignInResult(GoogleSignInResult result) {
    Log.d(TAG, "handleSignInResult:" + result.isSuccess());
    if (result.isSuccess()) {
        // Đã đăng nhập thành công, hiển thị trạng thái đăng nhập.
        GoogleSignInAccount acct = result.getSignInAccount();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        Bundle bundleGoogle = new Bundle();
        bundleGoogle.putString("googleName",acct.getDisplayName());
        bundleGoogle.putString("googleEmail",acct.getEmail());
        bundleGoogle.putString("googlePhoto",acct.getPhotoUrl().toString());
        intent.putExtras(bundleGoogle);
//        mStatusTextView.setText(acct.getDisplayName());
        startActivity(intent);
    } else {
        // Đã đăng xuất, hiển thị trạng thái đăng xuất.
//        mStatusTextView.setText("Signed out");
        Toast.makeText(getApplicationContext(),"already log out",Toast.LENGTH_SHORT);
    }
}
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed:" + connectionResult);
    }
    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // Nếu dữ liệu của người dùng trong bộ d dệm hợp lệ, OptionalPendingResult sẽ ở trạng thái "done"
            // và GoogleSignInResult sẽ có ngay mà không cần thực hiện đăng nhập lại.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // Nếu người dùng chưa từng đăng nhập trước đó, hoặc phiên làm việc đã hết hạn,
            // thao tác bất đồng bộ này sẽ ngầm đăng nhập người dùng, và thực hiện thao tác cross sign-on.
//            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
//                        mStatusTextView.setText("Signed out");
                        Toast.makeText(LoginActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
