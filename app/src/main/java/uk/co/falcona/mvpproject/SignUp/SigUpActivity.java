package uk.co.falcona.mvpproject.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uk.co.falcona.mvpproject.R;
import uk.co.falcona.mvpproject.SignUp.models.User;
import uk.co.falcona.mvpproject.login.LoginActivity;
import uk.co.falcona.mvpproject.main.MainActivity;

public class SigUpActivity extends AppCompatActivity implements SignUpPresenter.View {

    ProgressBar progressBar;
    private TextView tvData;
    private Button bSubmit;
    private TextView tvLogin;
    EditText eEmail, ePassword;
    SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.signup);
        presenter = new SignUpPresenter(this, this);
        initViews();
        clickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    private void clickListener() {
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setEmail(eEmail.getText().toString());
                user.setPassword(ePassword.getText().toString());
                presenter.createAccount(user);
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void initViews() {
        tvLogin = findViewById(R.id.tv_login);
        progressBar = findViewById(R.id.progress);
        tvData = findViewById(R.id.tv_data);
        eEmail = findViewById(R.id.et_email);
        ePassword = findViewById(R.id.et_password);
        bSubmit = findViewById(R.id.submit);
    }

    @Override
    public void success(String date) {
        Intent login = new Intent(this, MainActivity.class);
        startActivity(login);
    }

    private void login() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    @Override
    public void Failure(Exception e) {
        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressbar(boolean progress) {
        if (progress) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

    }
}