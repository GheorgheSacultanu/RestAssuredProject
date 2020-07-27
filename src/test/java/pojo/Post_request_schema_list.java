package pojo;


public class Post_request_schema_list {
    private String name;
    private String iso_639_1;
    private String description;
    private boolean pub;
    private String iso_3166_1;


    public String getName() {
        return name;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPub() {
        return pub;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public Post_request_schema_list(String name, String iso_639_1, String description, boolean pub, String iso_3166_1) {
        this.name = name;
        this.iso_639_1 = iso_639_1;
        this.description = description;
        this.pub = pub;
        this.iso_3166_1 = iso_3166_1;
    }

}
