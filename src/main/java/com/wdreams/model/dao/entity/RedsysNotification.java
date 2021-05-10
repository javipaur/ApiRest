package com.wdreams.model.dao.entity;

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "redsysnotification")
@DataTransferObject
public class RedsysNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Column(name = "amount")
    private String Amount = "";
    @Column(name = "authorisationCode")
    private String AuthorisationCode = "";
    @Column(name = "cardCountry")
    private String CardCountry = "";
    @Column(name = "cardType")
    private String CardType = "";
    @Column(name = "consumerLanguage")
    private String ConsumerLanguage = "";
    @Column(name = "currency")
    private String Currency = "";
    @Column(name = "date")
    private String Date = "";
    @Column(name = "hour")
    private String Hour = "";
    @Column(name = "merchantCode")
    private String MerchantCode = "";
    @Column(name = "merchantData")
    private String MerchantData = "";
    @Column(name = "orders")
    private String Order = "";
    @Column(name = "response")
    private String Response = "";
    @Column(name = "securePayment")
    private String SecurePayment = "";
    @Column(name = "signature")
    private String Signature = "";
    @Column(name = "terminal")
    private String Terminal = "";
    @Column(name = "transactionType")
    private String TransactionType = "";
    @Column(name = "epochTime")
    private String EpochTime = "0";



    public RedsysNotification(String amount, String authorisationCode,
                              String card_Country, String card_Type,
                              String consumerLanguage, String currency,
                              String date, String hour, String merchantCode,
                              String merchantData, String order,
                              String response, String securePayment,
                              String signature, String terminal,
                              String transactionType, Long epochTime) {
        Amount = amount;
        AuthorisationCode = authorisationCode;
        CardCountry = card_Country;
        CardType = card_Type;
        ConsumerLanguage = consumerLanguage;
        Currency = currency;
        Date = date;
        Hour = hour;
        MerchantCode = merchantCode;
        MerchantData = merchantData;
        Order = order;
        Response = response;
        SecurePayment = securePayment;
        Signature = signature;
        Terminal = terminal;
        TransactionType = transactionType;
        EpochTime = epochTime.toString();
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getAuthorisationCode() {
        return AuthorisationCode;
    }

    public void setAuthorisationCode(String authorisationCode) {
        AuthorisationCode = authorisationCode;
    }

    public String getCardCountry() {
        return CardCountry;
    }

    public void setCardCountry(String cardCountry) {
        CardCountry = cardCountry;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getConsumerLanguage() {
        return ConsumerLanguage;
    }

    public void setConsumerLanguage(String consumerLanguage) {
        ConsumerLanguage = consumerLanguage;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getMerchantCode() {
        return MerchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        MerchantCode = merchantCode;
    }

    public String getMerchantData() {
        return MerchantData;
    }

    public void setMerchantData(String merchantData) {
        MerchantData = merchantData;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getSecurePayment() {
        return SecurePayment;
    }

    public void setSecurePayment(String securePayment) {
        SecurePayment = securePayment;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getTerminal() {
        return Terminal;
    }

    public void setTerminal(String terminal) {
        Terminal = terminal;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String transactionType) {
        TransactionType = transactionType;
    }

    public String getEpochTime() {
        return EpochTime.toString();
    }

    public void setEpochTime(Long epochTime) {
        EpochTime = epochTime.toString();
    }
}
