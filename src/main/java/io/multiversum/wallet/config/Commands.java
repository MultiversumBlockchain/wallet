package io.multiversum.wallet.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component()
public class Commands {


    private static final Logger log = LoggerFactory.getLogger(Commands.class);

    @Bean
    public PromptProvider myPromptProvider() {
        return () -> new AttributedString("wallet:> ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}
