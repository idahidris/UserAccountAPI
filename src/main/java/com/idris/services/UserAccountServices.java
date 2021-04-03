package com.idris.services;

import com.idris.entity.UserAccount;
import com.idris.repos.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserAccountServices {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public List<UserAccount> findAll(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return userAccountRepository.findAll(pageable).toList();


    }
}
