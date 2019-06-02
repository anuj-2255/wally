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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.worldtechq.wally.Adapters.PhotosAdapter;
import tk.worldtechq.wally.Modules.Photo;
import tk.worldtechq.wally.R;
import tk.worldtechq.wally.Webservices.ApiInterface;
import tk.worldtechq.wally.Webservices.ServiceGenerator;

public class PhotosFragment extends Fragment {
    private final String TAG = PhotosFragment.class.getSimpleName();
    @BindView(R.id.foto_prog_bar)
    ProgressBar progressBar;
    @BindView(R.id.foto_recycle)
    RecyclerView recyclerView;

    private PhotosAdapter photosAdapter;
    private List<Photo> photos = new ArrayList<>();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        unbinder = ButterKnife.bind(this, view);
        //recycler view
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getPhotos();
        return view;
    }

    private void getPhotos() {
        final ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Photo>> call = apiInterface.getphotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "THIS IS DATAA>>>>>>" + response.message());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    photos.addAll(response.body());
                    photosAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "fetched successful", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "failed check the space between Client-ID and ending quote" + response.message());
                    Toast.makeText(getContext(), "this is failure", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "FAILED" + t.getMessage());
                Toast.makeText(getContext(), "this is failure", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
