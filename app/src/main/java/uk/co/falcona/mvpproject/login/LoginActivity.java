package uk.co.falcona.mvpproject.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uk.co.falcona.mvpproject.R;
import uk.co.falcona.mvpproject.SignUp.SignUpPresenter;
import uk.co.falcona.mvpproject.databinding.ActivityLoginBinding;
import uk.co.falcona.mvpproject.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements SignUpPresenter.View {

    LoginPresenter presenter;
    ProgressBar progressBar;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initLoginPresenteries();
        clickListener();


        check();


    }

    private void check() {
        boolean check;
        System.out.println("Hello World");
        List<String> list=new ArrayList<>();
        list.add("ahssan");
        list.add("usman");
        list.add("abc");
        list.add("xyz");
        List<String> list2=new ArrayList<>();
        list2.add("ahssan");
        list2.add("usman");

        for(String data:list)
        {
            check=false;
            for(String data2:list2)
            {
                if(data.equals(data2))
                {
                    check=true;
                    break;
                }
            }
            if(!check)
            {
                Log.e("user_name",data);
            }
        }
    }

    private void clickListener() {
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.loginUser(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
            }
        });
    }

    private void initLoginPresenteries() {
        progressBar = findViewById(R.id.progress);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        presenter = new LoginPresenter(this, this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void success(String date) {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
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