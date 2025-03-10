package epam.service.impl;

import epam.entity.User;
import epam.repository.UserRepository;
import epam.dto.request_dto.ChangePasswordRequestDTO;
import epam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Log log = LogFactory.getLog(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Override
    public Boolean existsByUsernameAndPassword(String username, String password) {
        return userRepository.existsByUsernameAndPassword(username, password);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        if (changePasswordRequestDTO.getUsername() != null
                && changePasswordRequestDTO.getOldPassword() != null
                && changePasswordRequestDTO.getNewPassword() != null
        )
            return userRepository.changePassword(changePasswordRequestDTO);
        return false;
    }

    @Override
    public Boolean toggleStatus(String username) {

        if (userRepository.existsByUsername(username)) {

            User user = userRepository.getByUsername(username);

            boolean newStatus = !user.getIsActive();
            System.out.println("newStatus: " + newStatus);

            user.setIsActive(newStatus);
            System.out.println("user: " + user);

            System.out.println("Toggling isActive for user " + username + " to: " + newStatus);

            return userRepository.toggleActiveStatus(user);
        }

        return false;
    }
}
