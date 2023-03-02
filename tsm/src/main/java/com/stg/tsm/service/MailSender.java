package com.stg.tsm.service;

import com.stg.tsm.exception.TsmException;

public interface MailSender {
    
    String sendEmailProjectManger(String userName, String userEmail, String ProjectMangerName, String adminEmail) throws TsmException;
    
    String sendEmailUser(String toEmail, String adminMail, String employeeName) throws TsmException;
    
    String sendEmailUserForPasswordReset(String adminEmail, String adminName, String employeeEmail, String employeeName) throws TsmException;
    
    String sendEmailProjectMangerForPasswordReset(String adminEmail, String adminName, String employeeEmail, String employeeName) throws TsmException;
    
    String sendEmailUserPasswordChanged(String userEmail , String userName) throws TsmException;
}
