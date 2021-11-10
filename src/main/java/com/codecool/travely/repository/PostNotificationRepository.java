package com.codecool.travely.repository;

import com.codecool.travely.model.social.PostNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostNotificationRepository extends JpaRepository<PostNotification, Long> {
}
