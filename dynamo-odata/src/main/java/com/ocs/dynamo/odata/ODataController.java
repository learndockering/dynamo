package com.ocs.dynamo.odata;

import com.ocs.dynamo.domain.model.util.EntityModelUtil;
import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ODataController implements HttpRequestHandler {

    private Map<>

    @Override
    public void handleRequest(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        try {
            // create odata handler and configure it with CsdlEdmProvider and Processor
            OData odata = OData.newInstance();
            ServiceMetadata edm = odata.createServiceMetadata(new EdmProvider(), new ArrayList<EdmxReference>());
            ODataHttpHandler handler = odata.createHandler(edm);
            handler.register(new ODataEntityCollectionProcessor());
            // let the handler do the work
            handler.process(req, resp);
        } catch (RuntimeException e) {
            throw new ServletException(e);
        }
    }

    //Use this method to configure the OData service for your application
    protected void init(){

    }
}