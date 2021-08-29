package com.codecool.travely.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "image_urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageUrls {

    @Id
    @GeneratedValue
    private Long id;
    private String firstImage = "firstImage.jpg";
    private String secondImage = "secondImage.jpg";
    private String thirdImage = "thirdImage.jpg";

    public ImageUrls(String firstImage, String secondImage, String thirdImage) {
        this.firstImage = firstImage;
        this.secondImage = secondImage;
        this.thirdImage = thirdImage;
    }

    public List<String> getAllImages() {
        return List.of(firstImage, secondImage, thirdImage);
    }
}
