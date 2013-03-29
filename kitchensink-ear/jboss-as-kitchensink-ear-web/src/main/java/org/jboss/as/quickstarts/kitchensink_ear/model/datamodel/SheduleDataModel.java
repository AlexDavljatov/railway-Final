package org.jboss.as.quickstarts.kitchensink_ear.model.datamodel;

import org.jboss.as.quickstarts.kitchensink_ear.dto.SheduleDTO;
import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;
import org.jboss.as.quickstarts.kitchensink_ear.model.SheduleModel;
import org.primefaces.model.SelectableDataModel;
import org.slf4j.LoggerFactory;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 24.03.13
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */
public class SheduleDataModel extends ListDataModel<SheduleModel> implements SelectableDataModel<SheduleModel> {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SheduleDataModel.class);

    public SheduleDataModel() {
    }

    public SheduleDataModel(List<SheduleModel> list) {
        super(list);
    }

    @Override
    public Object getRowKey(SheduleModel sheduleModel) {
//        log.info("onRowSelect " + sheduleModel.getUnique() + " " +sheduleModel.getTrainNumber());
        return sheduleModel.getUnique();
    }

    @Override
    public SheduleModel getRowData(String s) {
//        log.debug("getRowData " + s);
        List<SheduleModel> shedules = (List<SheduleModel>) getWrappedData();
//        log.info("getRowData " + shedules);
        for (SheduleModel sheduleModel: shedules){
//            log.info("rows " + sheduleModel.getUnique() + " " + s + " " + sheduleModel.getUnique().equals(s));
            if (sheduleModel.getUnique().equals(s)) return  sheduleModel;
        }
        return null;
    }
}
