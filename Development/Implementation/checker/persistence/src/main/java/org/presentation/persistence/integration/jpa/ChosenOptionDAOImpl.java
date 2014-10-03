package org.presentation.persistence.integration.jpa;

import java.util.List;
import javax.ejb.EJBException;
import javax.enterprise.context.Dependent;
import javax.persistence.TypedQuery;
import org.presentation.persistence.integration.ChosenOptionDAO;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.ChosenOption;

/**
 *
 * @author radio.koza
 */
@Dependent
public class ChosenOptionDAOImpl extends AbstractDAOImpl implements ChosenOptionDAO {

    @Override
    public List<ChosenOption> findAllCheckOptions(Integer checkupId) {
        TypedQuery<ChosenOption> q = getEntityManager().createNamedQuery("ChosenOption.findAllInCheckup", ChosenOption.class);
        q.setParameter("checkupId", checkupId);
        return q.getResultList();
    }

    @Override
    public void addOptionToCheckup(ChosenOption option, Integer checkupId) {
        ChosenOption tmp = getEntityManager().find(ChosenOption.class, option.getIdOption());
        Checkup checkup = getEntityManager().find(Checkup.class, checkupId);
        if (checkup == null) {
            throw new EJBException("Checkup with specified id was not found!");
        }
        if (tmp == null) {
            getEntityManager().persist(option);
            tmp = option;
        }
        checkup.getOptionList().add(tmp);
        tmp.getCheckupList().add(checkup);//not necessery for persisting to database - checking object corruption
    }

}
