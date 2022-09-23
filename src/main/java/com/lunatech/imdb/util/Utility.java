package com.lunatech.imdb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Log4j2
public class Utility {

    public static String generateReference() {
        Random rn = new Random();
        int digits = rn.nextInt(900) + 100;
        String ref = Integer.toString(digits) + System.currentTimeMillis();

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        return year+""+month+""+ref;
    }

    public static String getBufferedReaderContent(HttpResponse response) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null){
            result.append(line);
        }
        return result.toString();
    }

    public static String emptyToNull(String data){
        if(data == null)
            return null;

        if("".equals(data.trim()))
            return null;

        return data;
    }

    public static String nullToEmpty(String data){
        if(data == null)
            return "";

        return data;
    }

    public static Object stringToObject(String value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(value, Map.class);

        } catch (IOException ex) {
            log.error(ex);
        }
        return null;
    }

    public static String objectToJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error(ex);
        }
        return null;
    }

    public static String encodeUrl(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    public static String decodeUrl(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }

    public static int secondsToMinutes(int totalSecs) {
        return (totalSecs * 60) / 3600;
    }

    public static String formatDate(Date date, String format){

        if(date == null)
            return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date ISODateFormatter(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    public static LocalDateTime ISOLocalDateTimeFormatter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dateModified = date.replace(" ", "T");
        return LocalDateTime.parse(dateModified, formatter);
    }

    public static LocalDateTime ISOLocalDateTimeWithOffsetFormatter(String date) {
        String dateModified = date.replace(" ", "T");
        return OffsetDateTime.parse(dateModified)
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String getUTCTimestamp(int plusMinutes) {
        ZonedDateTime utc;

        if (plusMinutes > 0) {
            utc = ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(plusMinutes);
        } else {
            utc = ZonedDateTime.now(ZoneOffset.UTC);
        }

        return utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static void main(String[] args) throws ParseException {
        String date = LocalDateTime.now().toString();

        System.out.println(getUTCTimestamp(15));

        //System.out.println("ISODateFormatter: " + Utility.ISODateFormatter(date));
        //System.out.println("ISOLocalDateTimeFormatter: " + Utility.ISOLocalDateTimeFormatter(date));
        //System.out.println("ISOLocalDateTimeWithOffsetFormatter: " + Utility.ISOLocalDateTimeWithOffsetFormatter(date));
    }

}
