package com.idris.services;

import com.idris.dto.GenericResponseDto;
import com.idris.dto.UserAccountDto;
import com.idris.entity.UserAccount;
import com.idris.entity.UserRole;
import com.idris.entity.UserStatus;
import com.idris.error.AppException;
import com.idris.repos.UserAccountRepository;
import com.idris.repos.UserRoleRepository;
import com.idris.repos.UserStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@Service
public class UserAccountServices {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
     HttpServletRequest httpServletRequest;

    @Value("${spring.mail.username}")
    private String fromAddress;


    public List<UserAccount> findAll(int page, int pageSize){
        try {
            Pageable pageable = PageRequest.of(page, pageSize);
            return userAccountRepository.findAll(pageable).toList();
        }
        catch (Exception ex){
            throw new AppException(ex.getCause().getMessage());
        }


    }

    public UserAccount findById(long id){
        try {
            return userAccountRepository.findById(id).orElse(null);
        }
        catch (Exception ex){
            throw new AppException(ex.getCause().getMessage());
        }

    }

    public UserAccount delById(long id){
        try {
            UserAccount userAccount = userAccountRepository.findById(id).orElse(null);
            if (Objects.nonNull(userAccount)) {
                userAccount.setDeactivatedDate(new Date());
                UserStatus userStatus = userStatusRepository.findByName("DEACTIVATED").orElse(null);
                userAccount.setStatus(userStatus);
                return userAccountRepository.save(userAccount);
            }

            return new UserAccount();
        }
        catch (Exception ex){
            throw  new AppException(ex.getCause().getMessage());
        }
    }


    public UserAccount verify(String code){
        try {
            UserAccount userAccount = userAccountRepository.findByVerificationCode(code).orElse(null);
            if (Objects.nonNull(userAccount)) {
                userAccount.setVerifiedDate(new Date());
                userAccount.setVerified(true);
                UserStatus userStatus = userStatusRepository.findByName("VERIFIED").orElse(null);
                userAccount.setStatus(userStatus);
                return userAccountRepository.save(userAccount);
            }

            return new UserAccount();
        }
        catch (Exception ex){
            throw  new AppException(ex.getCause().getMessage());
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GenericResponseDto  create(UserAccountDto userAccountdto){

        try {

            UserAccount userAccount = toUserAccount(userAccountdto);
            UserAccount existUser = userAccountRepository.getByEmail(userAccount.getEmail()).orElse(null);

            if (Objects.nonNull(existUser)) {
                if (userAccount.equals(existUser)) {

                    return new GenericResponseDto(existUser);
                } else {

                    Set<String> error = new HashSet<>();
                    error.add("Already registered");
                    return new GenericResponseDto(error);
                }
            }
            userAccount.setStatus(userStatusRepository.findByName("REGISTERED").orElse(null));
            userAccount.setVerified(false);
            userAccount.setVerificationCode(UUID.randomUUID().toString());
            UserAccount result = userAccountRepository.save(userAccount);

            String baseUrl = getBaseUrl(httpServletRequest);
            sendVerificationByEmail(userAccount, baseUrl);
            return new GenericResponseDto(result);
        }
        catch (Exception ex){

            throw new AppException(ex.getCause().getMessage());
        }

    }


    public GenericResponseDto update(UserAccountDto userAccount, Long id){
        try {
            UserAccount userDb = toUserAccount(userAccount, id);
            if (Objects.isNull(userDb)) {
                Set<String> error = new HashSet<>();
                error.add("This user does not exists:" + id);
                return new GenericResponseDto(error);
            }
            return new GenericResponseDto(userAccountRepository.save(userDb));
        }
        catch (Exception ex){
            throw new AppException(ex.getCause().getMessage());
        }

    }


    public UserAccount toUserAccount(UserAccountDto userAccountDto){
        return toUserAccount(userAccountDto, 0L);
    }


    public UserAccount toUserAccount(UserAccountDto userAccountDto, long id){

        UserAccount userAccount = new UserAccount();

        if(id !=0){
            userAccount = userAccountRepository.findById(id).orElse(null);
        }

        if(Objects.nonNull(userAccount)) {
            if (Objects.nonNull(userAccountDto.getMobile())) {
                userAccount.setMobile(userAccountDto.getMobile());
            }
            if (Objects.nonNull(userAccountDto.getPassword())) {
                userAccount.setPassword(userAccountDto.getPassword());
            }

            if (Objects.nonNull(userAccountDto.getDeactivatedDate())) {
                userAccount.setDeactivatedDate(userAccountDto.getDeactivatedDate());
            }

            if (Objects.nonNull(userAccountDto.getRegistered())) {
                userAccount.setRegistered(userAccountDto.getRegistered());
            }

            if (userAccountDto.getRole() != 0) {
                UserRole userRole = userRoleRepository.findById(userAccountDto.getRole()).orElse(new UserRole());
                userAccount.setRole(userRole);
            }
            if (Objects.nonNull(userAccountDto.getTitle())) {
                userAccount.setTitle(userAccountDto.getTitle());
            }

            if (userAccountDto.getStatus() != 0) {
                UserStatus userStatus = userStatusRepository.findById(userAccountDto.getStatus()).orElse(new UserStatus());
                userAccount.setStatus(userStatus);
            }


            if (Objects.nonNull(userAccountDto.getVerifiedDate())) {
                userAccount.setVerifiedDate(userAccountDto.getVerifiedDate());
            }

            if (Objects.nonNull(userAccountDto.getVerified())) {
                userAccount.setVerified(userAccountDto.getVerified().equals("1"));
            }
            if (Objects.nonNull(userAccountDto.getFirstname())) {
                userAccount.setFirstname(userAccountDto.getFirstname());
            }

            if (Objects.nonNull(userAccountDto.getLastname())) {
                userAccount.setLastname(userAccountDto.getLastname());
            }

            if (Objects.nonNull(userAccountDto.getEmail())) {
                userAccount.setEmail(userAccountDto.getEmail());
            }



        }

        return userAccount;

    }

    private void sendVerificationByEmail(UserAccount user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String senderName = "UserAccountAPI";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to activate your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">ACTIVATE</a></h3>"
                + "Thank you,<br>"
                + "UserAccountAPI.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    String getBaseUrl(HttpServletRequest request) {
        return request.getRequestURL().substring(0, request.getRequestURL().length() - request.getRequestURI().length()) + request.getContextPath();

    }
}
