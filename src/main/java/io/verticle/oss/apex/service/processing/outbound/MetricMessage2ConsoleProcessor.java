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

package io.verticle.oss.apex.service.processing.outbound;

import io.verticle.oss.apex.service.processing.bridge.InboundMessageMonitor;
import io.verticle.oss.apex.service.processing.bridge.InboundMetricMessageListener;
import io.verticle.oss.apex.service.processing.inbound.UnifiedMessage;
import io.verticle.oss.apex.service.util.ANSI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * A simple {@link InboundMetricMessageListener} listener that outputs received MetricMessages to the console.
 * @author Jens Saade
 */
@Component
public class MetricMessage2ConsoleProcessor implements InboundMetricMessageListener {

    String format1 = "%-60s %s %s %n";
    String format2 = "%-40s%s%n";


    @Autowired
    InboundMessageMonitor messageMonitor;

    @PostConstruct
    private void registerListener(){
        messageMonitor.registerMetricMessageListener(this);
    }


    @Override
    public void notify(UnifiedMessage message) {

        System.out.println("==========");
        System.out.printf(format1, ANSI.ANSI_BLUE + message.getPartiallyQualifiedMetricIdent() + ANSI.ANSI_RESET, message.getCollectorIdent(), message.getCreatedAtCollector());
        System.out.println("----------");
        message.getFields().forEach((key, value) ->
            System.out.printf(format2, ANSI.ANSI_BLUE + key + ANSI.ANSI_RESET + ":", value)
        );
        System.out.println("==========");

    }
}
