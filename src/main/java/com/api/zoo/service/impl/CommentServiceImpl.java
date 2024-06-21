package com.api.zoo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.api.zoo.dto.request.CommentRequestDto;
import com.api.zoo.dto.request.ReplyCommentRequestDto;
import com.api.zoo.dto.response.CommentResponseDto;
import com.api.zoo.dto.response.PercentageAsweredCommentsResponseDto;
import com.api.zoo.dto.response.ReplyCommentResponseDto;
import com.api.zoo.entity.Animal;
import com.api.zoo.entity.Comment;
import com.api.zoo.entity.User;
import com.api.zoo.exception.EntityNotFoundException;
import com.api.zoo.repository.CommentRepository;
import com.api.zoo.service.AnimalService;
import com.api.zoo.service.CommentService;
import com.api.zoo.service.TokenService;
import com.api.zoo.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final TokenService tokenService;
    private final UserService userService;
    private final AnimalService animalService;
    private final CommentRepository commentRepository;

    private static final String COMMENT_NOT_FOUND = "Comment with id %s not found";

    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Long userId = tokenService.getUserIdByToken(tokenService.getToken());
        User user = userService.getUserById(userId);
        Animal animal = animalService.getAnimalEntityById(commentRequestDto.getAnimalId());

        ModelMapper modelMapper = new ModelMapper();
        commentRequestDto.setMessage(commentRequestDto.getMessage().toUpperCase());
        Comment comment = modelMapper.map(commentRequestDto, Comment.class);

        comment.setUser(user);
        comment.setAnimal(animal);
        comment.setCreatedAt(LocalDateTime.now());

        return modelMapper.map(commentRepository.save(comment), CommentResponseDto.class);
    }

    @Override
    public ReplyCommentResponseDto replyComment(Long id, ReplyCommentRequestDto answerRequestDto) {
        Optional<Comment> comment = commentRepository.findById(id);

        if (Boolean.FALSE.equals(comment.isPresent())) 
            throw new EntityNotFoundException(String.format(COMMENT_NOT_FOUND, id));

        Long userId = tokenService.getUserIdByToken(tokenService.getToken());
        User user = userService.getUserById(userId);

        ModelMapper modelMapper = new ModelMapper();
        answerRequestDto.setMessage(answerRequestDto.getMessage().toUpperCase());

        Comment reply = modelMapper.map(answerRequestDto, Comment.class);

        reply.setUser(user);
        reply.setAnimal(comment.get().getAnimal());
        reply.setCreatedAt(LocalDateTime.now());
        reply.setRootComment(comment.get());

        return modelMapper.map(commentRepository.save(reply), ReplyCommentResponseDto.class);
    }

    @Override
    public List<CommentResponseDto> findAllCommentByMessageMatch(String message) {
        return commentRepository.findByRootCommentIsNullAndMessageContainingIgnoreCase(message).stream()
                .map(comment -> new ModelMapper().map(comment, CommentResponseDto.class)).toList();
    }

    @Override
    public List<ReplyCommentResponseDto> findAllReplyCommentByMessageMatch(String message) {
        return commentRepository.findByRootCommentIsNotNullAndMessageContainingIgnoreCase(message).stream()
                .map(comment -> new ModelMapper().map(comment, ReplyCommentResponseDto.class)).toList();
    }

    @Override
    public PercentageAsweredCommentsResponseDto percentageOfCommentsAsweredByOthers() {
        Long totalComments = commentRepository.countTotalComments();
        Long commentsAsweredByOthers = commentRepository.countCommentsAsweredByOthers();
        Double percentage = (double) commentsAsweredByOthers / totalComments * 100;
        return new PercentageAsweredCommentsResponseDto(percentage);
    }
    
}
