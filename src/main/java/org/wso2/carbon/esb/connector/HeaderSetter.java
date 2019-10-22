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
import org.apache.synapse.SynapseException;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

import java.util.Map;

public class HeaderSetter extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        Map headers = (Map) ((Axis2MessageContext) messageContext).getAxis2MessageContext().
                getProperty(org.apache.axis2.context.MessageContext.TRANSPORT_HEADERS);

        Object basicAuthToken = messageContext.getProperty(HCMConnectorConstants.BASIC_AUTH_TOKEN);
        Object bearerToken = messageContext.getProperty(HCMConnectorConstants.BEARER_TOKEN);
        Object restVersion = messageContext.getProperty(HCMConnectorConstants.REST_VERSION);

        //set Authorization header
        if (basicAuthToken != null) {
            headers.put("Authorization", "Basic " + basicAuthToken.toString());
        } else if (bearerToken != null) {
            headers.put("Authorization", "Bearer " + bearerToken.toString());
        } else {
            log.error("Authorization Not Set!");
            throw new SynapseException("Authorization Parameters not set");
        }

        //set Rest framework version header
        if (restVersion != null) {
            headers.put(HCMConnectorConstants.REST_FRAMEWORK_PROPERTY, restVersion.toString());
        }
    }
}
