package org.jboss.as.quickstarts.kitchensink_ear.model.datamodel;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 22.03.13
 * Time: 22:54
 * To change this template use File | Settings | File Templates.
 */

import org.jboss.as.quickstarts.kitchensink_ear.model.TrainModel;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 22.03.13
 * Time: 22:16
 * To change this template use File | Settings | File Templates.
 */
public class TrainDataModel extends ListDataModel<TrainModel> implements SelectableDataModel<TrainModel> {

    public TrainDataModel() {
    }

    public TrainDataModel(List<TrainModel> list) {
        super(list);
    }

    @Override
    public Object getRowKey(TrainModel trainDTO) {
        return trainDTO.getNumber();
    }

    @Override
    public TrainModel getRowData(String s) {
        List<TrainModel> trains = (List<TrainModel>) getWrappedData();
        for (TrainModel trainModel: trains){
            if (trainModel.getNumber().equals(s)) return  trainModel;
        }
        return null;
    }
}

