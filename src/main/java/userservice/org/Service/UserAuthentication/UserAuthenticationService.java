package userservice.org.Service.UserAuthentication;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userservice.org.Model.UserLoginDetails;
import userservice.org.Model.UserModel;
import userservice.org.Repository.UserModelRepo;

import java.util.Date;
import java.util.Objects;

@Service
public class UserAuthenticationService implements UserAuthentication{
    @Autowired
    UserModelRepo umr;
    @Override
    public Boolean login(String email, String password) throws AuthenticationException {
        //For logging in we check if the user with email id exists.
        UserModel user = umr.findByEmail(email);
        if(user==null)
        {
            throw new AuthenticationException("EMAIL ID DOES NOT EXIST");
        }
        //If he does then check the credentials.
        else if(Objects.equals(user.getPasswordHash(),password)) {
            UserLoginDetails uld = user.getUserLoginDetails();
            //If the user is inactive then change status to active and proceed
            if(user.getUserLoginDetails().getAccountStatus()=='I')
            {
                uld.setAccountStatus('A');
            }
            //If the user is banned then dont allow login
            if(user.getUserLoginDetails().getAccountStatus()=='B')
            {
                throw new AuthenticationException("Account is banned from logging in");
            }
            //If active or set to active then we change the login status to true. Change last login to current time and date.
            uld.setLoginStatus(true);
            uld.setLastLogin(new Date());
            user.setUserLoginDetails(uld);
            //Save the user
            umr.save(user);
            return true;
        }
        else
        {
            return false;
        }
    }
    @Override
    public Boolean logout(String email) throws AuthenticationException {
        //For logout we check if user is existing
        UserModel user = umr.findByEmail(email);
        if(user==null)
        {
            throw new AuthenticationException("EMAIL ID DOES NOT EXIST");
        }
        else if(user.getUserLoginDetails().getLoginStatus()==false)
        {
            //If yes, check login status. If he is already logged out, then no need to again.
            throw new AuthenticationException("USER Already Logged out");
        }
        else
        {
            //if logged in then set login status to false as logout and save the user.
            UserLoginDetails uld = user.getUserLoginDetails();
            uld.setLoginStatus(false);
            user.setUserLoginDetails(uld);
            umr.save(user);
            return true;
        }
    }
}
