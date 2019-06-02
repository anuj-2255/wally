package tk.worldtechq.wally.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import tk.worldtechq.wally.Activities.FullscreenPhotoActivity;
import tk.worldtechq.wally.Modules.Photo;
import tk.worldtechq.wally.R;
import tk.worldtechq.wally.Utils.SquareImages;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {
    private Context context;
    private List<Photo> photos;

    public PhotosAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_foto, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Photo photo = photos.get(i);
        viewHolder.username.setText(photo.getUser().getUsername());
        GlideApp.with(context)
                .load(photo.getUrl().getRegular())
                .placeholder(R.drawable.placeholder)
                .override(600, 600)
                .into(viewHolder.foto);
        GlideApp.with(context)
                .load(photo.getUser().getProfileImage().getSmall())
                .into(viewHolder.userAvatar);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_foto)
        CircleImageView userAvatar;
        @BindView(R.id.user_tv)
        TextView username;
        @BindView(R.id.item_foto)
        SquareImages foto;
        @BindView(R.id.item_foto_layout)
        FrameLayout frameLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.item_foto_layout})
        public void setFrameLayout() {
            int position = getAdapterPosition();
            String photoId = photos.get(position).getId();
            Intent intent = new Intent(context, FullscreenPhotoActivity.class);
            intent.putExtra("photoId", photoId);
            context.startActivity(intent);
        }
    }
}
