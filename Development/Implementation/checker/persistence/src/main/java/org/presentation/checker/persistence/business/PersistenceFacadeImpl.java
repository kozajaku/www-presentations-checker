package org.presentation.checker.persistence.business;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.presentation.checker.persistence.integration.CheckupDAO;
import org.presentation.checker.persistence.integration.DomainDAO;
import org.presentation.checker.persistence.integration.LoginDAO;
import org.presentation.checker.persistence.integration.UserDAO;
import org.presentation.checker.persistence.model.Checkup;
import org.presentation.checker.persistence.model.Login;
import org.presentation.checker.persistence.model.User;

/**
 *
 * @author radio.koza
 */
@Local(PersistentFacade.class)
@Stateless
public class PersistentFacadeImpl implements PersistentFacade {

    @Inject
    private UserDAO userDAO;
    @Inject
    private LoginDAO loginDAO;
    @Inject
    private CheckupDAO checkupDAO;
    @Inject
    private DomainDAO domainDAO;
    @Resource
    private EJBContext context;

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    @Override
    public boolean createNewUser(String email, String pass, String name, String surname) {
        try {
            //generate new random salt
            SecureRandom random = new SecureRandom();
            byte[] saltBytes = new byte[32];
            random.nextBytes(saltBytes);
            //generate byte array from passed password
            byte[] passBytes = pass.getBytes(Charset.forName("UTF-8"));
            //setup hashing algorithm
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(saltBytes);
            sha.update(passBytes);
            //create new user
            User user = new User();
            user.setEmail(email);
            user.setRegistrationDate(new Date());
            user.setSalt(bytesToHex(saltBytes));
            user.setName(name);
            user.setSurname(surname);
            user.setPassword(bytesToHex(sha.digest()));//hashed password
            //try to persist new user
            try {
                userDAO.create(user);
            } catch (Exception ex){
                //exception during creation
                context.setRollbackOnly();//rollback transaction
                return false;
            }
            return true;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PersistentFacadeImpl.class.getName()).log(Level.SEVERE, null, ex);
            assert false;
            return false;//to have compiler happy
        }
    }

    @Override
    public void createNewCheckup(Checkup checkup) {
        checkupDAO.create(checkup);
    }

    @Override
    public void addUserLogin(User user, String address) {
        Login login = new Login();
        login.setAddress(address);
        login.setUser(user);
        login.setTime(new Date());
        loginDAO.create(login);
    }

    @Override
    public void editUser(User user) {
        userDAO.update(user);
    }

    @Override
    public User findUser(Integer userId) {
        return userDAO.find(userId);
    }

    @Override
    public User findUser(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public List<Checkup> findUserCheckings(User user) {
        return checkupDAO.findAllUserChecks(user.getIdUser());
    }

    @Override
    public Checkup findCheckup(Integer checkId) {
        return checkupDAO.find(checkId);
    }

    @Override
    public void updateCheckup(Checkup checkup) {
        checkupDAO.update(checkup);
    }

    @Override
    public List<Login> findUserLogins(User user) {
        return loginDAO.findAllUserLogins(user.getIdUser());
    }

    @Override
    public Login findLastUserLogin(User user) {
        return loginDAO.findLastUserLogin(user.getIdUser());
    }

}
