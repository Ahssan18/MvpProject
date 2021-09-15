package uk.co.falcona.mvpproject.main.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.falcona.mvpproject.R;
import uk.co.falcona.mvpproject.main.ui.home.Posts;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.CustomPost> {
    Context context;
    List<Posts> list;

    public PostsAdapter(Context context, List<Posts> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public CustomPost onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_design, parent, false);
        return new CustomPost(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomPost holder, int position) {
        try {
            Picasso.get().load(list.get(position).getImage()).into(holder.iPost);
            Picasso.get().load(list.get(position).getProfile()).into(holder.iProfile);
            holder.tTitle.setText(list.get(position).getTitle());
            holder.tName.setText(list.get(position).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomPost extends RecyclerView.ViewHolder {
        private ImageView iPost;
        private TextView tName, tTitle;
        private CircleImageView iProfile;

        public CustomPost(@NonNull @NotNull View itemView) {
            super(itemView);
            tName = itemView.findViewById(R.id.tv_name);
            tTitle = itemView.findViewById(R.id.title_post);
            iProfile = itemView.findViewById(R.id.profile);
            iPost = itemView.findViewById(R.id.iv_post);
        }
    }
}
