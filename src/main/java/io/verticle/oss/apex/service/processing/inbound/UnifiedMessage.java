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

package io.verticle.oss.apex.service.processing.inbound;

import java.util.HashMap;
import java.util.Map;

/**
 * Unifies all incoming message formats and their versions into an internal representation.
 * @author Jens Saade
 */
public class UnifiedMessage {

    int fromMessageFormatVersion;
    String domain;
    String qualifier;
    String partiallyQualifiedMetricIdent;
    String collectorIdent;
    String createdAtCollector;

    Map<String, Object> fields = new HashMap<>();


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public int getFromMessageFormatVersion() {
        return fromMessageFormatVersion;
    }

    public void setFromMessageFormatVersion(int fromMessageFormatVersion) {
        this.fromMessageFormatVersion = fromMessageFormatVersion;
    }

    public String getPartiallyQualifiedMetricIdent() {
        return partiallyQualifiedMetricIdent;
    }

    public void setPartialQualifiedMetricIdent(String partiallyQualifiedMetricIdent) {
        this.partiallyQualifiedMetricIdent = partiallyQualifiedMetricIdent;
    }

    public String getCollectorIdent() {
        return collectorIdent;
    }

    public void setCollectorIdent(String collectorIdent) {
        this.collectorIdent = collectorIdent;
    }

    public String getCreatedAtCollector() {
        return createdAtCollector;
    }

    public void setCreatedAtCollector(String createdAtCollector) {
        this.createdAtCollector = createdAtCollector;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }
}
