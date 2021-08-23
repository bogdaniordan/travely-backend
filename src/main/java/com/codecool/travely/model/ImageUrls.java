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
    private String firstImage;
    private String secondImage;
    private String thirdImage;

    public List<String> getAllImages() {
        return List.of(firstImage, secondImage, thirdImage);
    }
}
