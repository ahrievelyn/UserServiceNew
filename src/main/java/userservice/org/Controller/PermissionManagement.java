package userservice.org.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userservice.org.Service.PermissionManagement.PermissionManagementImpl;

import java.util.List;

@RestController
@RequestMapping("user/")
public class PermissionManagement {
    @Autowired
    PermissionManagementImpl pm;
    //This accepts email id and password of the user with add and remove privilege and adds or removes a list of permissions to a mentioned email.

    //URL : http://localhost:8080/user/Permissions/add/{email}/{password}/{email2}
    //body takes list of permissions.
    @PutMapping("Permissions/add/{email}/{password}/{email2}")
    public ResponseEntity<Object> addPermission(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("email2") String email2, @RequestBody List<String> perm)
    {
        try
        {
            if(pm.addPermission(email,password,email2,perm))
            {
                return new ResponseEntity<Object>("PERMISSIONS ADDED SUCCESSFULLY", HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<Object>("UNABLE TO ADD PERMISSIONS",HttpStatus.NOT_IMPLEMENTED);
            }
        }
        catch(Exception e)
        {
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_IMPLEMENTED);
        }
    }

    //URL : http://localhost:8080/user/Permission/remove/{email}/{password}/{email2}
    @PutMapping("Permissions/remove/{email}/{password}/{email2}")
    public ResponseEntity<Object> removePermission(@PathVariable("email") String email, @PathVariable("password") String password, @PathVariable("email2") String email2, @RequestBody List<String> perm)
    {
        try
        {
            if(pm.removePermission(email,password,email2,perm))
            {
                return new ResponseEntity<Object>("PERMISSIONS REMOVED SUCCESSFULLY", HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<Object>("UNABLE TO REMOVE PERMISSIONS",HttpStatus.NOT_IMPLEMENTED);
            }
        }
        catch(Exception e)
        {
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
