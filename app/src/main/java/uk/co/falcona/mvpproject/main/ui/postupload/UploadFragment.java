package uk.co.falcona.mvpproject.main.ui.postupload;

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

import uk.co.falcona.mvpproject.Helper;
import uk.co.falcona.mvpproject.SignUp.SignUpPresenter;
import uk.co.falcona.mvpproject.databinding.FragmentDashboardBinding;

import static android.app.Activity.RESULT_OK;


public class UploadFragment extends Fragment implements SignUpPresenter.View, View.OnClickListener {
    private static final int RC_TAKE_PHOTO = 1;
    public String TAG = "UploadFragment";
    private FragmentDashboardBinding binding;
    UploadPresenter presenter;
    KProgressHUD hud;
    Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        presenter = new UploadPresenter(this, getActivity());
        init();
        clickListener();
        return root;
    }

    private void clickListener() {
        binding.ivPicker.setOnClickListener(this::onClick);
        binding.upload.setOnClickListener(this::onClick);
    }

    private void init() {
        hud = Helper.initProgressHud(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void success(String date) {
        binding.etTitle.setText("");
        binding.imageView.setImageBitmap(null);
    }

    @Override
    public void Failure(Exception e) {
        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult =>called");
        if (requestCode == RC_TAKE_PHOTO && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult =>" + data.getExtras().get("data"));
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            bitmap = photo;
            binding.imageView.setImageBitmap(photo);
        }
    }

    @Override
    public void showProgressbar(boolean progress) {
        if (progress) {
            hud.show();
        } else {
            hud.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.ivPicker) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, RC_TAKE_PHOTO);
        } else if (v == binding.upload) {
            presenter.updateData(binding.etTitle.getText().toString(), bitmap);
        }
    }
}