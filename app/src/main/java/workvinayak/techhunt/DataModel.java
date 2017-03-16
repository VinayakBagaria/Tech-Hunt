package workvinayak.techhunt;

/**
 * Created by bagariavinayak on 23-10-2016.
 */

public class DataModel {
    String version;
    int id_;
    int image;

    public DataModel(String version, int id_, int image) {
        this.version = version;
        this.id_ = id_;
        this.image=image;
    }


    public String getVersion() {
        return version;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}
