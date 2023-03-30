package userservice.org.Service.PasswordReset;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userservice.org.Model.UserLoginDetails;
import userservice.org.Model.UserModel;
import userservice.org.Repository.UserModelRepo;

import java.util.Date;
import java.util.Objects;
@Service
public class PasswordResetImpl implements PasswordReset{
    @Autowired
    UserModelRepo umr;
    @Override
    public Boolean resetPassword(String email, String password, String newPassword) throws AuthenticationException {
        //Check if user exists.
        UserModel user = umr.findByEmail(email);
        if(user==null)
        {
            throw new AuthenticationException("EMAIL ID DOES NOT EXIST");
        }
        else if(Objects.equals(user.getPasswordHash(),password)) {
            //If user exists. We check if the account is either inactive or banned.
            UserLoginDetails uld = user.getUserLoginDetails();
            if(user.getUserLoginDetails().getAccountStatus()=='I') // If the account is inactive set it to active and login.
            {
                uld.setAccountStatus('A');
            }
            if(user.getUserLoginDetails().getAccountStatus()=='B') //If account is banned then mentioned the same.
            {
                throw new AuthenticationException("Account is banned hence cannot access account data");
            }
            //set the last login to now. login status to true.
            uld.setLoginStatus(true);
            uld.setLastLogin(new Date());
            user.setUserLoginDetails(uld);
            user.setPasswordHash(newPassword); // reset the new password and save user.
            umr.save(user);
            return true;
        }
        else
        {
            return false;
        }
    }
}
