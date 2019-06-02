package tk.worldtechq.wally.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tk.worldtechq.wally.Modules.Collection;
import tk.worldtechq.wally.R;

import tk.worldtechq.wally.Utils.SquareImages;

public class CollectionsAdapter extends BaseAdapter {
    private Context context;
    private List<Collection> collections;


    public CollectionsAdapter(Context context, List<Collection> collections) {
        this.context = context;
        this.collections = collections;
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int i) {
        return collections.get(i);
    }

    @Override
    public long getItemId(int i) {
        return collections.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_collections, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
           holder= (ViewHolder) view.getTag();
        }
        ButterKnife.bind(this, view);
        Collection collection = collections.get(i);

        if (collection.getTitle() != null) {
            Log.d("Title>>", collection.getTitle());
            holder.title.setText(collection.getTitle());
        }
        holder.totalPhotos.setText(collection.getTotal_photos() + " Photos");

        GlideApp.with(context)
                .load(collection.getCover_photo().getUrl().getRegular())
                .placeholder(R.drawable.placeholder)
                .into(holder.CollectionPhoto);
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.coll_title_tv)
        TextView title;
        @BindView(R.id.coll_total_tv)
        TextView totalPhotos;
        @BindView(R.id.coll_sq_iv)
        SquareImages CollectionPhoto;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
