package uk.co.falcona.mvpproject.main.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import uk.co.falcona.mvpproject.Helper;
import uk.co.falcona.mvpproject.SignUp.SignUpPresenter;
import uk.co.falcona.mvpproject.databinding.FragmentHomeBinding;
import uk.co.falcona.mvpproject.main.ui.home.adapter.PostsAdapter;

public class PostsFragment extends Fragment implements PostsPresenter.View {

    private FragmentHomeBinding binding;
    private String TAG="FragmentHomeBinding";
    PostsPresenter postsPresenter;
    private KProgressHUD hud;
    PostsAdapter postsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        postsPresenter=new PostsPresenter(this,getActivity());
        hud= Helper.initProgressHud(getActivity());
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        postsPresenter.loadData();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showProgress(Boolean b) {
        if(b)
        {
          hud.show();
        }else
        {
            hud.dismiss();
        }
    }

    @Override
    public void success(List<Posts> list) {
        try {
            setAdapter(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setAdapter(List<Posts> list) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        binding.recycleposts.setLayoutManager(linearLayoutManager);
        postsAdapter=new PostsAdapter(getActivity(),list);
        binding.recycleposts.setAdapter(postsAdapter);
        postsAdapter.notifyDataSetChanged();
    }

    @Override
    public void Failure(Exception e) {
        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
    }


}