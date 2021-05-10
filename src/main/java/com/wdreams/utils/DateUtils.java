/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.springframework.validation.Errors;

/**
 *
 * @author victor
 */
public class DateUtils {

    public static SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat ddMMyyyyHHmmss = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat yyyyMMddHHmmss_conFormato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat hibernateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");

    public static String fechaActualDatepicker() throws ParseException {
        Date date = new Date();
        return ddMMyyyy.format(date);
    }

    public static Date fechaInicialFormulario(Date fechaInicial) throws ParseException {
        return ddMMyyyyHHmmss.parse(ddMMyyyy.format(fechaInicial) + " 00:00:00");
    }

    public static Date fechaFinalFormulario(Date fechaInicial) throws ParseException {
        return ddMMyyyyHHmmss.parse(ddMMyyyy.format(fechaInicial) + " 23:59:59");
    }

    public static Errors validarRangoFechas(Errors errors, String fechaInicialNombre, String fechaFinalNombre, Date fechaInicial, Date fechaFinal) {
        if (!(fechaFinal == null && fechaInicial == null)) {

            if (fechaFinal == null && fechaInicial != null) {
                errors.rejectValue(fechaFinalNombre, "error.fechaFinal.obligatoria");
            } else if (fechaFinal != null && fechaInicial == null) {
                errors.rejectValue(fechaInicialNombre, "error.fechaInicial.obligatoria");
            } else {
                if (fechaFinal.before(fechaInicial)) {
                    errors.rejectValue(fechaInicialNombre, "error.fechaFinal.anterior");
                }
            }
        }
        return errors;
    }

    public static Date[] fechasUltimoMes(Date date) {
        Calendar primerDia = Calendar.getInstance();
        primerDia.setTime(date);
        primerDia.add(Calendar.MONTH, -1);

        Calendar ultimoDia = (Calendar) primerDia.clone();

        primerDia.set(Calendar.DAY_OF_MONTH, primerDia.getActualMinimum(Calendar.DAY_OF_MONTH));
        ultimoDia.set(Calendar.DAY_OF_MONTH, primerDia.getActualMaximum(Calendar.DAY_OF_MONTH));

        return new Date[]{primerDia.getTime(), ultimoDia.getTime()};
    }

    public static Date[] fechasUltimaSemana(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i - 7);
        Date primerDia = c.getTime();
        c.add(Calendar.DATE, 6);
        Date ultimoDia = c.getTime();
        return new Date[]{primerDia, ultimoDia};
    }

    public static Date addDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static Date add(Date date, Integer entidad, Integer cantidad) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(entidad, cantidad);
        return calendar.getTime();
    }

    public static Date getFechaInicioDia(Date fecha) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getFechaInicioMes(Date fecha) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getFechaInicioDia(fecha));

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    public static Date getFechaAlFinDia(Date fecha) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    public static Date getFechaAlFinMes(Date fecha) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getFechaAlFinDia(fecha));

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    public static String getFechaFormateadaStringDatePicker(Date fecha) {

        if (fecha == null) {
            return "";
        }

        return DateUtils.ddMMyyyy.format(fecha);
    }

    public static Date getUltimaFechaTiempo() {
        TimeZone UTC = TimeZone.getTimeZone("Europe/Madrid");
        final Calendar c = new GregorianCalendar(UTC);
        c.setTime(new Date(Long.MAX_VALUE));
        Date END_OF_TIME = c.getTime();
        return END_OF_TIME;
    }

    public static Date crearFecha(int dia, int mes, int ano) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, dia);
        cal.set(Calendar.MONTH, mes);
        cal.set(Calendar.YEAR, ano);
        Date date = cal.getTime();

        return date;
    }

    public static int getCantidadDiasEntreFechas(Date fechaIn, Date fechaFin) {
        Calendar start = org.apache.commons.lang3.time.DateUtils.toCalendar(fechaIn);
        start.set(Calendar.MILLISECOND, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.HOUR_OF_DAY, 0);

        Calendar finish = org.apache.commons.lang3.time.DateUtils.toCalendar(fechaFin);
        finish.set(Calendar.MILLISECOND, 999);
        finish.set(Calendar.SECOND, 59);
        finish.set(Calendar.MINUTE, 59);
        finish.set(Calendar.HOUR_OF_DAY, 23);

        long delta = finish.getTimeInMillis() - start.getTimeInMillis();
        return (int) Math.ceil(delta / (1000.0 * 60 * 60 * 24));
    }


    public static String getMesesFromMensajesProperties[] = {
            PropertiesUtils.getPropiedadMensaje("meses.enero"),
            PropertiesUtils.getPropiedadMensaje("meses.febrero"),
            PropertiesUtils.getPropiedadMensaje("meses.marzo"),
            PropertiesUtils.getPropiedadMensaje("meses.abril"),
            PropertiesUtils.getPropiedadMensaje("meses.mayo"),
            PropertiesUtils.getPropiedadMensaje("meses.junio"),
            PropertiesUtils.getPropiedadMensaje("meses.julio"),
            PropertiesUtils.getPropiedadMensaje("meses.agosto"),
            PropertiesUtils.getPropiedadMensaje("meses.septiembre"),
            PropertiesUtils.getPropiedadMensaje("meses.octubre"),
            PropertiesUtils.getPropiedadMensaje("meses.noviembre"),
            PropertiesUtils.getPropiedadMensaje("meses.diciembre"),
    };


    public static enum Mes {

        ENERO("Enero", 1),
        FEBRERO("Febrero", 2),
        MARZO("Marzo", 3),
        ABRIL("Abril", 4),
        MAYO("Mayo", 5),
        JUNIO("Junio", 6),
        JULIO("Julio", 7),
        AGOSTO("Agosto", 8),
        SEPTIEMBRE("Septiembre", 9),
        OCTUBRE("Octubre", 10),
        NOVIEMBRE("Noviembre", 11),
        DICIEMBRE("Diciembre", 12);

        private final String nombre;
        private final int numero;

        private Mes(String nombre, int numero) {
            this.nombre = nombre;
            this.numero = numero;
        }

        public int getNumero() {
            return numero;
        }

        public String getNombre() {
            return nombre;
        }
    }


    public static String getStringMesByInteger(Integer mes) {
        String mesString;
        switch (mes) {
            case 1:
                mesString = "Enero";
                break;
            case 2:
                mesString = "Febrero";
                break;
            case 3:
                mesString = "Marzo";
                break;
            case 4:
                mesString = "Abril";
                break;
            case 5:
                mesString = "Mayo";
                break;
            case 6:
                mesString = "Junio";
                break;
            case 7:
                mesString = "Julio";
                break;
            case 8:
                mesString = "Agosto";
                break;
            case 9:
                mesString = "Septiembre";
                break;
            case 10:
                mesString = "Octubre";
                break;
            case 11:
                mesString = "Noviembre";
                break;
            case 12:
                mesString = "Diciembre";
                break;
            default:
                mesString = "Invalid month";
                break;
        }
        return mesString;
    }


    public static Integer getDiasEnElMes(Integer anyo, Integer mes) {
        YearMonth anyoMes = YearMonth.of(anyo, mes);
        return anyoMes.lengthOfMonth();
    }
}
