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

import io.verticle.oss.apex.service.processing.inbound.messageformats.MessageFormat;
import io.verticle.oss.apex.service.processing.inbound.messageformats.MessageFormatException;
import io.verticle.oss.apex.service.processing.inbound.messageformats.MessageFormatV1;
import io.verticle.oss.apex.service.processing.inbound.messageformats.MessageFormatV2;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * transforms incoming messages into the current internal format.
 * @author Jens Saade
 */
public class MessageFormatHandler {


    public UnifiedMessage transform(String message) throws MessageFormatException{

        JSONObject jsonObject = new JSONObject(message);
        JSONObject meta = jsonObject.getJSONObject("meta");
        JSONObject metrics = jsonObject.getJSONObject("metrics");

        Map<String, Object> metaMap = new HashMap<>();
        Iterator<String> metaIterator = meta.keys();
        while (metaIterator.hasNext()){
            String key = metaIterator.next();
            metaMap.put(key, meta.get(key));
        }

        Map<String, Object> fieldMap = new HashMap<>();
        Iterator<String> fieldIterator = metrics.keys();
        while (fieldIterator.hasNext()){
            String key = fieldIterator.next();
            fieldMap.put(key, metrics.get(key));
        }

        MessageFormat messageFormat = null;
        if (metaMap.get("domain") != null && metaMap.get("qualifier") != null)
            messageFormat = new MessageFormatV2();
        else if (metaMap.get("index") != null)
            messageFormat = new MessageFormatV1();
        else
            throw new MessageFormatException("Message format cannot be identified");

        // set the @timestamp index field in proper format
        fieldMap.put("@timestamp", IndexCompatibleDateTimeFormatter.getIsoFormattedDateString(new Date(meta.getLong("@timestamp"))));

        UnifiedMessage unifiedMessage = messageFormat.unify(metaMap, fieldMap);


        return unifiedMessage;

    }







}
