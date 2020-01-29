package io.multiversum.wallet.console;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

import org.bouncycastle.util.encoders.Hex;
import org.jline.reader.LineReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.multiversum.core.services.WalletService;

import java.text.ParseException;

@ShellComponent
@ShellCommandGroup("wallet")
public class WalletCommands {

	private static final Logger log = LoggerFactory.getLogger(WalletCommands.class);

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	LineReader reader;

	@Autowired
	WalletService walletService;

	@ShellMethod(value = "Restore wallet from keywords", key = "restore-from-keywords")
	public void restore_from_keywords() throws NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchProviderException, JsonGenerationException, JsonMappingException, IOException {
		
		String keywords = this.reader.readLine(" please write keyword in correct order > ");

		Character mask = '*';

		String passphrase = this.reader.readLine(" Insert passphrase > ", mask);
		
		this.walletService.restoreFromKeyWords(keywords, passphrase);

		
		System.out.println(String.format("Wallet restored"));

		System.out.println(String.format("Address: %s ", this.walletService.getAddress()));
		
		System.out.println(String.format("Private Key: %s", Hex.toHexString(this.walletService.getPrivateKey().getEncoded())));
		System.out.println(String.format("Public Key: %s", Hex.toHexString(this.walletService.getPublicKey().getEncoded())));
		
		this.walletService.saveWallet();
	}

	@ShellMethod("Generate a new wallet	")
	public void generate(@ShellOption(value = "language", defaultValue = "english") String language)
			throws InvalidAlgorithmParameterException, NoSuchProviderException, NoSuchAlgorithmException, IOException,
			InvalidKeySpecException {

		Character mask = '*';

		String passphrase = this.reader.readLine(" please enter passphrase > ", mask);

		String keywords = walletService.generateMnemonic();

		this.walletService.restoreFromKeyWords(keywords, passphrase);

		System.out.println("Plese save your's keywords for future restore");
		
		System.out.println(String.format("Keywords: %s", keywords));
		System.out.println(String.format("Address: %s", walletService.getAddress()));
		
		System.out.println(String.format("Private Key: %s", Hex.toHexString(this.walletService.getPrivateKey().getEncoded())));
		System.out.println(String.format("Public Key: %s", Hex.toHexString(this.walletService.getPublicKey().getEncoded())));

		
		this.walletService.saveWallet();

	}

	@ShellMethod("Show my address")
	public void myAddress() {
		System.out.println("Current wallet address: " + this.walletService.getAddress());
	}
}
