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

public class FeedParamsSetter extends AbstractConnector {
    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        Object updatedMin = getParameter(messageContext, "updated-min");
        Object updatedMax = getParameter(messageContext, "updated-max");
        Object pageSize = getParameter(messageContext, "page-size");
        Object page = getParameter(messageContext, "page");

        StringBuilder queryParams = new StringBuilder();

        if (updatedMin != null) {
            queryParams = Utils.appendQueryParam(queryParams, "updated-min=", updatedMin.toString());
        }

        if (updatedMax != null) {
            queryParams = Utils.appendQueryParam(queryParams, "updated-max=", updatedMax.toString());
        }

        if (pageSize != null) {
            queryParams = Utils.appendQueryParam(queryParams, "page-size=", pageSize.toString());
        }

        if (page != null) {
            queryParams = Utils.appendQueryParam(queryParams, "page=", page.toString());
        }

        messageContext.setProperty(HCMConnectorConstants.QUERY_PARAMS_PROPERTY, queryParams.toString());
    }
}