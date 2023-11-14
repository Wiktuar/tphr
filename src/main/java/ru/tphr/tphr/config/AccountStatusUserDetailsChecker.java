package ru.tphr.tphr.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.security.Principal;

// класс обрабтывает ситуации, когда пользователь неактивен или заблокирован
// и выводит соответствующие сообщения
public class AccountStatusUserDetailsChecker implements UserDetailsChecker {
    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public void check(UserDetails userDetails) {
        if (!userDetails.isAccountNonLocked()) {
            this.logger.debug("Failed to authenticate since user account is locked");
            throw new LockedException("Your account is blocked");
        }
//        if (!userDetails.isEnabled()) {
//            this.logger.debug("Failed to authenticate since user account is disabled");
//            throw new DisabledException(
//                    this.messages.getMessage("AccountStatusUserDetailsChecker.disabled", "User is disabled"));
//        }
//        if (!userDetails.isAccountNonExpired()) {
//            this.logger.debug("Failed to authenticate since user account is expired");
//            throw new AccountExpiredException(
//                    this.messages.getMessage("AccountStatusUserDetailsChecker.expired", "User account has expired"));
//        }
        if (!userDetails.isCredentialsNonExpired()) {
            this.logger.debug("Failed to authenticate since user account credentials have expired");
            throw new CredentialsExpiredException("Пароль просрочен");
        }
    }
}
