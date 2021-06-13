package com.edu.hutech.services.core;

import com.edu.hutech.entities.User;

import org.springframework.data.domain.Page;

public interface UserService extends IService<User> {
    Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);


}
