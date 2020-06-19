package com.example.filetestarlegacy;

public class AssetObject {
    private String name;
    private String image;
    private String url;

    public AssetObject(String name, String image, String url) {
        this.name = name;
        this.image = image;
        this.url = url;
    }

    public String getName() { return name; }
    public String getImage() { return image; }
    public String getUrl() { return url; }
}
