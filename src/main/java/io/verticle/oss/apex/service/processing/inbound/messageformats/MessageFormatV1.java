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

package io.verticle.oss.apex.service.processing.inbound.messageformats;

import io.verticle.oss.apex.service.processing.inbound.UnifiedMessage;

import java.util.Date;
import java.util.Map;

/**
 *
 * @author Jens Saade
 */
public class MessageFormatV1 extends AbstractMessageFormat implements MessageFormat{

    final int version = 1;

    public final static String FIELD_INDEX = "index";
    public final static String FIELD_TYPE = "type";
    public final static String FIELD_TIMESTAMP = "@timestamp";

    public final static String FIELD_SRC_IP = "src_ip";
    public final static String FIELD_SRC_MACHASH = "src_mac_hash";
    public final static String FIELD_SRC_COLLECTORID = "src_collect";
    public final static String FIELD_SRC_CLASS = "src_class";



    @Override
    public int getVersion() {
        return version;
    }

    @Override
    public UnifiedMessage unify(Map<String, Object> metaMap, Map<String, Object> fieldMap) {
        UnifiedMessage um = new UnifiedMessage();

        um.setCollectorIdent((String) fieldMap.get(FIELD_SRC_COLLECTORID));
        um.setCreatedAtCollector(getIsoFormattedDateString(new Date((long) metaMap.get(FIELD_TIMESTAMP))));
        um.setFromMessageFormatVersion(getVersion());
        um.setFields(fieldMap);

        um.setDomain("generic");
        um.setQualifier(metaMap.get(FIELD_INDEX) + "." + metaMap.get(FIELD_TYPE));
        um.setPartialQualifiedMetricIdent(um.getDomain() + "." + um.getQualifier());

        return um;
    }


}
