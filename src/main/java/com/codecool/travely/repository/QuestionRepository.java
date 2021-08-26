package com.codecool.travely.repository;

import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Host;
import com.codecool.travely.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    void deleteById(Long questionId);

    List<Question> findAllByCustomer(Customer customer);

    List<Question> findAllByHost(Host host);

}
