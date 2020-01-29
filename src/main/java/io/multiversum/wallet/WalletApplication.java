package io.multiversum.wallet;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import io.multiversum.core.conf.MTVCoreConfiguration;
import io.multiversum.core.services.SettingsService;
import io.multiversum.core.services.WalletService;

@SpringBootApplication(exclude = { WebMvcAutoConfiguration.class })
@ComponentScan(basePackages = { "io.multiversum.core.services", "io.multiversum.wallet.console", "io.multiversum.wallet.config" })
@Import({ MTVCoreConfiguration.class })
@Order(value = -2000)
public class WalletApplication implements ApplicationRunner {

	@Autowired
	SettingsService settingsService;

	@Autowired
	WalletService walletService;

	private static final Logger logger = LoggerFactory.getLogger(WalletApplication.class);

	public static void main(String... args) {

//        for(String arg:args) {
//            System.out.println(arg);
//        }

		SpringApplication.run(WalletApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (Objects.nonNull(walletService.getWalletFile())) {
			if (walletService.getWalletFile().exists()) {
				walletService.loadWallet();
			} else {
				logger.warn("Wallet not found. Create with command generate or restore");
			}
		}

//      logger.info("Application started with command-line arguments: {}", Arrays.toString(args.getSourceArgs()));
//		System.out.println("# NonOptionArgs: " + args.getNonOptionArgs().size());
//
//		System.out.println("NonOptionArgs:");
//		args.getNonOptionArgs().forEach(System.out::println);
//
//		System.out.println("# OptionArgs: " + args.getOptionNames().size());
//		System.out.println("OptionArgs:");
//
//		args.getOptionNames().forEach(optionName -> {
//			System.out.println(optionName + "=" + args.getOptionValues(optionName));
//		});
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
