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

import io.verticle.oss.apex.service.registries.CollectorStateRegistry;
import io.verticle.oss.apex.service.transport.HeartbeatMessage;
import io.verticle.oss.apex.service.util.ANSI;
import org.joda.time.DateTime;
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
@RequestMapping(value = "/collector")
public class InboundCollectorHeartbeatController {

    @Autowired
    CollectorStateRegistry collectorStateRegistry;

    @RequestMapping(value = "/heartbeat", method = RequestMethod.POST)
    public void consumeMetricMessage(@RequestBody HeartbeatMessage message){

        System.out.print(ANSI.ANSI_GREEN + "heartbeat " + ANSI.ANSI_RESET);
        message.getMeta().forEach((key, value) ->
                    System.out.print(value + " ")
        );
        System.out.println("");
        Date timestamp = new Date((Long) message.getMeta().get("@timestamp"));

        String collectorMacHash = (String) message.getMeta().get("src_mac_hash");
        String collectorIP = (String) message.getMeta().get("src_ip");
        String collectorIdent = (String) message.getMeta().get("src_collect");


        collectorStateRegistry.get(collectorIdent).setLastHeartBeat(new DateTime(timestamp));

    }

}
