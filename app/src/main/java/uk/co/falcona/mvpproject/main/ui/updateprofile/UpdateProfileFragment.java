package uk.co.falcona.mvpproject.main.ui.updateprofile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import uk.co.falcona.mvpproject.Helper;
import uk.co.falcona.mvpproject.R;
import uk.co.falcona.mvpproject.databinding.FragmentNotificationsBinding;

import static android.app.Activity.RESULT_OK;

public class UpdateProfileFragment extends Fragment implements UpdateProfilePresenter.View, View.OnClickListener {
    private static final int RC_TAKE_PHOTO = 1;
    private FragmentNotificationsBinding binding;
    UpdateProfilePresenter presenter;
    KProgressHUD hud;
    public String TAG = "UpdateProfileFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        init();
        clickListener();
        return root;
    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.getDataFromDb();
    }

    private void init() {
        presenter = new UpdateProfilePresenter(this, getActivity());
        hud = Helper.initProgressHud(getActivity());
    }

    private void clickListener() {
        binding.ivPicker.setOnClickListener(this);
        binding.btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult =>called");
        if (requestCode == RC_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult =>" + data.getExtras().get("data"));
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            presenter.ProfileUpdated(photo);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void success(String msg) {
        binding.etEmail.setText("");
        binding.etName.setText("");
        binding.etPhone.setText("");
        Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Failure(Exception e) {
        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(Boolean b) {
        if (b) {
            hud.show();
        } else {
            hud.dismiss();
        }
    }

    @Override
    public void setProfile(String url) {
        Picasso.get().load(url).into(binding.circleImageView);
    }

    @Override
    public void dataFromServer(Profile profile) {
        binding.etPhone.setText(profile.getPhone());
        binding.etName.setText(profile.getName());
        binding.etEmail.setText(profile.email);
        try {
            Picasso.get().load(profile.getPic()).into(binding.circleImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_picker:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RC_TAKE_PHOTO);
                break;
            case R.id.btn_update:
                presenter.submitData(binding.etName.getText().toString(), binding.etEmail.getText().toString(), binding.etPhone.getText().toString());
                break;
        }
    }
}