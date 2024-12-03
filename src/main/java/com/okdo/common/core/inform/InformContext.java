package com.okdo.common.core.inform;

import com.okdo.domain.User;
import lombok.Builder;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class InformContext {
    public String InformType;
    public String title;
    public String content;
    public String contentType;
    public String contentFilePath;
    public String sender;
    public String receiver;
    public Map<String, Object> extra;

    public Map<String, Object> addExtra(String key, Object value){
        if (extra == null)
            extra = new HashMap<>();
        extra.put(key, value);
        return extra;
    };
}
