package tk.worldtechq.wally.Utils;

import java.util.List;

import io.realm.Realm;
import tk.worldtechq.wally.Modules.Photo;

public class RealmController {
    private final Realm realm;

    public RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public void savePhoto(Photo photo) {
        realm.beginTransaction();
        realm.copyToRealm(photo);
        realm.commitTransaction();
    }

    public void deletePhoto(final Photo photo) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Photo resultPhoto=realm.where(Photo.class).equalTo("id",photo.getId()).findFirst();
                resultPhoto.deleteFromRealm();
            }
        });
    }

    public boolean isPhotoExist(String photoId) {
        Photo resultPhoto=realm.where(Photo.class).equalTo("id",photoId).findFirst();
            if(resultPhoto==null){
                return  false;
            }
            return true;
    }

    public List<Photo> getPhotos() {
        return realm.where(Photo.class).findAll();
    }
}
