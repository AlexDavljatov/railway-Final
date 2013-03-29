package org.jboss.as.quickstarts.kitchensink_ear.model.datamodel;

import org.jboss.as.quickstarts.kitchensink_ear.dto.TicketDTO;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 24.03.13
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public class TicketDataModel extends ListDataModel<TicketDTO> implements SelectableDataModel<TicketDTO> {

    public TicketDataModel() {
    }

    public TicketDataModel(List<TicketDTO> list) {
        super(list);
    }

    @Override
    public Object getRowKey(TicketDTO ticketDTO) {
        return String.valueOf(ticketDTO.getTrain().getNumber());
    }

    @Override
    public TicketDTO getRowData(String s) {
        List<TicketDTO> tickets = (List<TicketDTO>) getWrappedData();
        for (TicketDTO ticket: tickets){
            if (String.valueOf(ticket.getTrain().getNumber()).equals(s)) return ticket;
        }
        return null;
    }
}
