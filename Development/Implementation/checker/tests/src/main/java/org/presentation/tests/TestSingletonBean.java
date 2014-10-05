package org.presentation.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.presentation.kernel.CheckRequestReceiver;
import org.presentation.kernel.CheckingRequest;
import org.presentation.model.Domain;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.persistence.business.PersistenceFacade;
import org.presentation.utils.OptionContainer;

/**
 *
 * @author radio.koza
 */
@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class TestSingletonBean {
    private static final Logger LOG = Logger.getLogger(TestSingletonBean.class.getName());
    
    @EJB
    private PersistenceFacade persistenceFacade;
    
    @Resource
    private SessionContext context;
    
    @EJB
    private CheckRequestReceiver receiver;
    
    @PostConstruct
    public void init(){
        //call test asynchronously
        context.getBusinessObject(TestSingletonBean.class).testAsync();
    }
    
    @Asynchronous
    public void testAsync(){
        LOG.log(Level.INFO, "Waiting few seconds to test start");
        try {
            //wait few seconds
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestSingletonBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOG.log(Level.INFO, "Test started!");
        test();
    }
    
    private void test(){
        CheckingRequest req = new CheckingRequest();
        req.setMaxDepth(10);
        req.setPageLimit(20);
        req.setRequestInterval(2000);
        req.setStartingPoint(new LinkURL("http://www.webzdarma.cz/"));
        OptionContainer optCon = new OptionContainer();
//        optCon.addOption("css");
//        optCon.addOption("html");
        req.setChosenOptions(optCon);
//        List<Header> headers = new ArrayList<>();
//        headers.add(new Header("neco", "roflmao"));
//        headers.add(new Header("neco2", "haf"));
//        headers.add(new Header("Cookie", "blem=3"));
//        req.setHeaders(headers);
        List<Domain> domains = new ArrayList<>();
        domains.add(new Domain("webzdarma.cz"));
        req.setAllowedDomains(domains);
        receiver.addNewCheckingRequest("guest@guest.cz", req);
    }
    
}
