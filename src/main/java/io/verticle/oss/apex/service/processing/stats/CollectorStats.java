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

package io.verticle.oss.apex.service.processing.stats;


import org.joda.time.DateTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Tracks incoming metric message stats
 * @author Jens Saade
 */
public class CollectorStats {

    String collectorIdent;
    Map<String, Long> metricsReceived = new ConcurrentHashMap<>();
    DateTime lastHeartBeat;


    public long increaseMetricReceived(String metricFamilyIdent){
        Long count = metricsReceived.get(metricFamilyIdent);
        if (count == null){
            metricsReceived.put(metricFamilyIdent, 1L);
            count = metricsReceived.get(metricFamilyIdent);
        } else {
            count++;
            metricsReceived.put(metricFamilyIdent, count);
        }


        return count;
    }

    public Map<String, Long> getMetricsReceived() {
        return metricsReceived;
    }

    public void setMetricsReceived(Map<String, Long> metricsReceived) {
        this.metricsReceived = metricsReceived;
    }

    public DateTime getLastHeartBeat() {
        return lastHeartBeat;
    }

    public void setLastHeartBeat(DateTime lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }

    public String getCollectorIdent() {
        return collectorIdent;
    }

    public void setCollectorIdent(String collectorIdent) {
        this.collectorIdent = collectorIdent;
    }
}
