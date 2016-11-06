/*
 *    Copyright 2016 Jens Saade <jens@verticle.io>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.verticle.oss.apex.service.transport.inbound.rest;

import io.verticle.oss.apex.service.processing.bridge.InboundMessageMonitor;
import io.verticle.oss.apex.service.processing.inbound.MessageFormatHandler;
import io.verticle.oss.apex.service.processing.inbound.UnifiedMessage;
import io.verticle.oss.apex.service.processing.inbound.messageformats.MessageFormatException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 *
 * @author Jens Saade
 */
@RestController

@RequestMapping("/collector")
public class InboundMetricsController {

    private static Logger logger = LoggerFactory.getLogger(InboundMetricsController.class);

    @Autowired
    InboundMessageMonitor inboundMessageMonitor;

    @RequestMapping(value = "/metrics", method = RequestMethod.POST)
    public void consumeMetricMessage(@RequestBody  String message){

        JSONObject jsonObject = new JSONObject(message);
        JSONObject meta = jsonObject.getJSONObject("meta");

        MessageFormatHandler handler = new MessageFormatHandler();
        try {
            UnifiedMessage unifiedMessage = handler.transform(message);
            inboundMessageMonitor.notifyMetricMessageListeners(unifiedMessage);
        } catch (MessageFormatException e) {
           logger.error("Format exception: ", e);
        }
    }

}
