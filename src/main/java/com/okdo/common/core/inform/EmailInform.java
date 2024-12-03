package com.okdo.common.core.inform;

import com.okdo.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailInform implements Inform {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String defaultMailSender;


    @Override
    public void sendMessage(InformContext informContext) throws Exception{
        try {
            mailSender.send(buildMessage(informContext));
            log.info("send to: {}, email: {}", informContext.getReceiver(),
                    String.format("[title: %s, content: \n%s]",
                            informContext.getTitle(), informContext.getContent()));
        } catch (MessagingException e) {
            log.error("send email to {} fail, the reason is {}",
                    informContext.getReceiver(), e.getMessage());
            throw e;
        }
    }

    private MimeMessage buildMessage(InformContext informContext) throws Exception {
        checkInformContext(informContext);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        helper.setFrom(informContext.getSender());
        helper.setTo(informContext.getReceiver());
        helper.setSubject(informContext.getTitle());
        helper.setText(informContext.getContent(), true);
        return mimeMessage;
    }

    private void checkInformContext(InformContext informContext) throws Exception{
        String sender = informContext.getSender();
        if (StringUtils.isEmpty(sender)) {
            sender = defaultMailSender;
            informContext.setSender(defaultMailSender);
        }
        String receiver = informContext.getReceiver();
        String contentType = informContext.getContentType();
        for (String str : Arrays.asList(sender, receiver, contentType)) {
            if (StringUtils.isEmpty(str))
                throw new Exception(String.format("%s is null.", str));
        }
        String content = null;
        switch (contentType) {
            case ContentType.HTML:
                content = htmlContent(informContext);
                informContext.setContent(content);
                break;
            case ContentType.TEXT:
                content = informContext.getContent();
                break;
            default:
                throw new Exception(String.format("unSupport content type.", contentType));
        }
    }

    private String htmlContent(InformContext informContext) throws Exception{
        String contextFilePath = informContext.getContentFilePath();
        Context context = new Context();
        context.setVariables(informContext.extra);
        return templateEngine.process(contextFilePath, context);
    }

}
