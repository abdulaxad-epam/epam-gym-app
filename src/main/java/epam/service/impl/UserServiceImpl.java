package epam.service.impl;

import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.repository.UserRepository;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        return userRepository.existsByUsername(changePasswordRequestDTO.getUsername()) &&
                userRepository.changePassword(changePasswordRequestDTO);
    }

}
