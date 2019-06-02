package tk.worldtechq.wally.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.worldtechq.wally.Adapters.GlideApp;
import tk.worldtechq.wally.Adapters.PhotosAdapter;
import tk.worldtechq.wally.Modules.Collection;
import tk.worldtechq.wally.Modules.Photo;
import tk.worldtechq.wally.R;
import tk.worldtechq.wally.Webservices.ApiInterface;
import tk.worldtechq.wally.Webservices.ServiceGenerator;

public class Collection2Fragment extends Fragment {
    private final String TAG2 = Collection2Fragment.class.getSimpleName();

    @BindView(R.id.col2_username)
    TextView usernamae;
    @BindView(R.id.col2_des_tv)
    TextView description;
    @BindView(R.id.col2_title_tv)
    TextView title;
    @BindView(R.id.col2_user_avatar)
    CircleImageView userAvatar;

    @BindView(R.id.col2_rv)
    RecyclerView recyclerView;
    @BindView(R.id.col2_prog)
    ProgressBar progressBar;

    private Unbinder unbinder;

    private List<Photo> photos = new ArrayList<>();
    private PhotosAdapter photosAdapter;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection2, container, false);
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        Bundle bundle = getArguments();
        int collectionId = bundle.getInt("collectionId");
        getInformationofCollection(collectionId);
        getPhotosofCollection(collectionId);
        return view;
    }

    private void getInformationofCollection(final int collectionId) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<Collection> call = apiInterface.getInformationofCollection(collectionId);
        call.enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if (response.isSuccessful()) {
                    Collection collection = response.body();
                    title.setText(collection.getTitle());
                    description.setText(collection.getDescription());
                    usernamae.setText(collection.getUser().getUsername());
                    GlideApp.with(getActivity())
                            .load(collection.getUser().getProfileImage().getSmall())
                            .placeholder(R.drawable.placeholder)
                            .into(userAvatar);
                    Toast.makeText(getContext(), "data is fetched", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG2, "this is else called" + response.message());
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.d(TAG2, "this is failed " + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPhotosofCollection(int collectionId) {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Photo>> photoCall = apiInterface.getPhotosofCollection(collectionId);
        photoCall.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    photos.addAll(response.body());
                    photosAdapter.notifyDataSetChanged();
                } else {
                    Log.d(TAG2, "this is photo collection else trigged" + response.message());
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG2, "this is photos of collection onfailure trigged" + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
