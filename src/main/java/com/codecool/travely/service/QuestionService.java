package com.codecool.travely.service;

import com.codecool.travely.model.social.Question;
import com.codecool.travely.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final HostService hostService;
    private final CustomerService customerService;

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find the question with id: " + id));
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public void addQuestion(Question question, Long customerId, Long hostId) {
        log.info("Adding question for host with id: " + hostId);
        question.setCustomer(customerService.findById(customerId));
        question.setHost(hostService.findById(hostId));
        save(question);
    }

    public List<Question> getAllForHostAndCustomer(Long customerId, long hostId) {
        log.info("Fetching all questions for customer with id: " + customerId + " and host with id: " + hostId);
        return questionRepository.findAllByCustomerId(customerId).stream().filter(question -> question.getHost().getId() == hostId).collect(Collectors.toList());
    }

    public void markAsSolved(Long id) {
        log.info("Changing solved state for questions with id: " + id);
        Question question = findById(id);
        question.setSolved(!question.isSolved());
        save(question);
    }

    public void delete(Long id) {
        log.info("Question with id: " + id + " has been deleted.");
        questionRepository.deleteById(id);
    }

    public void updateQuestion(Long id, Question question) {
        log.info("Updating question with id: " + id);
        Question updatedQuestion = findById(id);
        updatedQuestion.setResponse(question.getResponse());
//        updatedQuestion.setAuthor(question.getAuthor());
//        updatedQuestion.setDate(question.getDate());
//        updatedQuestion.setCustomer(question.getCustomer());
//        updatedQuestion.setSolved(question.isSolved());
//        updatedQuestion.setSeen(question.isSeen());
//        updatedQuestion.setText(question.getText());
        save(updatedQuestion);
    }

    public void markAsSeen(Long id) {
        log.info("Question with id : " + id + " has been marked as seen.");
        Question question = findById(id);
        question.setSeen(true);
        questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }
}
