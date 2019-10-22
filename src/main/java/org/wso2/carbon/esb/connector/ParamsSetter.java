/*
*  Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.carbon.esb.connector;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

public class ParamsSetter extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        Object expand = getParameter(messageContext, "expand");
        Object dependency = getParameter(messageContext, "dependency");
        Object fields = getParameter(messageContext, "fields");
        Object onlyData = getParameter(messageContext, "onlyData");

        String queryParams = messageContext.getProperty(HCMConnectorConstants.QUERY_PARAMS_PROPERTY) != null ?
                messageContext.getProperty(HCMConnectorConstants.QUERY_PARAMS_PROPERTY).toString() :
                "";
        StringBuilder params = new StringBuilder(queryParams);

        if (expand != null) {
            params = Utils.appendQueryParam(params, "expand=", expand.toString());
        }

        if (dependency != null) {
            params = Utils.appendQueryParam(params, "dependency=", dependency.toString());
        }

        if (fields != null) {
            params = Utils.appendQueryParam(params, "fields=", fields.toString());
        }

        if (onlyData != null) {
            params = Utils.appendQueryParam(params, "onlyData=", onlyData.toString());
        }

        messageContext.setProperty(HCMConnectorConstants.QUERY_PARAMS_PROPERTY, params.toString());
    }
}