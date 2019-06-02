package tk.worldtechq.wally.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.worldtechq.wally.Adapters.CollectionsAdapter;
import tk.worldtechq.wally.Modules.Collection;
import tk.worldtechq.wally.R;
import tk.worldtechq.wally.Utils.Functions;
import tk.worldtechq.wally.Webservices.ApiInterface;
import tk.worldtechq.wally.Webservices.ServiceGenerator;

public class CollectionFragment extends Fragment {
    private final String TAG1 = CollectionFragment.class.getSimpleName();
    @BindView(R.id.coll_pbar)
    ProgressBar coll_prog;
    @BindView(R.id.fragment_collections_gridview)
    GridView gridView;


    private CollectionsAdapter collectionsAdapter;
    private List<Collection> collections = new ArrayList<>();

    private Unbinder unbinder1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        unbinder1 = ButterKnife.bind(this, view);
        collectionsAdapter = new CollectionsAdapter(getActivity(), collections);
        gridView.setAdapter(collectionsAdapter);
        gridView.setVisibility(View.INVISIBLE);
        coll_prog.setVisibility(View.VISIBLE);
        getCollections();
        return view;
    }

    @OnItemClick(R.id.fragment_collections_gridview)
    public void setGridView(int position){
        Collection collection=collections.get(position);
        Bundle bundle=new Bundle();
        bundle.putInt("collectionId",collection.getId());
        Collection2Fragment collection2Fragment=new Collection2Fragment();
        collection2Fragment.setArguments(bundle);
        Functions.changeinMainFragmentwithBack(getActivity(),collection2Fragment);
    }

    private void getCollections() {
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<List<Collection>> call = apiInterface.getcollection();
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (response.isSuccessful()) {
                    gridView.setVisibility(View.VISIBLE);
                    coll_prog.setVisibility(View.INVISIBLE);
                    collections.addAll(response.body());
                    collectionsAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "data is fetched", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG1, "this is failed and else called" + response.message());
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.d(TAG1, "this is failed" + t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                coll_prog.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
