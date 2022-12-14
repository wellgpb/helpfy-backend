package com.example.helpfy.services.search;

import com.example.helpfy.exceptions.BadRequestException;
import com.example.helpfy.models.Question;
import com.example.helpfy.repositories.QuestionRepository;
import com.example.helpfy.services.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionSearchServiceImpl implements QuestionSearchService {
    public static final String VOTE = "vote";
    public static final String RELEVANT = "relevant";
    public static final String ANSWERED = "answered";
    public static final String NEW = "new";

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<Question> search(String title, Set<String> tags, String filter) {
        List<Question> questions = questionRepository.findBySimilarity(title);
        if (tags != null && !tags.isEmpty())
        {
            questions = questions.stream()
                    .filter(question -> question.getTags().stream().anyMatch(tags::contains))
                    .collect(Collectors.toList());
        }

        questions = updateQuestionsByFilter(questions, filter);
        return questions;
    }

    private List<Question> updateQuestionsByFilter(List<Question> questions, String filter) {
        Set<String> validFilters = Set.of(VOTE, RELEVANT, ANSWERED, NEW);
        if (!validFilters.contains(filter)) {
            throw new BadRequestException("Invalid filter.");
        }

        List<Question> newQuestions = new ArrayList<Question>(questions);
        if (VOTE.equals(filter)) newQuestions = updateQuestionsByVote(questions);
        else if (RELEVANT.equals(filter)) newQuestions = updateQuestionsByRelevance(questions);
        else if (ANSWERED.equals(filter)) newQuestions = updateQuestionsByAnswers(questions);
        else if (NEW.equals(filter)) newQuestions = updateQuestionsByDate(questions);

        return newQuestions;
    }

    private List<Question> updateQuestionsByVote(List<Question> questions) {
        Comparator<Question> byLike = (Question q1, Question q2) -> q2.getIdsFromUsersLikes().size() - q1.getIdsFromUsersLikes().size();

        List<Question> newQuestions = new ArrayList<>(questions);
        newQuestions.sort(byLike);

        return newQuestions;
    }

    private List<Question> updateQuestionsByRelevance(List<Question> questions) {
        Comparator<Question> byRelevance = (Question q1, Question q2) -> {
            return (q2.getIdsFromUsersLikes().size() - q2.getIdsFromUsersDislikes().size()) - (q1.getIdsFromUsersLikes().size() - q1.getIdsFromUsersDislikes().size());
        };

        List<Question> newQuestions = new ArrayList<>(questions);
        newQuestions.sort(byRelevance);

        return newQuestions;
    }

    private List<Question> updateQuestionsByAnswers(List<Question> questions) {
        return questions.stream().filter(Question::isAnswered).collect(Collectors.toList());
    }

    private List<Question> updateQuestionsByDate(List<Question> questions) {
        Comparator<Question> byDate = (Question q1, Question q2) -> {
            return q2.getCreatedAt().compareTo(q1.getCreatedAt());
        };

        List<Question> newQuestions = new ArrayList<>(questions);
        newQuestions.sort(byDate);

        return newQuestions;
    }
}
