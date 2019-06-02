package tk.worldtechq.wally.Webservices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tk.worldtechq.wally.Modules.Collection;
import tk.worldtechq.wally.Modules.Photo;

public interface ApiInterface {
    @GET("photos")
    Call<List<Photo>> getphotos();

    @GET("collections/featured")
    Call<List<Collection>> getcollection();

    @GET("collections/{id}")
    Call<Collection> getInformationofCollection(@Path("id") int id);

    @GET("collections/{id}/photos")
    Call<List<Photo>> getPhotosofCollection(@Path("id") int id);

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String id);

}
