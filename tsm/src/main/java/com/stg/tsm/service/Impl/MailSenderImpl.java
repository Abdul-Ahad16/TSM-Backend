package com.stg.tsm.service.Impl;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.stg.tsm.Literals;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.stg.tsm.entity.ParameterMaster;
import com.stg.tsm.exception.TsmException;
import com.stg.tsm.repos.ParameterMasterRepository;
import com.stg.tsm.service.MailSender;

@Service
public class MailSenderImpl implements MailSender {

	@Value("${spring.mail.password}")
	private String RESET_123;
	@Value("${login-user-name}")
	private String FORUMS;

	@Value("${spring.mail.host}")
	private String EXCHANGE;
	@Value("${spring.mail.port}")
	private String PORT;

	@Value("${sendMail}")
	private String SEND_EMAIL;

	public static final String MAIL_SMTP_HOST = "mail.smtp.host";
	public static final String MAIL_SMTP_PORT = "mail.smtp.port";
	public static final String MAIL_USER = "mail.user";
	public static final String MAIL_PASSWORD = "mail.password";
	public static final String MAIL_DEBUG = "mail.debug";
	public static final String TRUE = "true";
	public static final String MAIL_DESCRIPTION_PROJECT_MANAGER = "MAIL_DESCRIPTION_PROJECT_MANAGER";
	public static final String MAIL_SUBJECT_PROJECT_MANAGER = "MAIL_SUBJECT_PROJECT_MANAGER";
	public static final String HI = "Hi ";
	private static final String THANKS_STGIT_TEAM = ".\r\n \r\nThanks, \r\nSTGIT Team";
	private static final String TEXT_PLAIN_CHARSET_UTF_8 = "text/plain; charset=UTF-8";
	private static final String MESSAGE_SENT_SUCCESSFULLY = "Message sent successfully!";
	private static final String FAILED_TO_SEND_MESSAGE = "failed to send message";
	private static final String THANKS_STGIT_TEAM1 = "\r\nThanks, \r\nSTGIT Team";
	private static final String MAIL_SUBJECT_EMPLOYEE_PASSWORD_CHANGED = "MAIL_SUBJECT_EMPLOYEE_PASSWORD_CHANGED";
	private static final String MAIL_DESCRIPTION_EMPLOYEE_PASSWORD_CHANGED = "MAIL_DESCRIPTION_EMPLOYEE_PASSWORD_CHANGED";
	private static final String UTF_8 = "UTF-8";
	private static final String ENTER_KEY = ",\r\n";
	
	@Autowired
	private ParameterMasterRepository parameterMasterRepository;
	
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;

	private MimeMessage getMessage(String toEmail) throws MessagingException {
		Properties properties = System.getProperties();
		properties.setProperty(MAIL_SMTP_HOST, EXCHANGE);
		properties.setProperty(MAIL_SMTP_PORT, PORT);
		properties.setProperty(MAIL_USER, FORUMS);
		properties.setProperty(MAIL_PASSWORD, RESET_123);
		properties.setProperty(MAIL_DEBUG, TRUE);
		Session mailSession = Session.getDefaultInstance(properties);
		MimeMessage mimeMessage = new MimeMessage(mailSession);
//        MimeMessage mimeMessage = emailSender.createMimeMessage();

		mimeMessage.setHeader("Content-Type", "text/html; charset=UTF-8");
		mimeMessage.setFrom(new InternetAddress(SEND_EMAIL));
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

		return mimeMessage;
	}

	@Override
	public String sendEmailProjectManger(String userName, String userEmail, String projectMangerName, String adminEmail)
			throws TsmException {
		ParameterMaster mailDescription = parameterMasterRepository
				.findByparameterDescription(MAIL_DESCRIPTION_PROJECT_MANAGER);

		ParameterMaster mailSubject = parameterMasterRepository
				.findByparameterDescription(MAIL_SUBJECT_PROJECT_MANAGER);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(SEND_EMAIL);
//        message.setTo(adminEmail);
//
//        message.setSubject(mailSubject.getParameterDetails().get(0).getDescription());
//
        String textDesc = mailDescription.getParameterDetails().get(0).getDescription();

        textDesc = String.format(textDesc, userName, userEmail);

		String out = "";
		try {
			MimeMessage mimeMessage = getMessage(adminEmail);

			Context context = new Context();
			context.setVariable("name", userName);
			context.setVariable("Email", userEmail);
			context.setVariable("textDesc", textDesc);

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			final String template = "userCreationForAdmin-template";
			String html = templateEngine.process(template, context);

			helper.setTo(adminEmail);
			helper.setText(html, true);
			helper.setFrom(SEND_EMAIL);
			helper.setSubject(mailSubject.getParameterDetails().get(0).getDescription());
//			String textDesc = mailDescription.getParameterDetails().get(0).getDescription();
//            textDesc = String.format(textDesc, userName, userEmail);

			
//

//            
//            mimeMessage.setSubject(mailSubject.getParameterDetails().get(0).getDescription(), UTF_8);
//            mimeMessage.setContent(HI + "\r\n" +
//                    "\r\n" + textDesc + "\r\n   Empolyee Name : " + userName + "\r\n   Employee Email : " + userEmail
//                    + THANKS_STGIT_TEAM1, TEXT_PLAIN_CHARSET_UTF_8);
			Transport.send(mimeMessage, FORUMS, RESET_123);
			out = MESSAGE_SENT_SUCCESSFULLY;

			//emailSender.send(mimeMessage);
		} catch (Exception e) {
			out = FAILED_TO_SEND_MESSAGE;
		}

		return out;
	}

	@Override
	public String sendEmailUser(String toEmail, String adminMail, String employeeName) throws TsmException {
		ParameterMaster mailDescription = parameterMasterRepository
				.findByparameterDescription("MAIL_DESCRIPTION_EMPLOYEE");

		ParameterMaster mailSubject = parameterMasterRepository.findByparameterDescription("MAIL_SUBJECT_EMPLOYEE");

		String textDesc = mailDescription.getParameterDetails().get(0).getDescription();

		textDesc = String.format(textDesc, adminMail);

		String out = "";
		try {
			MimeMessage mimeMessage = getMessage(toEmail);
			Context context = new Context();
			context.setVariable("name", employeeName);
			context.setVariable("textDesc", textDesc);
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			final String template = "plainEmail-remplate";
			String html = templateEngine.process(template, context);

			helper.setTo(toEmail);
			helper.setText(html, true);
			helper.setFrom(SEND_EMAIL);
			helper.setSubject(mailSubject.getParameterDetails().get(0).getDescription());
			
			
			Transport.send(mimeMessage, FORUMS, RESET_123);
			//emailSender.send(mimeMessage);
			out = MESSAGE_SENT_SUCCESSFULLY;
		} catch (Exception e) {
			out = FAILED_TO_SEND_MESSAGE;

		}

		return out;
	}

	@NotNull

	@Override
	public String sendEmailUserForPasswordReset(String adminEmail, String adminName, String employeeEmail,
			String employeeName) throws TsmException {
		ParameterMaster mailDescription = parameterMasterRepository
				.findByparameterDescription("MAIL_DES_EMP_RESETPASSWORD");

		ParameterMaster mailSubject = parameterMasterRepository
				.findByparameterDescription("MAIL_SUB_EMP_RESETPASSWORD");

		String textDesc = mailDescription.getParameterDetails().get(0).getDescription();

		textDesc = String.format(textDesc, adminEmail);

		String out = "";
		try {
			MimeMessage mimeMessage = getMessage(employeeEmail);
			
			Context context = new Context();
			context.setVariable("name", employeeName);
			context.setVariable("textDesc", textDesc);
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			final String template = "plainEmail-remplate";
			String html = templateEngine.process(template, context);

			helper.setTo(employeeEmail);
			helper.setText(html, true);
			helper.setFrom(SEND_EMAIL);
			helper.setSubject(mailSubject.getParameterDetails().get(0).getDescription());
			
//			mimeMessage.setSubject(mailSubject.getParameterDetails().get(0).getDescription(), UTF_8);
//			mimeMessage.setContent(HI + employeeName + ENTER_KEY + "\r\n" + textDesc + THANKS_STGIT_TEAM,
//					TEXT_PLAIN_CHARSET_UTF_8);
			Transport.send(mimeMessage, FORUMS, RESET_123);
			out = MESSAGE_SENT_SUCCESSFULLY;
		} catch (Exception e) {
			out = FAILED_TO_SEND_MESSAGE;
		}

		return out;
	}

	@Override
	public String sendEmailProjectMangerForPasswordReset(String adminEmail, String adminName, String employeeEmail,
			String employeeName) throws TsmException {
		ParameterMaster mailDescription = parameterMasterRepository
				.findByparameterDescription("MAIL_DES_PM_PASSWORD_RESET");

		ParameterMaster mailSubject = parameterMasterRepository
				.findByparameterDescription("MAIL_SUB_PM_PASSWORD_RESET");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(SEND_EMAIL);
		message.setTo(adminEmail);

		message.setSubject(mailSubject.getParameterDetails().get(0).getDescription());

		String textDesc = mailDescription.getParameterDetails().get(0).getDescription();

		textDesc = String.format(textDesc, employeeName, employeeEmail);

		String out = "";
		try {
			MimeMessage mimeMessage = getMessage(adminEmail);

			Context context = new Context();
			
			context.setVariable("name", adminName);
			context.setVariable("nameEmp", employeeName);
			context.setVariable("Email", employeeEmail);
			context.setVariable("textDesc", textDesc);

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			final String template = "passwordResetToAdmin-template";
			String html = templateEngine.process(template, context);

			helper.setTo(adminEmail);
			helper.setText(html, true);
			helper.setFrom(SEND_EMAIL);
			helper.setSubject(mailSubject.getParameterDetails().get(0).getDescription());
			
			mimeMessage.setSubject(mailSubject.getParameterDetails().get(0).getDescription(), UTF_8);
//			mimeMessage.setContent(
//					HI + adminName + ENTER_KEY + "\r\n" + textDesc + "\r\n" + "\r\nEmpolyee Name: " + employeeName
//							+ "\r\nEmployee Email: " + employeeEmail + "\r\n" + THANKS_STGIT_TEAM1,
//					TEXT_PLAIN_CHARSET_UTF_8);
			Transport.send(mimeMessage, FORUMS, RESET_123);
			out = MESSAGE_SENT_SUCCESSFULLY;
		} catch (Exception e) {
			out = FAILED_TO_SEND_MESSAGE;
		}

		return out;
	}

	@Override
	public String sendEmailUserPasswordChanged(String userEmail, String userName) throws TsmException {
		ParameterMaster mailDescription = parameterMasterRepository
				.findByparameterDescription(MAIL_DESCRIPTION_EMPLOYEE_PASSWORD_CHANGED);

		ParameterMaster mailSubject = parameterMasterRepository
				.findByparameterDescription(MAIL_SUBJECT_EMPLOYEE_PASSWORD_CHANGED);

		String textDesc = mailDescription.getParameterDetails().get(0).getDescription();

		textDesc = String.format(textDesc);

		String out = "";
		try {
			MimeMessage mimeMessage = getMessage(userEmail);
			
			Context context = new Context();
			context.setVariable("name", userName);
			context.setVariable("textDesc", textDesc);
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

			final String template = "plainEmail-remplate";
			String html = templateEngine.process(template, context);

			helper.setTo(userEmail);
			helper.setText(html, true);
			helper.setFrom(SEND_EMAIL);
			helper.setSubject(mailSubject.getParameterDetails().get(0).getDescription());
			
//			mimeMessage.setSubject(mailSubject.getParameterDetails().get(0).getDescription(), UTF_8);
//			mimeMessage.setContent(HI + userName + ENTER_KEY + "\r\n" + textDesc + THANKS_STGIT_TEAM,
//					TEXT_PLAIN_CHARSET_UTF_8);
			Transport.send(mimeMessage, FORUMS, RESET_123);
			out = MESSAGE_SENT_SUCCESSFULLY;
		} catch (Exception e) {
			out = FAILED_TO_SEND_MESSAGE;
		}

		return out;
	}

}
