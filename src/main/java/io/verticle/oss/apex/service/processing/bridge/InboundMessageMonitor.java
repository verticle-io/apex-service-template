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

package io.verticle.oss.apex.service.processing.bridge;

import io.verticle.oss.apex.service.processing.inbound.UnifiedMessage;
import io.verticle.oss.apex.service.processing.stats.CollectorStats;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Notifies all {@link InboundMetricMessageListener} instances when new MetricMessages arrive.
 * @author Jens Saade
 */
@Service("InboundMessageMonitor")
public class InboundMessageMonitor {

    Set<InboundCollectorHeartbeatListener> collectorHeartbeatListeners = new HashSet<>();
    Set<InboundMetricMessageListener> metricMessageListeners = new HashSet<>();

    public void registerMetricMessageListener(InboundMetricMessageListener listener){
        metricMessageListeners.add(listener);
    }

    public void registerCollectorHeartbeatListener(InboundCollectorHeartbeatListener listener){
        collectorHeartbeatListeners.add(listener);
    }


    @Async
    public void notifyMetricMessageListeners(UnifiedMessage message) {
        metricMessageListeners.forEach(listener -> {
            listener.notify(message);
        });
    }

    @Async
    public void notifyCollectorHeartbeatListeners(CollectorStats collectorStats){
        collectorHeartbeatListeners.forEach(listener -> {
            listener.notify(collectorStats);
        });
    }
}
