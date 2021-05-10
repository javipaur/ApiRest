/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;


import com.wdreams.model.dao.Dao;
import com.wdreams.model.dao.entity.AppConfig;
import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author victor
 */
public class Email {

    private static Logger loggerBack = Logger.getLogger("ciudadanaErrorBack");

    public static boolean success;
    private String adress;
    private String subject;
    private String text;
    private List<String> adjuntos;
    private static Logger logger = Logger.getLogger("ciudadanaErrorBack");

    private List<String> adjuntosRutaAbsoluta;

    private Email() {
    }

    private Boolean esEmpresa = false;

    private Boolean copiaAyuntamiento = false;

    private Email(String adress, String subject, String text) {
        this.adress = adress;
        this.subject = subject;
        this.text = text;
    }

    private Email(String adress, String subject, String text, Boolean esEmpresa) {
        this.adress = adress;
        this.subject = subject;
        this.text = text;
        this.esEmpresa = esEmpresa;
    }

    public Email(String adress, String subject, String text, List<String> adjuntos) {
        this.adress = adress;
        this.subject = subject;
        this.text = text;
        this.adjuntos = adjuntos;
    }

    public static Email generaEmail(String adress, String subject, String text) {
        return new Email(adress, subject, text);
    }

    public static Email generaEmailEmpresa(String adress, String subject, String text) {
        return new Email(adress, subject, text, true);
    }

    public static Email generarEmailEnviable(Enviable enviable, Dao dao) throws FileNotFoundException, IOException {
        return new Email(enviable.getEmailEnviable(dao), enviable.getSubject(dao), enviable.getText(dao), enviable.getAdjuntos(dao));
    }

    public static Email generaEmailAdjuntos(String adress, String subject, String text, List<String> adjuntos) {
        Email email = new Email(adress, subject, text);
        email.adjuntos = adjuntos;
        return email;
    }

    public static Email generaEmailAdjuntosLiquidacion(String adress, String subject, String text, List<String> adjuntos) {
        Email email = new Email(adress, subject, text);
        email.adjuntosRutaAbsoluta = adjuntos;
        email.copiaAyuntamiento = true;
        return email;
    }

    public void enviarComo(DatosEmail d, Dao dao) {
        if (!d.getPassword().equalsIgnoreCase("_NULL_")) {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", d.getHost());
            properties.put("mail.smtp.starttls.enable", "false");
            properties.put("mail.smtp.port", d.getPort());

            properties.put("mail.smtp.socketFactory.class",d.getSmtp());//"javax.net.ssl.SSLSocketFactory"
            properties.put("mail.smtp.socketFactory.fallback","false");
            
            properties.put("mail.smtp.mail.sender", d.getFrom());
            properties.put("mail.smtp.user", d.getUser());
            properties.put("mail.smtp.password", d.getPassword());
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.timeout", "5000");
            properties.put("mail.smtp.connectiontimeout", "5000");

            Session session = Session.getDefaultInstance(properties);
            this.enviarMail(session, properties, dao);
        } else {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "false");
            properties.put("mail.smtp.starttls.enable", "false");
            properties.put("mail.smtp.host", d.getHost());
            properties.put("mail.smtp.port", d.getPort());
            properties.put("mail.smtp.mail.sender", d.getFrom());

            properties.put("mail.smtp.timeout", "5000");
            properties.put("mail.smtp.connectiontimeout", "5000");
            Session session = Session.getDefaultInstance(properties);
            this.enviarMail(session, properties, dao);
        }
    }

    private void enviarMail(Session session, Properties properties, Dao dao) {
        try {
            MimeMessage message = new MimeMessage(session);
            MimeMultipart multipart = new MimeMultipart("related");
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender"), dao.getAppConfigId(AppConfig.ALIAS_EMAIL).getValor()));
            if (!adress.isEmpty()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(adress));
            }
            try {
                List<String> copiasOcultas =
                        Arrays.asList((dao.getAppConfigId(AppConfig.USERS_EMAIL_COPIAS_OCULTAS).getValor()).split(";"));
                for (String mail : copiasOcultas) {
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mail));
                }
                if(esEmpresa != null && esEmpresa)
                {
                    List<String> copiasOcultasEmpresa =
                            Arrays.asList((dao.getAppConfigId(AppConfig.USERS_EMAIL_COPIAS_OCULTAS_EMPRESA).getValor()).split(";"));
                    for (String mail : copiasOcultasEmpresa) {
                        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mail));
                    }
                }
                if(copiaAyuntamiento != null && copiaAyuntamiento)
                {
                    List<String> copiaAyuntamiento =
                            Arrays.asList((dao.getAppConfigId(AppConfig.EMAIL_AYUNTAMIENTO).getValor()).split(";"));
                    for (String mail : copiaAyuntamiento) {
                        message.addRecipient(Message.RecipientType.CC, new InternetAddress(mail));
                    }
                }
            } catch (Exception j) {
                LogUtils.escribeLog(logger, "enviarMail.addCopiasOcultas", j);
            }


            message.setSubject(subject);
            MimeBodyPart bp = new MimeBodyPart();
            bp.setContent(text, "text/html; charset=UTF-8");
            multipart.addBodyPart(bp);
            if (adjuntos != null && adjuntos.size() > 0) {
                for (String adjunto : adjuntos) {
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    try {
                        messageBodyPart.attachFile(dao.getAppConfigId(AppConfig.CARPETA_ADJUNTOS_NOTIFICACION).getValor() + adjunto + ".pdf");
                    } catch (IOException ex) {
                        LogUtils.escribeLog(logger, "enviarMail", ex);
                    }
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            if (adjuntosRutaAbsoluta != null && adjuntosRutaAbsoluta.size() > 0) {
                for (String adjunto : adjuntosRutaAbsoluta) {
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    try {
                        messageBodyPart.attachFile(adjunto);
                    } catch (IOException ex) {
                        LogUtils.escribeLog(logger, "enviarMail", ex);
                    }
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            message.setContent(multipart);

            if (!dao.getAppConfigId(AppConfig.PASS_EMAIL).getValor().equalsIgnoreCase("_NULL_")) {
                Transport t = session.getTransport("smtp");
                t.connect((String) properties.get("mail.smtp.user"), (String) properties.get("mail.smtp.password"));
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            } else {
                Transport t = session.getTransport("smtp");
                t.connect();
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            }

        } catch (MessagingException me) {
            LogUtils.escribeLog(loggerBack, "Email.enviarMail", me);
            //LogUtils.escribeLog(logger, "Email.enviarMail", me);
            this.success = false;
            return;
        } catch (UnsupportedEncodingException ex) {
            LogUtils.escribeLog(loggerBack, "Email.enviarMail", ex);
            java.util.logging.Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            this.success = false;
            return;
        }
        this.success = true;
    }

    public void estadisticasEnviarComo(DatosEmail d, Dao dao) {
        if (!d.getPassword().equalsIgnoreCase("_NULL_")) {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", d.getHost());
            properties.put("mail.smtp.starttls.enable", "false");
            properties.put("mail.smtp.port", d.getPort());

            properties.put("mail.smtp.socketFactory.class",d.getSmtp());//"javax.net.ssl.SSLSocketFactory"
            properties.put("mail.smtp.socketFactory.fallback","false");

            properties.put("mail.smtp.mail.sender", d.getFrom());
            properties.put("mail.smtp.user", d.getUser());
            properties.put("mail.smtp.password", d.getPassword());
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.timeout", "5000");
            properties.put("mail.smtp.connectiontimeout", "5000");

            Session session = Session.getDefaultInstance(properties);
            this.enviarMailEstadisticas(session, properties, dao);
        } else {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "false");
            properties.put("mail.smtp.starttls.enable", "false");
            properties.put("mail.smtp.host", d.getHost());
            properties.put("mail.smtp.port", d.getPort());
            properties.put("mail.smtp.mail.sender", d.getFrom());

            properties.put("mail.smtp.timeout", "5000");
            properties.put("mail.smtp.connectiontimeout", "5000");
            Session session = Session.getDefaultInstance(properties);
            this.enviarMailEstadisticas(session, properties, dao);
        }
    }

    private void enviarMailEstadisticas(Session session, Properties properties, Dao dao) {
        try {
            MimeMessage message = new MimeMessage(session);
            MimeMultipart multipart = new MimeMultipart("related");
            message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender"), dao.getAppConfigId(AppConfig.ALIAS_EMAIL).getValor()));
            if (!adress.isEmpty()) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(adress));
            }
            try {
                List<String> copiasOcultas =
                        Arrays.asList((dao.getAppConfigId(AppConfig.EMAIL_CCO_ESTADISTICAS).getValor()).split(";"));
                for (String mail : copiasOcultas) {
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mail));
                }
//                if(esEmpresa != null && esEmpresa)
//                {
//                    List<String> copiasOcultasEmpresa =
//                            Arrays.asList((dao.getAppConfigId(AppConfig.USERS_EMAIL_COPIAS_OCULTAS_EMPRESA).getValor()).split(";"));
//                    for (String mail : copiasOcultasEmpresa) {
//                        message.addRecipient(Message.RecipientType.BCC, new InternetAddress(mail));
//                    }
//                }
//                if(copiaAyuntamiento != null && copiaAyuntamiento)
//                {
//                    List<String> copiaAyuntamiento =
//                            Arrays.asList((dao.getAppConfigId(AppConfig.EMAIL_AYUNTAMIENTO).getValor()).split(";"));
//                    for (String mail : copiaAyuntamiento) {
//                        message.addRecipient(Message.RecipientType.CC, new InternetAddress(mail));
//                    }
//                }
            } catch (Exception j) {
                LogUtils.escribeLog(logger, "enviarMailEstadisticas.addCopiasOcultas", j);
            }


            message.setSubject(subject);
            MimeBodyPart bp = new MimeBodyPart();
            bp.setContent(text, "text/html; charset=UTF-8");
            multipart.addBodyPart(bp);
            if (adjuntos != null && adjuntos.size() > 0) {
                for (String adjunto : adjuntos) {
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    try {
                        messageBodyPart.attachFile(dao.getAppConfigId(AppConfig.CARPETA_ADJUNTOS_NOTIFICACION).getValor() + adjunto + ".pdf");
                    } catch (IOException ex) {
                        LogUtils.escribeLog(logger, "enviarMailEstadisticas", ex);
                    }
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            if (adjuntosRutaAbsoluta != null && adjuntosRutaAbsoluta.size() > 0) {
                for (String adjunto : adjuntosRutaAbsoluta) {
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    try {
                        messageBodyPart.attachFile(adjunto);
                    } catch (IOException ex) {
                        LogUtils.escribeLog(logger, "enviarMailEstadisticas", ex);
                    }
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            message.setContent(multipart);

            if (!dao.getAppConfigId(AppConfig.PASS_EMAIL).getValor().equalsIgnoreCase("_NULL_")) {
                Transport t = session.getTransport("smtp");
                t.connect((String) properties.get("mail.smtp.user"), (String) properties.get("mail.smtp.password"));
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            } else {
                Transport t = session.getTransport("smtp");
                t.connect();
                t.sendMessage(message, message.getAllRecipients());
                t.close();
            }

        } catch (MessagingException me) {
            LogUtils.escribeLog(loggerBack, "Email.enviarMailEstadisticas", me);
            //LogUtils.escribeLog(logger, "Email.enviarMail", me);
            this.success = false;
            return;
        } catch (UnsupportedEncodingException ex) {
            LogUtils.escribeLog(loggerBack, "Email.enviarMailEstadisticas", ex);
            java.util.logging.Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
            this.success = false;
            return;
        }
        this.success = true;
    }
}
