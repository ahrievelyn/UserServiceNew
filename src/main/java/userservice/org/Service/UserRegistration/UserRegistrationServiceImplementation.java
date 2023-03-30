package userservice.org.Service.UserRegistration;

import org.hibernate.event.service.spi.EventListenerRegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userservice.org.Model.UserLoginDetails;
import userservice.org.Model.UserModel;
import userservice.org.Repository.UserModelRepo;

import java.util.Date;
@Service
public class UserRegistrationServiceImplementation extends UserRegisterCheck implements UserRegistrationService{
    @Autowired
    UserModelRepo umr;
    @Override
    public UserModel registration(UserModel user) {
        /*
        Rule No 1: email id cannot be repeating for multiple users also should not be empty.
        Rule No 2: DOB cannot be empty
        Rule No 3: Address cannot be empty
        Rule No 4: Age cannot be below 18
         */
        UserRegisterCheck userRegisterCheck = new UserRegisterCheck();
        if(userRegisterCheck.nullEmail(user.getEmail()))
        {
            throw new EventListenerRegistrationException("EMAIL cannot be empty");
        }
        if(userRegisterCheck.emailIdExists(user.getEmail()))
        {
            throw new EventListenerRegistrationException("Email id is taken. Kindly mention another");
        } else if (userRegisterCheck.dobEmpty(user.getDob()))
        {
            throw new EventListenerRegistrationException("Date cannot be empty");
        }
        else if (userRegisterCheck.addressEmpty(user.getAddress()))
        {
            throw new EventListenerRegistrationException("Address cannot be empty");
        } else if (userRegisterCheck.isMinor(user.getDob()))
        {
            throw new EventListenerRegistrationException("Age is too low");
        } else
        {
            UserLoginDetails userLoginDetails = new UserLoginDetails();
            userLoginDetails.setAccountCreationDate(new Date()); // account creation date to current date & time.
            userLoginDetails.setAccountStatus('A'); //setting account to active.
            userLoginDetails.setLoginStatus(false); //we just registering not logging in hence false
            user.setUserLoginDetails(userLoginDetails);
            umr.save(user);
            return user;
        }
    }
}
