package userservice.org.Service.DeactivateOrBan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userservice.org.Model.AdminUser;
import userservice.org.Model.UserModel;
import userservice.org.Repository.UserModelRepo;

import java.util.Objects;

@Service
public class DeactivateOrBanImplementation implements DeactivateOrBan{

        @Autowired
        UserModelRepo umr;
        @Override
        public Boolean banAccount(String email, String password, String email2) throws Exception {
            //Check if both users exist. If neither of them exist by email then we cannot ban.
            UserModel au = umr.findByEmail(email);
            UserModel u = umr.findByEmail(email2);
            if(u==null)
            {
                throw new Exception("EMAIL Id does not exist");
            }
            else if(au==null)
            {
                throw new Exception("Admin user does not exist");
            }
            else
            {
                //Check if the given credentials user has ban privilege.
                if(au.getPermissions().contains("ban")) {
                    //If he has ban privilege then check credentials.
                    if (Objects.equals(au.getEmail(), email) && Objects.equals(au.getPasswordHash(), password)) {
                        //If the credentials match then save the account status as banned.
                        u.getUserLoginDetails().setAccountStatus('B');
                        umr.save(u);
                        return true;
                    }
                    else
                    {
                        throw new Exception("Authentication Failed");
                    }
                }
                else throw new Exception("Ban permission not allowed for user");
            }
        }
        @Override
        public Boolean deactivateAccount(String email, String password) throws Exception {
            //Check if user exists by id.
            UserModel u = umr.findByEmail(email);
            if(u==null)
            {
                throw new Exception("EMAIL Id does not exist");
            }
            else
            {
                //If user exists then check for credentials
                if(Objects.equals(u.getPasswordHash(),password)) {
                    //If credentials match set status as Inactive.
                    u.getUserLoginDetails().setAccountStatus('I');
                    umr.save(u);
                    return true;
                }
                else
                {
                    throw new Exception("Authentication Failed");
                }
            }
        }
    }
