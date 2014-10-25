package org.presentation.presentation.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.presentation.model.Header;

/**
 * JSF converter - converts http headers newline-separated list - List of Header
 * objects
 *
 * @author petrof
 * @version $Id: $Id
 */
@FacesConverter("HttpHeadersConverter")
public class HttpHeadersConverter implements Converter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        List<Header> headers = new ArrayList<>();

        String[] lines = value.split("\\r?\\n");

        for (String line : lines) {
            String[] splitted = line.split(":", 2);
            if (splitted.length == 2) {
                headers.add(new Header(splitted[0].trim(), splitted[1].trim()));
            }
        }

        return headers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        StringBuilder sb = new StringBuilder();

        for (Iterator it = ((List) value).iterator(); it.hasNext();) {
            Header h = (Header) it.next();
            sb.append(h.getKey()).append(": ").append(h.getValue());
            if (it.hasNext()) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

}
