package epam.service_impl;

import epam.repository.UserRepository;
import epam.request_dto.ChangePasswordRequestDTO;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
       return userRepository.changePassword(changePasswordRequestDTO);
    }

    @Override
    public Boolean existsByUsernameAndPassword(String username, String password) {
        return userRepository.existsByUsernameAndPassword(username, password);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
