package org.presentation.persistence.business;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.logging.Message;
import org.presentation.persistence.integration.CheckupDAO;
import org.presentation.persistence.integration.ChosenOptionDAO;
import org.presentation.persistence.integration.DomainDAO;
import org.presentation.persistence.integration.GraphDAO;
import org.presentation.persistence.integration.HeaderEntityDAO;
import org.presentation.persistence.integration.LoginDAO;
import org.presentation.persistence.integration.MessageEntityDAO;
import org.presentation.persistence.integration.UserDAO;
import org.presentation.persistence.model.CheckState;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.ChosenOption;
import org.presentation.persistence.model.Graph;
import org.presentation.persistence.model.HeaderEntity;
import org.presentation.persistence.model.Login;
import org.presentation.persistence.model.MessageEntity;
import org.presentation.persistence.model.User;

/**
 *
 * @author radio.koza
 */
@Local(PersistenceFacade.class)
@Stateless
public class PersistenceFacadeImpl implements PersistenceFacade {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;//only for debugging purposes

    //injected necessary DAO objects
    @Inject
    private CheckupDAO checkupDAO;
    @Inject
    private ChosenOptionDAO chosenOptionDAO;
    @Inject
    private DomainDAO domainDAO;
    @Inject
    private GraphDAO graphDAO;
    @Inject
    private HeaderEntityDAO headerDAO;
    @Inject
    private LoginDAO loginDAO;
    @Inject
    private MessageEntityDAO messageDAO;
    @Inject
    private UserDAO userDAO;

    //Context of EJB session bean to control transaction management - ability to rollback transactions
    @Resource
    private EJBContext context;

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Method used for converting array of bytes to HEX representation in String
     *
     * @param bytes Array of bytes to be converted
     * @return Representation of bytes in hex
     */
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
                LOG.log(Level.INFO, "New user {0} created", user.getEmail());
            } catch (Exception ex) {
                //exception during creation
                LOG.log(Level.INFO, "Unable to create new user - exception", ex);
                context.setRollbackOnly();//rollback transaction
                return false;
            }
            return true;
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, null, ex);
            assert false : "Hashing algorithm is not specified correctly";
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
    public User findUser(String email) {
        return userDAO.find(email);
    }

    @Override
    public List<Checkup> findUserCheckings(User user) {
        return checkupDAO.findAllUserChecks(user.getEmail());
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
        return loginDAO.findAllUserLogins(user.getEmail());
    }

    @Override
    public Login findLastUserLogin(User user) {
        return loginDAO.findLastUserLogin(user.getEmail());
    }

    @Override
    public Checkup findCheckupInitializedInputs(Integer checkId) {
        Checkup checkup = checkupDAO.find(checkId);
        if (checkup == null) {
            return null;//checkup was not found
        }
        //initialize lazy loaded input lists by calling size method
        checkup.getDomainList().size();
        checkup.getHeaderList().size();
        checkup.getOptionList().size();
        //return resulting checkup
        return checkup;
    }

    @Override
    public void addHeadersToCheckup(Checkup checkup, List<Header> headers) {
        //at first find managed version of checkup
        Checkup tmp = checkupDAO.find(checkup.getIdCheckup());
        if (tmp == null) {
            //persist new checkup
            tmp = checkup;
            checkupDAO.create(checkup);
        }
        //convert headers to entity versions and persist
        //note that headers is owning side of jpa bidirectional relationship
        HeaderEntity tmpHeader;
        for (Header i : headers) {
            tmpHeader = HeaderEntity.convert(i);
            tmpHeader.setCheckup(tmp);
            headerDAO.create(tmpHeader);
        }
    }

    @Override
    public List<Header> findCheckupHeaders(Checkup checkup) {
        List<HeaderEntity> entityHeaders = headerDAO.findAllCheckHeaders(checkup.getIdCheckup());
        List<Header> res = new ArrayList<>(entityHeaders.size());
        for (HeaderEntity i : entityHeaders) {
            res.add(HeaderEntity.convert(i));
        }
        return res;
    }

    @Override
    public void addOptionsToCheckup(Checkup checkup, List<String> options) {
        //at first find managed version of checkup
        Checkup tmp = checkupDAO.find(checkup.getIdCheckup());
        if (tmp == null) {
            //persist new checkup
            tmp = checkup;
            checkupDAO.create(checkup);
        }
        //convert options to entity alternatives and persist
        for (String i : options) {
            ChosenOption opt = ChosenOption.convert(i);
            //important! in this ManyToMany bidirectional relationship the Checkup is owning side!
            chosenOptionDAO.addOptionToCheckup(opt, tmp.getIdCheckup());
        }
    }

    @Override
    public List<ChosenOption> findCheckupOptions(Checkup checkup) {
        return chosenOptionDAO.findAllCheckOptions(checkup.getIdCheckup());
    }

    @Override
    public void addDomainsToCheckup(Checkup checkup, List<Domain> domains) {
        //at first find managed version of checkup
        Checkup tmp = checkupDAO.find(checkup.getIdCheckup());
        if (tmp == null) {
            //persist new checkup
            tmp = checkup;
            checkupDAO.create(checkup);
        }
        //convert domain to its entity mapping alternative
        for (Domain i : domains) {
            org.presentation.persistence.model.Domain entity = org.presentation.persistence.model.Domain.convert(i);
            entity.setChecking(tmp);
            domainDAO.create(entity);
        }
    }

    @Override
    public List<Domain> findCheckupDomains(Checkup checkup) {
        List<org.presentation.persistence.model.Domain> domains = domainDAO.findAllCheckDomains(checkup.getIdCheckup());
        List<org.presentation.model.Domain> res = new ArrayList<>(domains.size());
        for (org.presentation.persistence.model.Domain i : domains) {
            res.add(org.presentation.persistence.model.Domain.convert(i));
        }
        return res;
    }

    @Override
    public void addGraphsToCheckup(Checkup checkup, List<Graph> graphs) {
        //at first find managed version of checkup
        Checkup tmp = checkupDAO.find(checkup.getIdCheckup());
        if (tmp == null) {
            //persist new checkup
            tmp = checkup;
            checkupDAO.create(checkup);
        }
        //persist graphs to database
        for (Graph i : graphs) {
            i.setCheckup(checkup);
            graphDAO.create(i);
        }
    }

    @Override
    public List<Graph> findCheckupGraphs(Checkup checkup) {
        return graphDAO.findAllCheckGraphs(checkup.getIdCheckup());
    }

    @Override
    public List<String> listGraphTypes(Checkup checkup){
        return graphDAO.listGraphTypes(checkup.getIdCheckup());
    }
    
    @Override
    public Graph findGraphByGraphType(Checkup checkup, String graphType){
        return graphDAO.findGraphByGraphType(checkup.getIdCheckup(), graphType);
    }
    
    @Override
    public void addMessagesToCheckup(Checkup checkup, List<Message> messages, String resource) {
        //at first find managed version of checkup
        Checkup tmp = checkupDAO.find(checkup.getIdCheckup());
        if (tmp == null) {
            //persist new checkup
            tmp = checkup;
            checkupDAO.create(checkup);
        }
        //convert messages to its entity mapping alternative
        for (Message i : messages) {
            MessageEntity entity = MessageEntity.convert(i);
            entity.setCheckup(tmp);
            entity.setResource(resource);
            messageDAO.create(entity);
        }
    }

    @Override
    public List<Message> findCheckupMessages(Checkup checkup) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessages(checkup.getIdCheckup());
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities) {
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public List<String> findCheckupMessageResources(Checkup checkup) {
        return messageDAO.findAllCheckMessageResources(checkup.getIdCheckup());
    }

    @Override
    public List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessagesFromResource(checkup.getIdCheckup(), resource);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities) {
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public void flush() {
        checkupDAO.flush();
    }

    @Override
    public List<Checkup> findNotEndedCheckupsStateOrdered() {
        CheckState[] notEndedStates = {CheckState.CREATED, CheckState.PENDING, CheckState.CHECKING};
        List<Checkup> checkups = checkupDAO.findAllWithState(notEndedStates);
        Collections.sort(checkups, new Comparator<Checkup>() {

            private int toComparableValue(CheckState state) {
                int res = 10;//initialization
                switch (state) {
                    case CHECKING:
                        res = 0;
                        break;
                    case PENDING:
                        res = 1;
                        break;
                    case CREATED:
                        res = 2;
                        break;
                }
                return res;
            }

            @Override
            public int compare(Checkup o1, Checkup o2) {
                int a, b;
                a = toComparableValue(o1.getState());
                b = toComparableValue(o2.getState());
                return a - b;
            }
        });
        return checkups;
    }

    @Override
    public Checkup fetchNewlyCreatedCheckup() {
        List<Checkup> checkups = checkupDAO.findAllWithState(new CheckState[]{CheckState.CREATED});
        if (checkups.isEmpty()){
            return null;
        }
        Checkup res = checkups.get(0);
        res.setState(CheckState.PENDING);
        checkupDAO.update(res);
        return res;
    }

    @Override
    public List<Checkup> findUserCheckings(User user, int offset, int count) {
        return checkupDAO.findAllUserChecks(user.getEmail(), offset, count);
    }

    @Override
    public List<Message> findCheckupMessages(Checkup checkup, int offset, int count) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessages(checkup.getIdCheckup(), offset, count);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities) {
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public List<Message> findCheckupMessagesWithResource(Checkup checkup, String resource, int offset, int count) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessagesFromResource(checkup.getIdCheckup(), resource, offset, count);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities) {
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public List<Message> findCheckupMessagesWithResources(Checkup checkup, List<String> resources) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessagesFromResources(checkup.getIdCheckup(), resources);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities){
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public List<Message> findCheckupMessagesWithResources(Checkup checkup, List<String> resources, int offset, int count) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessagesFromResources(checkup.getIdCheckup(), resources, offset, count);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities){
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public int countUserCheckups(User user) {
        return checkupDAO.countUserChecks(user.getEmail());
    }

    @Override
    public int countCheckupMessages(Checkup checkup) {
        return messageDAO.countCheckMessages(checkup.getIdCheckup());
    }

    @Override
    public int countCheckupMessagesWithResources(Checkup checkup, List<String> resources) {
        return messageDAO.countCheckMessagesFromResources(checkup.getIdCheckup(), resources);
    }

    @Override
    public List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessagesByDiscriminators(checkup.getIdCheckup(), discriminators);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities){
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public List<Message> findCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators, int offset, int count) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessagesByDiscriminators(checkup.getIdCheckup(), discriminators, offset, count);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities){
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessagesByResourcesDiscriminators(checkup.getIdCheckup(), resources, discriminators);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities){
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public List<Message> findCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators, int offset, int count) {
        List<MessageEntity> entities = messageDAO.findAllCheckMessagesByResourcesDiscriminators(checkup.getIdCheckup(), resources, discriminators, offset, count);
        List<Message> messages = new ArrayList<>(entities.size());
        for (MessageEntity i : entities){
            messages.add(MessageEntity.convert(i));
        }
        return messages;
    }

    @Override
    public int countCheckupMessagesWithDiscriminators(Checkup checkup, List<String> discriminators) {
        return messageDAO.countCheckMessagesByDiscriminators(checkup.getIdCheckup(), discriminators);
    }

    @Override
    public int countCheckupMessagesWithResourcesDiscriminators(Checkup checkup, List<String> resources, List<String> discriminators) {
        return messageDAO.countCheckMessagesByResourcesDiscriminators(checkup.getIdCheckup(), resources, discriminators);
    }

}
