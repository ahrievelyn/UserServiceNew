package userservice.org.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.org.Service.DeactivateOrBan.DeactivateOrBanImplementation;

@RestController
@RequestMapping("user/")
public class DeactivateOrBanController{
    @Autowired
    DeactivateOrBanImplementation dorb;
    //Deactivate takes user email id and password. And its responsibility is to set that user to inactive.
    //URL : http://localhost:8080/user/Deactivate/{email}/{password}/
    @GetMapping("Deactivate/{email}/{password}/")
    public ResponseEntity<Object> Deactivate(@PathVariable("email") String email, @PathVariable("password") String password) throws Exception {
        try
        {
            //Try to deactivate account. If return is true, then setting is successful
            if(dorb.deactivateAccount(email, password))
            {
                return new ResponseEntity<Object>("ACCOUNT SET TO INACTIVE SUCCESSFULLY",HttpStatus.OK);
            }
            //If return is false, the action is unsuccessful
            else
            {
                return new ResponseEntity<Object>("ACCOUNT UNABLE TO SET TO INACTIVE",HttpStatus.NOT_IMPLEMENTED);
            }
        }
        //Catch the exception and send the client error message mentioning the problem.
        catch (Exception e)
        {
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_IMPLEMENTED);
        }
    }

    //Ban requires a user email id and password with ban permission. It takes the email id of which user to set to banned state.
    //URL : http://localhost:8080/user/Ban/{email}/{password}/{email2}
    @GetMapping("/Ban/{email}/{password}/{email2}")
    public ResponseEntity<Object> Ban(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("email2") String email2) throws Exception {
        try
        {
            //Try to ban the account. If result is true it is successful
            if(dorb.banAccount(email, password, email2))
            {
                return new ResponseEntity<Object>("ACCOUNT SET TO BANNED SUCCESSFULLY",HttpStatus.OK);
            }
            //If the above condition result is false then it is unsuccessful
            else
            {
                return new ResponseEntity<Object>("ACCOUNT UNABLE TO SET TO BAN",HttpStatus.NOT_IMPLEMENTED);
            }
        }
        //What ever exceptions may be thrown by the functionality is caught and sent to user for further understanding.
        catch (Exception e)
        {
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
