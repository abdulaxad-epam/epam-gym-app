package epam.service.impl;

import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.entity.User;
import epam.exception.exception.UserNotFoundException;
import epam.repository.UserRepository;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Boolean existsByUsernameAndPassword(String username, String password) {
        return userRepository.existsByUsernameAndPassword(username.toLowerCase(), password);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username.toLowerCase());
    }

    @Transactional
    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {

        userRepository.getByUsernameAndPassword(((changePasswordRequestDTO.getUsername())), changePasswordRequestDTO.getOldPassword())
                .ifPresentOrElse(
                        user -> user.setPassword(changePasswordRequestDTO.getNewPassword()),
                        () -> {
                            throw new UserNotFoundException("User not found");
                        });
        return true;
    }

}
