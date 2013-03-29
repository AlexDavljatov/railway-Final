package org.jboss.as.quickstarts.kitchensink_ear.model.datamodel;

import org.jboss.as.quickstarts.kitchensink_ear.dto.StationDTO;
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
public class StationDataModel extends ListDataModel<StationDTO> implements SelectableDataModel<StationDTO>{

    public StationDataModel() {
    }

    public StationDataModel(List<StationDTO> list) {
        super(list);
    }

    @Override
    public Object getRowKey(StationDTO stationDTO) {
        return stationDTO.getName();
    }

    @Override
    public StationDTO getRowData(String s) {
        List<StationDTO> stations = (List<StationDTO>) getWrappedData();
        for (StationDTO stationDTO: stations){
            if (stationDTO.getName().equals(s)) return  stationDTO;
        }
        return null;
    }
}
