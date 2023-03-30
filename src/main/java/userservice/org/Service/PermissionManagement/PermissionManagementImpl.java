package userservice.org.Service.PermissionManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userservice.org.Model.AdminUser;
import userservice.org.Model.UserModel;
import userservice.org.Repository.UserModelRepo;

import java.util.List;
import java.util.Objects;
@Service
public class PermissionManagementImpl implements  PermissionManagement{
    @Autowired
    UserModelRepo umr;
    @Override
    public Boolean addPermission(String email, String Password, String email2, List<String> permissions) throws Exception {
        //For adding or removing permissions I have hard coded an admin user with admin email id and admin password.
        AdminUser au = new AdminUser();
        //If the admin user email & password credentials match.
        if(Objects.equals(email,au.getEmail()) && Objects.equals(Password,au.getPassword()))
        {
            //check if user to whom you want to add permissions exist
            UserModel um = umr.findByEmail(email2);
            if(um == null)
            {
                throw new Exception("Email Id does not exist");
            }
            else
            { //if the user does, add permissions.
                um.getPermissions().addAll(permissions);
                umr.save(um);
                return true;
            }
        }
        else
        {
            throw new Exception("AUTHENTICATION FAILED");
        }
    }
    @Override
    public Boolean removePermission(String email, String Password, String email2, List<String> permissions) throws Exception {
        AdminUser au = new AdminUser();
        //For adding or removing permissions I have hard coded an admin user with admin email id and admin password.
        //If the admin user email & password credentials match.
        if(Objects.equals(email,au.getEmail()) && Objects.equals(Password,au.getPassword()))
        {
            //check if user to whom you want to remove permissions exist
            UserModel um = umr.findByEmail(email2);
            if(um == null)
            {
                throw new Exception("Email Id does not exist");
            }
            else
            {
                //if the user does, remove permissions.
                um.getPermissions().removeAll(permissions);
                umr.save(um);
                return true;
            }
        }
        else
        {
            throw new Exception("AUTHENTICATION FAILED");
        }
    }
}
