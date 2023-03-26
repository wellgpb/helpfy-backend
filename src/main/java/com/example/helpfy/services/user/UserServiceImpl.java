package com.example.helpfy.services.user;

import com.example.helpfy.exceptions.BadRequestException;
import com.example.helpfy.exceptions.Constants;
import com.example.helpfy.exceptions.NotFoundException;
import com.example.helpfy.models.User;
import com.example.helpfy.repositories.UserRepository;
import com.example.helpfy.services.encoder.Encoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Encoder encoder;

    public UserServiceImpl(UserRepository userRepository, Encoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new NotFoundException(Constants.USER_NOT_FOUND));
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent((value) -> {
            throw new BadRequestException(Constants.USER_ALREADY_EXISTS);
        });

        var hash = encoder.encode(user.getPassword());
        user.setPassword(hash);

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User updateUser(Long id, User newUser) {
        var user = getUserById(id);

        var updatedUser = updateAllInformationUser(newUser, user);

        return userRepository.saveAndFlush(updatedUser);

    }

    private User updateAllInformationUser(User newUser, User user) {
        // TODO: refactor this
        if(newUser.getEmail() != null) {
            user.setEmail(newUser.getEmail());
        }
        if(newUser.getName() != null) {
            user.setName(newUser.getName());
        }
        if(newUser.getLastName() != null) {
            user.setLastName(newUser.getLastName());
        }
        if(newUser.getAvatarLink() != null) {
            user.setAvatarLink(newUser.getAvatarLink());
        }

        return user;
    }

    @Override
    public void deleteUser(Long id) {
        var user = getUserById(id);
        userRepository.delete(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new NotFoundException(Constants.USER_NOT_FOUND);
        });
    }
}
