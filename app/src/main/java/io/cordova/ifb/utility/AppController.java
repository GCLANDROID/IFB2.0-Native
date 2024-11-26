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


    public static  int air_lg=0;
    public static  int air_samsung=0;
    public static  int air_daiken=0;
    public static  int air_carrier=0;
    public static  int air_bluestar=0;
    public static  int air_voltas=0;
    public static  int air_onida=0;
    public static  int air_panasonic=0;
    public static  int air_whirlpool=0;
    public static  int air_ogeneral=0;
    public static  int air_godrej=0;
    public static  int air_Haier=0;
    public static  int air_llyods=0;
    public static  int cloths_bosch=0;
    public static  int dish_bosch=0;
    public static  int dish_lg=0;
    public static  int dish_sam=0;
    public static  int micro_lg=0;
    public static  int micro_sam=0;
    public static  int micro_whirl=0;
    public static  int micro_pana=0;
    public static  int micro_godrej=0;
    public static  int micro_onida=0;
    public static  int ka_faber=0;
    public static  int ka_sun=0;
    public static  int ka_elica=0;
    public static  int ka_kaff=0;
    public static  int ka_bosch=0;
    public static  int wmflu_lg=0;
    public static  int wmflu_sam=0;
    public static  int wmflu_bosch=0;
    public static  int wmflu_whirlpool=0;
    public static  int wmflu_beko=0;
    public static  int tl_lg=0;
    public static  int tl_sam=0;
    public static  int tl_bosch=0;
    public static  int tl_whirlpool=0;
    public static  int tl_pana=0;
    public static  int tl_godrej=0;
    public static  int tl_onida=0;
    public static  int dryer_lg=0;
    public static  int dryer_sam=0;
    public static  int dryer_whirlpool=0;
    public static  int dryer_pansonic=0;
    public static  int dryer_godrej=0;
    public static  int dryer_onida=0;
    public static  int dc_sam=0;
    public static  int dc_lg=0;
    public static  int dc_whirlpool=0;
    public static  int dc_haier=0;
    public static  int dc_godrej=0;
    public static  int ff_godrej=0;
    public static  int ff_haier=0;

    public static String APIURL="https://nonfss.geniusconsultant.com/IFBiOSApi/";
    public static String localAPIURL="https://171.16.2.30/IFBiOSApi/";


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
