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

package io.verticle.oss.apex.service.registries;

import io.verticle.oss.apex.service.processing.stats.CollectorStats;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * Registry saves the state of the collectors (updated on heartbeats received)
 * @author Jens Saade
 */
@Service("CollectorStateRegistry")
public class CollectorStateRegistry {

    private Map<String, CollectorStats> collectorStatsMap = new ConcurrentHashMap<>();

    public CollectorStats get(Object key) {
        CollectorStats stats = collectorStatsMap.get(key);
        if (stats == null){
            stats = new CollectorStats();
            stats.setCollectorIdent((String) key);
            collectorStatsMap.put(stats.getCollectorIdent(), stats);
        }

        return collectorStatsMap.get(key);
    }

    public CollectorStats put(String key, CollectorStats value) {
        return collectorStatsMap.put(key, value);
    }

    public Set<Map.Entry<String, CollectorStats>> entrySet() {
        return collectorStatsMap.entrySet();
    }

    public Collection<CollectorStats> values() {
        return collectorStatsMap.values();
    }

    public Set<String> keySet() {
        return collectorStatsMap.keySet();
    }

    public void forEach(BiConsumer<? super String, ? super CollectorStats> action) {
        collectorStatsMap.forEach(action);
    }

    public Map<String, CollectorStats> getStatsMap(){
        return collectorStatsMap;
    }
}
