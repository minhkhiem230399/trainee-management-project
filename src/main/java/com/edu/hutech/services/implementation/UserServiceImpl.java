package com.edu.hutech.services.implementation;

import java.util.List;

import com.edu.hutech.entities.User;
import com.edu.hutech.repositories.RoleRepository;
import com.edu.hutech.repositories.UserRepository;
import com.edu.hutech.services.core.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void save(User user, String roleName) {
        user.setRoles(roleRepository.findByName(roleName));
        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User t) {
        t = userRepository.getOne(t.getId());
        userRepository.save(t);
    }


    public User findById(Integer theId) {
        return userRepository.getOne(theId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }


    public void delete(Integer id) {
    }

    @Override
    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.userRepository.findAll(pageable);
    }

    /**
     * check mail
     *
     * @param email
     * @return
     */
    public boolean checkEmail(String email) {
        String account = email.substring(0, email.indexOf("@"));
        List<User> userList = getAll();

        int dem = 0;

        for (User user : userList) {
            if (account.equals(user.getAccount()) || account.equals("admin")) {
                dem++;
            }
        }

        return dem > 1;
    }

}
