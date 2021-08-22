package com.codecool.travely.aws;

public enum BucketName {
    PROFILE_IMAGE("travely-bucket-images");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}