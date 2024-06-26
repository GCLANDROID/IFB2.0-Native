package io.cordova.ifb.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppController {
    public static int ifbac=0;
    public  static String acid="";

    public static  int ifbclotsdryersize=0;
    public static  String clothsdryedid="";


    public static  int ifbdishsize=0;
    public  static  String dishid="";

    public  static int ifbwashersize=0;
    public  static  String washerid="0";


    public static  int ifbovensize=0;
    public static String ovenid="0";

    public static  int ifbkasize=0;
    public static String kaid="0";

    public static  int ifbflusize=0;
    public static String fluid="";

    public static  int ifbtlsize=0;
    public static String tlid="";

    public static  int ifbrefsize=0;
    public static String refid="";

    public static  int ifbrefffsize=0;
    public static String refffid="";

    public static String APIURL="https://nonfss.geniusconsultant.com/IFBiOSApi/";


    public static String changeAnyDateFormat(String reqdate, String dateformat, String reqformat) {
        //String	date1=reqdate;

        if (reqdate.equalsIgnoreCase("") ||reqdate.equalsIgnoreCase("null") || dateformat.equalsIgnoreCase("") || reqformat.equalsIgnoreCase(""))
            return "";
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        String changedate = "";
        Date dt = null;
        if (!reqdate.equals("") && !reqdate.equals("null")) {
            try {
                dt = format.parse(reqdate);
                //SimpleDateFormat your_format = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat your_format = new SimpleDateFormat(reqformat);
                changedate = your_format.format(dt);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return reqdate;
            }


        }
        return changedate;
    }


}
