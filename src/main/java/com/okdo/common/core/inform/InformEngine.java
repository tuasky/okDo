package com.okdo.common.core.inform;

import com.okdo.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InformEngine implements Inform {
    private static Map<String, Inform> informMap;
    private final EmailInform emailInform;

    @PostConstruct
    private void init() {
        informMap = new HashMap<>();
        informMap.put(InformType.EMAIL, emailInform);
    }

    public void sendMessage(String informType, String receiver, String title,
                            String content, String contentType, String contentFilePath) throws Exception {
        InformContext informContext = InformContext.builder()
                .InformType(informType)
                .receiver(receiver)
                .title(title)
                .content(content)
                .contentType(contentType)
                .contentFilePath(contentFilePath).build();
        this.sendMessage(informContext);
    }

    public void sendEmailMessage(String receiver, String title, String content) throws Exception {
        InformContext informContext = InformContext.builder()
                .InformType("email")
                .receiver(receiver)
                .title(title)
                .content(content)
                .contentType("text").build();
        this.sendMessage(informContext);
    }

    public void sendEmailHtmlMessage(String receiver, String title, String contentFilePath,
                                     Map<String, Object> bindingFields) throws Exception {
        InformContext informContext = InformContext.builder()
                .InformType("email")
                .receiver(receiver)
                .title(title)
                .contentType("html")
                .extra(bindingFields)
                .contentFilePath(contentFilePath).build();
        this.sendMessage(informContext);
    }

    public void sendEmailHtmlMessage(String receiver, String title, String contentFilePath,
                                     String... fields) throws Exception {
        if (fields.length % 2 != 0)
            throw new Exception("fields as (key, value) of map, must be even.");
        Map<String, Object> bindingFields = new HashMap<>();
        for (int i = 0; i < fields.length; i += 2) {
            bindingFields.put(fields[i], fields[i + 1]);
        }
        InformContext informContext = InformContext.builder()
                .InformType("email")
                .receiver(receiver)
                .title(title)
                .contentType("html")
                .extra(bindingFields)
                .contentFilePath(contentFilePath).build();
        this.sendMessage(informContext);
    }


    public void sendMessage(InformContext informContext) throws Exception {
        String informType = informContext.getInformType();
        verifyInformType(informType);
        informMap.get(informType).sendMessage(informContext);
    }

    private void verifyInformType(String informType) throws Exception {
        if (StringUtils.isEmpty(informType))
            throw new Exception("inform type is empty.");
        if (!informMap.containsKey(informType))
            throw new Exception(String
                    .format("inform type [%s] does not support.", informType));
    }
}
