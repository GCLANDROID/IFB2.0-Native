package io.cordova.ifb.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private SharedPreferences _pref;
    private static final String PREF_FILE = "com.deus";
    private SharedPreferences.Editor _editorPref;

    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        _pref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        _editorPref = _pref.edit();
    }


    public void saveEmpName(String EmpName){
        _editorPref.putString("EmpName", EmpName);
        _editorPref.commit();
    }

    public String getEmpName(){
        return _pref.getString("EmpName","");
    }

    public void saveLoginTime(String LoginTime){
        _editorPref.putString("LoginTime", LoginTime);
        _editorPref.commit();
    }

    public String getLoginTime(){
        return _pref.getString("LoginTime","");
    }



    public void saveCounter(String Counter){
        _editorPref.putString("Counter", Counter);
        _editorPref.commit();
    }

    public String getCounter(){
        return _pref.getString("Counter","");
    }


    public void saveBranchId(String BranchId){
        _editorPref.putString("BranchId", BranchId);
        _editorPref.commit();
    }

    public String getBranchId(){
        return _pref.getString("BranchId","");
    }


    public void saveClintId(String ClintId){
        _editorPref.putString("ClintId", ClintId);
        _editorPref.commit();
    }

    public String getClintId(){
        return _pref.getString("ClintId","");
    }


    public void saveUserId(String UserId){
        _editorPref.putString("UserId", UserId);
        _editorPref.commit();
    }

    public String getUserId(){
        return _pref.getString("UserId","");
    }

    public void savePassword(String Password){
        _editorPref.putString("Password", Password);
        _editorPref.commit();
    }

    public String getPassword(){
        return _pref.getString("Password","");
    }

    public void saveWebSales(String WebSales){
        _editorPref.putString("WebSales", WebSales);
        _editorPref.commit();
    }

    public String getWebSales(){
        return _pref.getString("WebSales","");
    }

    public void saveSecurityCode(String SecurityCode){
        _editorPref.putString("SecurityCode", SecurityCode);
        _editorPref.commit();
    }

    public String getSecurityCode(){
        return _pref.getString("SecurityCode","");
    }


    public void saveRemberFlag(String RemberFlag){
        _editorPref.putString("RemberFlag", RemberFlag);
        _editorPref.commit();
    }

    public String getRemberFlag(){
        return _pref.getString("RemberFlag","");
    }


    public void saveMasterId(String MasterId){
        _editorPref.putString("MasterId", MasterId);
        _editorPref.commit();
    }

    public String getMasterId(){
        return _pref.getString("MasterId","");
    }


    public void saveUserCode(String UserCode){
        _editorPref.putString("UserCode", UserCode);
        _editorPref.commit();
    }

    public String getUserCode(){
        return _pref.getString("UserCode","");
    }


    public void saveZoneId(String ZoneId){
        _editorPref.putString("ZoneId", ZoneId);
        _editorPref.commit();
    }

    public String getZoneId(){
        return _pref.getString("ZoneId","");
    }


    public void saveMonthlyTarget(String MonthlyTarget){
        _editorPref.putString("MonthlyTarget", MonthlyTarget);
        _editorPref.commit();
    }

    public String getMonthlyTarget(){
        return _pref.getString("MonthlyTarget","");
    }

    public void saveSold(String Sold){
        _editorPref.putString("Sold", Sold);
        _editorPref.commit();
    }

    public String getSold(){
        return _pref.getString("Sold","");
    }


    public void savePending(String Pending){
        _editorPref.putString("Pending", Pending);
        _editorPref.commit();
    }

    public String getPending(){
        return _pref.getString("Pending","");
    }


    public void saveTarget(String Target){
        _editorPref.putString("Target", Target);
        _editorPref.commit();
    }

    public String getTarget(){
        return _pref.getString("Target","");
    }



       public void saveApproved(String Approved){
        _editorPref.putString("Approved", Approved);
        _editorPref.commit();
    }

    public String getApproved(){
        return _pref.getString("Approved","");
    }



    public void saveRejected(String Rejected){
        _editorPref.putString("Rejected", Rejected);
        _editorPref.commit();
    }

    public String getRejected(){
        return _pref.getString("Rejected","");
    }



    public void saveHRDeskURL(String HRDeskURL){
        _editorPref.putString("HRDeskURL", HRDeskURL);
        _editorPref.commit();
    }

    public String getHRDeskURL(){
        return _pref.getString("HRDeskURL","");
    }


    public void saveManualURL(String ManualURL){
        _editorPref.putString("ManualURL", ManualURL);
        _editorPref.commit();
    }

    public String getManualURL(){
        return _pref.getString("ManualURL","");
    }


    public void saveLeaveURL(String LeaveURL){
        _editorPref.putString("LeaveURL", LeaveURL);
        _editorPref.commit();
    }

    public String getLeaveURL(){
        return _pref.getString("LeaveURL","");
    }


    public void saveLeaveEncahURL(String LeaveEncahURL){
        _editorPref.putString("LeaveEncahURL", LeaveEncahURL);
        _editorPref.commit();
    }

    public String getLeaveEncahURL(){
        return _pref.getString("LeaveEncahURL","");
    }

    public void saveAirConditionerId(String AirConditionerId){
        _editorPref.putString("AirConditionerId", AirConditionerId);
        _editorPref.commit();
    }

    public String getAirConditionerId(){
        return _pref.getString("AirConditionerId","");
    }

    public void saveWasherDryerId(String WasherDryerId){
        _editorPref.putString("WasherDryerId", WasherDryerId);
        _editorPref.commit();
    }

    public String getWasherDryerId(){
        return _pref.getString("WasherDryerId","");
    }



    public void SaveKAItemId(String BuiltInHobsId){
        _editorPref.putString("BuiltInHobsId", BuiltInHobsId);
        _editorPref.commit();
    }

    public String getKAItemId(){
        return _pref.getString("BuiltInHobsId","");
    }

    public void saveBuiltInOvenId(String BuiltInOvenId){
        _editorPref.putString("BuiltInOvenId", BuiltInOvenId);
        _editorPref.commit();
    }

    public String getBuiltInOvenId(){
        return _pref.getString("BuiltInOvenId","");
    }


    public void saveClothsDryerId(String ClothsDryerId){
        _editorPref.putString("ClothsDryerId", ClothsDryerId);
        _editorPref.commit();
    }

    public String getClothsDryerId(){
        return _pref.getString("ClothsDryerId","");
    }


    public void saveCookerHoodsId(String CookerHoodsId){
        _editorPref.putString("CookerHoodsId", CookerHoodsId);
        _editorPref.commit();
    }

    public String getCookerHoodsId(){
        return _pref.getString("CookerHoodsId","");
    }


    public void saveDishWasherId(String DishWasherId){
        _editorPref.putString("DishWasherId", DishWasherId);
        _editorPref.commit();
    }

    public String getDishWasherId(){
        return _pref.getString("DishWasherId","");
    }

    public void saveMicroOvenId(String MicroOvenId){
        _editorPref.putString("MicroOvenId", MicroOvenId);
        _editorPref.commit();
    }

    public String getMicroOvenId(){
        return _pref.getString("MicroOvenId","");
    }


    public void saveRefrigeratorId(String RefrigeratorId){
        _editorPref.putString("RefrigeratorId", RefrigeratorId);
        _editorPref.commit();
    }

    public String getRefrigeratorId(){
        return _pref.getString("RefrigeratorId","");
    }


    public void saveWaterPurifierId(String WaterPurifierId){
        _editorPref.putString("WaterPurifierId", WaterPurifierId);
        _editorPref.commit();
    }

    public String getWaterPurifierId(){
        return _pref.getString("WaterPurifierId","");
    }

    public void saveWashingFLUId(String WashingFLUId){
        _editorPref.putString("WashingFLUId", WashingFLUId);
        _editorPref.commit();
    }

    public String getWashingFLUId(){
        return _pref.getString("WashingFLUId","");
    }

    public void saveWashingTLId(String WashingTLId){
        _editorPref.putString("WashingTLId", WashingTLId);
        _editorPref.commit();
    }

    public String getWashingTLId(){
        return _pref.getString("WashingTLId","");
    }


    public void saveetAirDaikin(String etAirDaikin){
        _editorPref.putString("etAirDaikin", etAirDaikin);
        _editorPref.commit();
    }

    public String getetAirDaikin(){
        return _pref.getString("etAirDaikin","");
    }

    public void saveetAirIFB(String etAirIFB){
        _editorPref.putString("etAirIFB", etAirIFB);
        _editorPref.commit();
    }

    public String getetAirIFB(){
        return _pref.getString("etAirIFB","");
    }


    public void saveetAirLG(String etAirLG){
        _editorPref.putString("etAirLG", etAirLG);
        _editorPref.commit();
    }

    public String getetAirLG(){
        return _pref.getString("etAirLG","");
    }


    public void saveetAirLloyds(String etAirLloyds){
        _editorPref.putString("etAirLloyds", etAirLloyds);
        _editorPref.commit();
    }

    public String getetAirLloyds(){
        return _pref.getString("etAirLloyds","");
    }

    public void saveetAirLOthers(String etAirLOthers){
        _editorPref.putString("etAirLOthers", etAirLOthers);
        _editorPref.commit();
    }

    public String getetAirLOthers(){
        return _pref.getString("etAirLOthers","");
    }


    public void saveetAirLSSG(String etAirLSSG){
        _editorPref.putString("etAirLSSG", etAirLSSG);
        _editorPref.commit();
    }

    public String getetAirLSSG(){
        return _pref.getString("etAirLSSG","");
    }


    public void saveetAirVoltas(String etAirVoltas){
        _editorPref.putString("etAirVoltas", etAirVoltas);
        _editorPref.commit();
    }

    public String getetAirVoltas(){
        return _pref.getString("etAirVoltas","");
    }

    public void saveetAirWPL(String etAirWPL){
        _editorPref.putString("etAirWPL", etAirWPL);
        _editorPref.commit();
    }

    public String getetAirWPL(){
        return _pref.getString("etAirWPL","");
    }

    public void saveetHobElica(String etHobElica){
        _editorPref.putString("etHobElica", etHobElica);
        _editorPref.commit();
    }

    public String getetHobElica(){
        return _pref.getString("etHobElica","");
    }

    public void saveetHobFaber(String etHobFaber){
        _editorPref.putString("etHobFaber", etHobFaber);
        _editorPref.commit();
    }

    public String getetHobFaber(){
        return _pref.getString("etHobFaber","");
    }

    public void saveetHobIfb(String etHobIfb){
        _editorPref.putString("etHobIfb", etHobIfb);
        _editorPref.commit();
    }

    public String getetHobIfb(){
        return _pref.getString("etHobIfb","");
    }


    public void saveetHobKAFF(String etHobKAFF){
        _editorPref.putString("etHobKAFF", etHobKAFF);
        _editorPref.commit();
    }

    public String getetHobKAFF(){
        return _pref.getString("etHobKAFF","");
    }

    public void saveetHobOther(String etHobOther){
        _editorPref.putString("etHobOther", etHobOther);
        _editorPref.commit();
    }

    public String getetHobOther(){
        return _pref.getString("etHobOther","");
    }

    public void saveetOvenIfb(String etOvenIfb){
        _editorPref.putString("etOvenIfb", etOvenIfb);
        _editorPref.commit();
    }

    public String getetOvenIfb(){
        return _pref.getString("etOvenIfb","");
    }

    public void saveetClothBosch(String etClothBosch){
        _editorPref.putString("etClothBosch", etClothBosch);
        _editorPref.commit();
    }

    public String getetClothBosch(){
        return _pref.getString("etClothBosch","");
    }

    public void saveetClothIFB(String etClothIFB){
        _editorPref.putString("etClothIFB", etClothIFB);
        _editorPref.commit();
    }

    public String getetClothIFB(){
        return _pref.getString("etClothIFB","");
    }

    public void saveetClothLG(String etClothLG){
        _editorPref.putString("etClothLG", etClothLG);
        _editorPref.commit();
    }

    public String getetClothLG (){
        return _pref.getString("etClothLG","");
    }

    public void saveetClothOthrs(String etClothOthrs){
        _editorPref.putString("etClothOthrs", etClothOthrs);
        _editorPref.commit();
    }

    public String getetClothOthrs(){
        return _pref.getString("etClothOthrs","");
    }

    public void saveetClothSimens(String etClothSimens){
        _editorPref.putString("etClothSimens", etClothSimens);
        _editorPref.commit();
    }

    public String getetClothSimens(){
        return _pref.getString("etClothSimens","");
    }

    public void saveCookerElica(String CookerElica){
        _editorPref.putString("CookerElica", CookerElica);
        _editorPref.commit();
    }

    public String getCookerElica(){
        return _pref.getString("CookerElica","");
    }

    public void saveCookerFaber(String CookerFaber){
        _editorPref.putString("CookerFaber", CookerFaber);
        _editorPref.commit();
    }

    public String getCookerFaber(){
        return _pref.getString("CookerFaber","");
    }

    public void saveCookerIFB(String CookerIFB){
        _editorPref.putString("CookerIFB", CookerIFB);
        _editorPref.commit();
    }

    public String getCookerIFB(){
        return _pref.getString("CookerIFB","");
    }

    public void saveCookerKaff(String CookerKaff){
        _editorPref.putString("CookerKaff", CookerKaff);
        _editorPref.commit();
    }

    public String getCookerKaff(){
        return _pref.getString("CookerKaff","");
    }

    public void saveCookerOthers(String CookerOthers){
        _editorPref.putString("CookerOthers", CookerOthers);
        _editorPref.commit();
    }

    public String getCookerOthers(){
        return _pref.getString("CookerOthers","");
    }

    public void saveDishBosch(String DishBosch){
        _editorPref.putString("DishBosch", DishBosch);
        _editorPref.commit();
    }

    public String getDishBosch(){
        return _pref.getString("DishBosch","");
    }

    public void saveDishIFB(String DishIFB){
        _editorPref.putString("DishIFB", DishIFB);
        _editorPref.commit();
    }

    public String getDishIFB(){
        return _pref.getString("DishIFB","");
    }

    public void saveDishOthers(String DishOthers){
        _editorPref.putString("DishOthers", DishOthers);
        _editorPref.commit();
    }

    public String getDishOthers(){
        return _pref.getString("DishOthers","");
    }


    public void saveDishSimens(String DishSimens){
        _editorPref.putString("DishSimens", DishSimens);
        _editorPref.commit();
    }

    public String getDishSimens(){
        return _pref.getString("DishSimens","");
    }

    public void saveMicroGodrej(String MicroGodrej){
        _editorPref.putString("MicroGodrej", MicroGodrej);
        _editorPref.commit();
    }

    public String getMicroGodrej(){
        return _pref.getString("MicroGodrej","");
    }

    public void saveMicroIfb(String MicroIfb){
        _editorPref.putString("MicroIfb", MicroIfb);
        _editorPref.commit();
    }

    public String getMicroIfb(){
        return _pref.getString("MicroIfb","");
    }

    public void saveMicroLG(String MicroLG){
        _editorPref.putString("MicroLG", MicroLG);
        _editorPref.commit();
    }

    public String getMicroLG(){
        return _pref.getString("MicroLG","");
    }

    public void saveMicroOthrs(String MicroOthrs){
        _editorPref.putString("MicroOthrs", MicroOthrs);
        _editorPref.commit();
    }

    public String getMicroOthrs(){
        return _pref.getString("MicroOthrs","");
    }

    public void saveMicroPanasonic(String MicroPanasonic){
        _editorPref.putString("MicroPanasonic", MicroPanasonic);
        _editorPref.commit();
    }

    public String getMicroPanasonic(){
        return _pref.getString("MicroPanasonic","");
    }

    public void saveMicroSSG(String MicroSSG){
        _editorPref.putString("MicroSSG", MicroSSG);
        _editorPref.commit();
    }

    public String getMicroSSG(){
        return _pref.getString("MicroSSG","");
    }

    public void saveMicroWPL(String MicroWPL){
        _editorPref.putString("MicroWPL", MicroWPL);
        _editorPref.commit();
    }

    public String getMicroWPL(){
        return _pref.getString("MicroWPL","");
    }

    public void saveRefIFB(String RefIFB){
        _editorPref.putString("RefIFB", RefIFB);
        _editorPref.commit();
    }

    public String getRefIFB(){
        return _pref.getString("RefIFB","");
    }

    public void saveWaterIFB(String WaterIFB){
        _editorPref.putString("WaterIFB", WaterIFB);
        _editorPref.commit();
    }

    public String getWaterIFB(){
        return _pref.getString("WaterIFB","");
    }



    public String getWMFLUBosch(){
        return _pref.getString("WMFLUBosch","");
    }

    public void saveWMFLUIFB(String WMFLUIFB){
        _editorPref.putString("WMFLUIFB", WMFLUIFB);
        _editorPref.commit();
    }

    public String getWMFLUIFB(){
        return _pref.getString("WMFLUIFB","");
    }

    public void saveWMFLULG(String WMFLULG){
        _editorPref.putString("WMFLULG", WMFLULG);
        _editorPref.commit();
    }

    public String getWMFLULG(){
        return _pref.getString("WMFLULG","");
    }



    public String getWMFLUOthers(){
        return _pref.getString("WMFLUOthers","");
    }

    public void saveWMFLUSSG(String WMFLUSSG){
        _editorPref.putString("WMFLUSSG", WMFLUSSG);
        _editorPref.commit();
    }

    public String getWMFLUSSG(){
        return _pref.getString("WMFLUSSG","");
    }

    public void saveWMTLGodrej(String WMTLGodrej){
        _editorPref.putString("WMTLGodrej", WMTLGodrej);
        _editorPref.commit();
    }

    public String getWMTLGodrej(){
        return _pref.getString("WMTLGodrej","");
    }

    public void saveWMTLIFB(String WMTLIFB){
        _editorPref.putString("WMTLIFB", WMTLIFB);
        _editorPref.commit();
    }

    public String getWMTLIFB(){
        return _pref.getString("WMTLIFB","");
    }


    public void saveWMTLLG(String WMTLLG){
        _editorPref.putString("WMTLLG", WMTLLG);
        _editorPref.commit();
    }

    public String getWMTLLG(){
        return _pref.getString("WMTLLG","");
    }

    public void saveWMTLOthers(String WMTLOthers){
        _editorPref.putString("WMTLOthers", WMTLOthers);
        _editorPref.commit();
    }

    public String getWMTLOthers(){
        return _pref.getString("WMTLOthers","");
    }

    public void saveWMTLPanasonic(String WMTLPanasonic){
        _editorPref.putString("WMTLPanasonic", WMTLPanasonic);
        _editorPref.commit();
    }

    public String getWMTLPanasonic(){
        return _pref.getString("WMTLPanasonic","");
    }

    public void saveWMTLSSG(String WMTLSSG){
        _editorPref.putString("WMTLSSG", WMTLSSG);
        _editorPref.commit();
    }

    public String getWMTLSSG(){
        return _pref.getString("WMTLSSG","");
    }

    public void saveWMTLWML(String WMTLWML){
        _editorPref.putString("WMTLWML", WMTLWML);
        _editorPref.commit();
    }

    public String getWMTLWML(){
        return _pref.getString("WMTLWML","");
    }

    public void saveAirLG(String AirLG){
        _editorPref.putString("AirLG", AirLG);
        _editorPref.commit();
    }

    public String getAirLG(){
        return _pref.getString("AirLG","");
    }

    public void saveUserTypeId(String UserTypeId){
        _editorPref.putString("UserTypeId", UserTypeId);
        _editorPref.commit();
    }

    public String getUserTypeId(){
        return _pref.getString("UserTypeId","");
    }


    public void savesaveTSRItem(String TSRItem){
        _editorPref.putString("TSRItem", TSRItem);
        _editorPref.commit();
    }

    public String getTSRItem(){
        return _pref.getString("TSRItem","");
    }

    public void savesaveOtherItem1(String OtherItem1){
        _editorPref.putString("OtherItem1", OtherItem1);
        _editorPref.commit();
    }

    public String getOtherItem1(){
        return _pref.getString("OtherItem1","");
    }

    public void savesaveOtherItem2(String OtherItem2){
        _editorPref.putString("OtherItem2", OtherItem2);
         _editorPref.commit();
    }

    public String getOtherItem2(){
        return _pref.getString("OtherItem2","");
    }


    public void saveCompetitorItem(String CompetitorItem){
        _editorPref.putString("CompetitorItem", CompetitorItem);
        _editorPref.commit();
    }

    public String getCompetitorItem(){
        return _pref.getString("CompetitorItem","");
    }


    public void saveAirIfbSize(int AirIfbSize){
        _editorPref.putInt("AirIfbSize", AirIfbSize);
        _editorPref.commit();
    }

    public int getAirIfbSize(){
        return _pref.getInt("AirIfbSize",0);
    }


    public void saveWasherDryerIfbSize(int WasherDryerIfbSize){
        _editorPref.putInt("WasherDryerIfbSize", WasherDryerIfbSize);
        _editorPref.commit();
    }

    public int getWasherDryerIfbSize(){
        return _pref.getInt("WasherDryerIfbSize",0);
    }


    public void saveKAItemSize(int HobIfbSize){
        _editorPref.putInt("HobIfbSize", HobIfbSize);
        _editorPref.commit();
    }

    public int getKAIfbSize(){
        return _pref.getInt("HobIfbSize",0);
    }

    public void saveOvenIfbSize(int ovenifbsize){
        _editorPref.putInt("ovenifbsize", ovenifbsize);
        _editorPref.commit();
    }

    public int getOvenIfbSize(){
        return _pref.getInt("ovenifbsize",0);
    }

    public void saveClothsIfbSize(int ClothsIfbSize){
        _editorPref.putInt("ClothsIfbSize", ClothsIfbSize);
        _editorPref.commit();
    }

    public int getClothsIfbSize(){
        return _pref.getInt("ClothsIfbSize",0);
    }

    public void saveCookerIfbSize(int CookerIfbSize){
        _editorPref.putInt("CookerIfbSize", CookerIfbSize);
        _editorPref.commit();
    }

    public int getCookerIfbSize(){
        return _pref.getInt("CookerIfbSize",0);
    }


    public void saveDishIfbSize(int DishIfbSize){
        _editorPref.putInt("DishIfbSize", DishIfbSize);
        _editorPref.commit();
    }

    public int getDishIfbSize(){
        return _pref.getInt("DishIfbSize",0);
    }

    public void saveMicroOvenIfbSize(int MicroOvenIfbSize){
        _editorPref.putInt("MicroOvenIfbSize", MicroOvenIfbSize);
        _editorPref.commit();
    }

    public int getMicroOvenIfbSize(){
        return _pref.getInt("MicroOvenIfbSize",0);
    }


    public void saveRefIfbSize(int RefIfbSize){
        _editorPref.putInt("RefIfbSize", RefIfbSize);
        _editorPref.commit();
    }

    public int getRefIfbSize(){
        return _pref.getInt("RefIfbSize",0);
    }


    public void saveWMFLUIfbSize(int WMFLUIfbSize){
        _editorPref.putInt("WMFLUIfbSize", WMFLUIfbSize);
        _editorPref.commit();
    }

    public int getWMFLUIfbSize(){
        return _pref.getInt("WMFLUIfbSize",0);
    }


    public void saveWMTLIFBSize(int WMTLIFBSize){
        _editorPref.putInt("WMTLIFBSize", WMTLIFBSize);
        _editorPref.commit();
    }

    public int getWMTLIFBSize(){
        return _pref.getInt("WMTLIFBSize",0);
    }

    public void saveDailyLogFlag(String DailyLogFlag){
        _editorPref.putString("DailyLogFlag", DailyLogFlag);
        _editorPref.commit();
    }

    public String getDailyLogFlag(){
        return _pref.getString("DailyLogFlag","");
    }




    public void saveDocFlag(String DocFlag){
        _editorPref.putString("DocFlag", DocFlag);
        _editorPref.commit();
    }

    public String getDocFlag(){
        return _pref.getString("DocFlag","");
    }



    public void saveCVFlag(String CVFlag){
        _editorPref.putString("CVFlag", CVFlag);
        _editorPref.commit();
    }

    public String getCVFlag(){
        return _pref.getString("CVFlag","");
    }



    public void saveInvoiceFlag(String InvoiceFlag){
        _editorPref.putString("InvoiceFlag", InvoiceFlag);
        _editorPref.commit();
    }

    public String getInvoiceFlag(){
        return _pref.getString("InvoiceFlag","");
    }



    public void saveAirLg(String AirLg){
        _editorPref.putString("AirLg", AirLg);
        _editorPref.commit();
    }

    public String getAirLg(){
        return _pref.getString("AirLg","");
    }

    public void saveAirSamsung(String AirSamsung){
        _editorPref.putString("AirSamsung", AirSamsung);
        _editorPref.commit();
    }

    public String getAirSamsung(){
        return _pref.getString("AirSamsung","");
    }

    public void saveAirDaikin(String AirDaikin){
        _editorPref.putString("AirDaikin", AirDaikin);
        _editorPref.commit();
    }

    public String getAirDaikin(){
        return _pref.getString("AirDaikin","");
    }


    public void saveAirCarrier(String AirCarrier){
        _editorPref.putString("AirCarrier", AirCarrier);
        _editorPref.commit();
    }

    public String getAirCarrier(){
        return _pref.getString("AirCarrier","");
    }



    public void saveAirBlue(String AirBlue){
        _editorPref.putString("AirBlue", AirBlue);
        _editorPref.commit();
    }

    public String getAirBlue(){
        return _pref.getString("AirBlue","");
    }

    public void saveAirVoltas(String AirVoltas){
        _editorPref.putString("AirVoltas", AirVoltas);
        _editorPref.commit();
    }

    public String getAirVoltas(){
        return _pref.getString("AirVoltas","");
    }


    public void saveAirOnida(String AirOnida){
        _editorPref.putString("AirOnida", AirOnida);
        _editorPref.commit();
    }

    public String getAirOnida(){
        return _pref.getString("AirOnida","");
    }


    public void saveAirPanasonic(String AirPanasonic){
        _editorPref.putString("AirPanasonic", AirPanasonic);
        _editorPref.commit();
    }

    public String getAirPanasonic(){
        return _pref.getString("AirPanasonic","");
    }

    public void saveAirWhirlPool(String AirWhirlPool){
        _editorPref.putString("AirWhirlPool", AirWhirlPool);
        _editorPref.commit();
    }

    public String getAirWhirlPool(){
        return _pref.getString("AirWhirlPool","");
    }

    public void saveAirGeneral(String AirGeneral){
        _editorPref.putString("AirGeneral", AirGeneral);
        _editorPref.commit();
    }

    public String getAirGeneral(){
        return _pref.getString("AirGeneral","");
    }

    public void saveAirGodrej(String AirGodrej){
        _editorPref.putString("AirGodrej", AirGodrej);
        _editorPref.commit();
    }

    public String getAirGodrej(){
        return _pref.getString("AirGodrej","");
    }


    public void saveAirHaier(String AirHaier){
        _editorPref.putString("AirHaier", AirHaier);
        _editorPref.commit();
    }

    public String getAirHaier(){
        return _pref.getString("AirHaier","");
    }

    public void saveAirLloyds(String AirLloyds){
        _editorPref.putString("AirLloyds", AirLloyds);
        _editorPref.commit();
    }

    public String getAirLloyds(){
        return _pref.getString("AirLloyds","");
    }


    public void saveAirOthers(String AirOthers){
        _editorPref.putString("AirOthers", AirOthers);
        _editorPref.commit();
    }

    public String getAirOthers(){
        return _pref.getString("AirOthers","");
    }

    public void saveClothsBosch(String ClothsBosch){
        _editorPref.putString("ClothsBosch", ClothsBosch);
        _editorPref.commit();
    }

    public String getClothsBosch(){
        return _pref.getString("ClothsBosch","");
    }

    public void saveDishLg(String DishLg){
        _editorPref.putString("DishLg", DishLg);
        _editorPref.commit();
    }

    public String getDishLg(){
        return _pref.getString("DishLg","");
    }

    public void saveDishSamsung(String DishSamsung){
        _editorPref.putString("DishSamsung", DishSamsung);
        _editorPref.commit();
    }

    public String getDishSamsung(){
        return _pref.getString("DishSamsung","");
    }

    public void saveMicroLg(String MicroLg){
        _editorPref.putString("MicroLg", MicroLg);
        _editorPref.commit();
    }

    public String getMicroLg(){
        return _pref.getString("MicroLg","");
    }

    public void saveMicroSamsung(String MicroSamsung){
        _editorPref.putString("MicroSamsung", MicroSamsung);
        _editorPref.commit();
    }

    public String getMicroSamsung(){
        return _pref.getString("MicroSamsung","");
    }

    public void saveMicroWhirlPool(String MicroWhirlPool){
        _editorPref.putString("MicroWhirlPool", MicroWhirlPool);
        _editorPref.commit();
    }

    public String getMicroWhirlPool(){
        return _pref.getString("MicroWhirlPool","");
    }

    public void saveMicroOnida(String MicroOnida){
        _editorPref.putString("MicroOnida", MicroOnida);
        _editorPref.commit();
    }

    public String getMicroOnida(){
        return _pref.getString("MicroOnida","");
    }

    public void saveMicroOthers(String MicroOthers){
        _editorPref.putString("MicroOthers", MicroOthers);
        _editorPref.commit();
    }

    public String getMicroOthers(){
        return _pref.getString("MicroOthers","");
    }

    public void saveKAFaber(String KAFaber){
        _editorPref.putString("KAFaber", KAFaber);
        _editorPref.commit();
    }

    public String getKAFaber(){
        return _pref.getString("KAFaber","");
    }

    public void saveKASunflame(String KASunflame){
        _editorPref.putString("KASunflame", KASunflame);
        _editorPref.commit();
    }

    public String getKASunflame(){
        return _pref.getString("KASunflame","");
    }

    public void saveKAElica(String KAElica){
        _editorPref.putString("KAElica", KAElica);
        _editorPref.commit();
    }

    public String getKAElica(){
        return _pref.getString("KAElica","");
    }

    public void saveKAKaff(String KAKaff){
        _editorPref.putString("KAKaff", KAKaff);
        _editorPref.commit();
    }

    public String getKAKaff(){
        return _pref.getString("KAKaff","");
    }

    public void saveKABosch(String KABosch){
        _editorPref.putString("KABosch", KABosch);
        _editorPref.commit();
    }

    public String getKABosch(){
        return _pref.getString("KABosch","");
    }

    public void saveKAOthers(String KAOthers){
        _editorPref.putString("KAOthers", KAOthers);
        _editorPref.commit();
    }

    public String getKAOthers(){
        return _pref.getString("KAOthers","");
    }

    public void saveWMFLULg(String WMFLULg){
        _editorPref.putString("WMFLULg", WMFLULg);
        _editorPref.commit();
    }

    public String getWMFLULg(){
        return _pref.getString("WMFLULg","");
    }

    public void saveWMFLUSamsung(String WMFLUSamsung){
        _editorPref.putString("WMFLUSamsung", WMFLUSamsung);
        _editorPref.commit();
    }

    public String getWMFLUSamsung(){
        return _pref.getString("WMFLUSamsung","");
    }

    public void saveWMFLUBosch(String WMFLUBosch){
        _editorPref.putString("WMFLUBosch", WMFLUBosch);
        _editorPref.commit();
    }

    public String getWMFLUWMFLUBosch(){
        return _pref.getString("WMFLUSamsung","");
    }

    public void saveWMFLUWhirlpool(String WMFLUWhirlpool){
        _editorPref.putString("WMFLUWhirlpool", WMFLUWhirlpool);
        _editorPref.commit();
    }

    public String getWMFLUWhirlpool(){
        return _pref.getString("WMFLUWhirlpool","");
    }

    public void saveWMFLUBeko(String WMFLUBeko){
        _editorPref.putString("WMFLUBeko", WMFLUBeko);
        _editorPref.commit();
    }

    public String getWMFLUWMFLUBeko(){
        return _pref.getString("WMFLUBeko","");
    }

    public void saveWMFLUOthers(String WMFLUOthers){
        _editorPref.putString("WMFLUOthers", WMFLUOthers);
        _editorPref.commit();
    }

    public String getWMFLUWMFLUOthers(){
        return _pref.getString("WMFLUOthers","");
    }


    public void saveWMTLLg(String WMTLLg){
        _editorPref.putString("WMTLLg", WMTLLg);
        _editorPref.commit();
    }

    public String getWMTLLg(){
        return _pref.getString("WMTLLg","");
    }

    public void saveWMTLSamsung(String WMTLSamsung){
        _editorPref.putString("WMTLSamsung", WMTLSamsung);
        _editorPref.commit();
    }

    public String getWMTLSamsung(){
        return _pref.getString("WMTLSamsung","");
    }

    public void saveWMTLBosch(String WMTLBosch){
        _editorPref.putString("WMTLBosch", WMTLBosch);
        _editorPref.commit();
    }

    public String getWMTLBosch(){
        return _pref.getString("WMTLBosch","");
    }


    public void saveWMTLWhirlpool(String WMTLWhirlpool){
        _editorPref.putString("WMTLWhirlpool", WMTLWhirlpool);
        _editorPref.commit();
    }

    public String getWMTLWhirlpool(){
        return _pref.getString("WMTLWhirlpool","");
    }


    public void saveWMTLOnida(String WMTLOnida){
        _editorPref.putString("WMTLOnida", WMTLOnida);
        _editorPref.commit();
    }

    public String getWMTLOnida(){
        return _pref.getString("WMTLOnida","");
    }

    public void saveRefreshToken(String RefreshToken){
        _editorPref.putString("RefreshToken", RefreshToken);
        _editorPref.commit();
    }

    public String getRefreshToken(){
        return _pref.getString("RefreshToken","");
    }



    public void saveSubDealerType(String SubDealerType){
        _editorPref.putString("SubDealerType", SubDealerType);
        _editorPref.commit();
    }

    public String getSubDealerType(){
        return _pref.getString("SubDealerType","");
    }

    public void saveSalesPartyCode(String SalesPartyCode){
        _editorPref.putString("SalesPartyCode", SalesPartyCode);
        _editorPref.commit();
    }

    public String getSalesPartyCode(){
        return _pref.getString("SalesPartyCode","");
    }

    public void saveCustomerSOPImage(String CustomerSOPImage){
        _editorPref.putString("CustomerSOPImage", CustomerSOPImage);
        _editorPref.commit();
    }

    public String getCustomerSOPImage(){
        return _pref.getString("CustomerSOPImage","");
    }

    public void saveNotify(String Notify){
        _editorPref.putString("Notify", Notify);
        _editorPref.commit();
    }

    public String getNotify(){
        return _pref.getString("Notify","");
    }


    public void saveNotifyUrl(String NotifyUrl){
        _editorPref.putString("NotifyUrl", NotifyUrl);
        _editorPref.commit();
    }

    public String getNotifyUrl(){
        return _pref.getString("NotifyUrl","");
    }



    public void saveSalesPointID(String SalesPointID){
        _editorPref.putString("SalesPointID", SalesPointID);
        _editorPref.commit();
    }

    public String getSalesPointID(){
        return _pref.getString("SalesPointID","");
    }


    public void saveIsFillCSRSurvey(String IsFillCSRSurvey){
        _editorPref.putString("IsFillCSRSurvey", IsFillCSRSurvey);
        _editorPref.commit();
    }

    public String getIsFillCSRSurvey(){
        return _pref.getString("IsFillCSRSurvey","");
    }

    public void saveCSRSurveyURL(String CSRSurveyURL){
        _editorPref.putString("CSRSurveyURL", CSRSurveyURL);
        _editorPref.commit();
    }

    public String getCSRSurveyURL(){
        return _pref.getString("CSRSurveyURL","");
    }






















}
