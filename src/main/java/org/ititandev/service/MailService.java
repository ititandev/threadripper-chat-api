package org.ititandev.service;

import org.ititandev.config.Config;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class MailService {
	public static void sendMail(String to, String subject, String body) {
		System.out.println("to:" + to);
		System.out.println("subject:" + subject);
//		System.out.println("body:" + body);
		Email email = EmailBuilder.startingBlank()
				.from("Threadripper Chat System", Config.getConfig("mail.username") + "@gmail.com").to("User", to)
				.withSubject(subject).withHTMLText(body).buildEmail();

		Mailer mailer = MailerBuilder
				.withSMTPServer("smtp.gmail.com", 587, Config.getConfig("mail.username"),
						Config.getConfig("mail.password"))
				.withTransportStrategy(TransportStrategy.SMTP_TLS).buildMailer();

		mailer.sendMail(email);
	}
}
