package com.codecool.travely.service;

import com.codecool.travely.model.Question;
import com.codecool.travely.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final HostService hostService;
    private final CustomerService customerService;

    private Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find the question with id: " + id));
    }

    private void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void addQuestion(Question question, Long customerId, Long hostId) {
        log.info("Adding question for host with id: " + hostId);
        question.setCustomer(customerService.findById(customerId));
        question.setHost(hostService.findById(hostId));
        saveQuestion(question);
    }

}
