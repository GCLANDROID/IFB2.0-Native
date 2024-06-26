package io.cordova.ifb.utility;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Robert
 */

 public  interface PostDisplayMatrixService {
    /*@Multipart
    @POST("/upload_multi_files/MultiUpload.php")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);*/
    @Multipart
    @POST("post_DisplayMatrix")
    Call<UploadObject> postdisplaymatrix(@Part("SalesDate") String SalesDate,@Part("Category") String Category,@Part("Model") String Model,@Part("AEMEmployeeID") String AEMEmployeeID,  @Part("SecurityCode") String SecurityCode);
    @Multipart
    @POST("post_TLSalesActivity")
    Call<UploadObject> postTL(@Part("SalesDate") String SalesDate,@Part("SalesPointID") String SalesPointID,@Part("SalesPointName") String SalesPointName,@Part("Caption") String Caption,@Part("Remarks") String Remarks,@Part("LocationID") String LocationID,@Part("AEMEmployeeID") String AEMEmployeeID,@Part("Operation") String Operation,@Part("FinancialYear") String FinancialYear,@Part("Month") String Month,@Part("UsertTypeID") String UsertTypeID,@Part("SecurityCode") String SecurityCode);
   @Multipart
   @POST("post_TSROtherSalesActivity")
   Call<UploadObject> postTSR(@Part("ZoneID")String  ZoneID,@Part("BranchID") String BranchID,@Part("TransNo")String TransNo,@Part("AEMEmployeeID") String AEMEmployeeID,@Part("SalesDate") String SalesDate,@Part("SalesPointID") String SalesPointID,@Part("SalesPointName") String SalesPointName,@Part("UserTypeID") String UserTypeID,@Part("FinancialYear") String FinancialYear,@Part("Month")String Month,@Part("Percipient")String Percipient,@Part("Recipe_Demonstrate")String Recipe_Demonstrate,@Part("Remarks")String Remarks,@Part("Category1")String Category1,@Part("Category2")String Category2,@Part("LocationID")String LocationID,@Part("Operation")String Operation,@Part("SecurityCode") String SecurityCode);
    @Multipart
    @POST("post_CompitetorSales")
    Call<UploadObject> postCompetitor(@Part("ZoneID")String  ZoneID,@Part("BranchID") String BranchID,@Part("AEMEmployeeID") String AEMEmployeeID,@Part("SalesDate") String SalesDate,@Part("FinancialYear") String FinancialYear,@Part("Month")String Month,@Part("Category")String Category,@Part("SecurityCode") String SecurityCode,@Part("CSRRemarks") String CSRRemarks);
    @Multipart
    @POST("post_EmployeeFeedbackQuestionAnswer")
    Call<UploadObject> postquestionanswer(@Part("AEMEmployeeID")String  AEMEmployeeID,@Part("FinacialYear") String FinacialYear,@Part("Month") String Month,@Part("Answer") String Answer,@Part("CreatedBy") String CreatedBy,@Part("SecurityCode") String SecurityCode);


    @Multipart
    @POST("post_EmployeeCategorywiseQuestion")
    Call<UploadObject> postAprilAnswer(@Part("AEMEmployeeId")String  AEMEmployeeID,@Part("FinacialYear") String FinacialYear,@Part("Month") String Month,@Part("Question") String Question,@Part("SecurityCode") String SecurityCode,@Part("Remarks") String Remarks);

    @Multipart
    @POST("post_SalesEntryWithImage")
    Call<UploadObject> postSale(@Part("TransNo")String TransNo,@Part("AEMEmployeeId")String AEMEmployeeID,@Part("SalesDate")String SalesDate,@Part("FinacialYear")String FinacialYear,@Part("Month")String Month,@Part("CategoryID")String CategoryID,@Part("Quantity")String Quantity,@Part("xmldata")String xmldata,@Part("UserID")String UserID,@Part("BranchID")String BranchID,@Part("ModelID")String ModelID,@Part("CustomerName")String CustomerName,@Part("CustomerPhNo")String CustomerPhNo,@Part("CustomerPinCode")String CustomerPinCode,@Part("CustomerEmail")String CustomerEmail,@Part("InvoiceNo")String InvoiceNo,@Part("FinanceScheme")String FinanceScheme,@Part("DeliveryAddress")String DeliveryAddress,@Part("FirstName")String FirstName,@Part("LastName")String LastName,@Part("CustomerAlternateNumber")String CustomerAlternateNumber,@Part("HouseNo")String HouseNo,@Part("StreetName")String StreetName,@Part("Landmark")String Landmark,@Part("Title")String Title,@Part("StateID")String StateID,@Part("City")String City,@Part("InvoiceValue")String InvoiceValue,@Part("Remarks")String Remarks,@Part("UnderExchange")String UnderExchange,@Part("Area")String Area,@Part("SalesEntryFlag")String SalesEntryFlag,@Part("SecurityCode")String SecurityCode,@Part MultipartBody.Part file);


    @Multipart
    @POST("post_SalesEntryWithImage")
    Call<UploadObject> postSaleWithImage( @Part("TransNo")String TransNo,@Part("AEMEmployeeID")String AEMEmployeeID,@Part("SalesDate")String SalesDate,@Part("FinancialYear")String FinancialYear,@Part("Month")String Month,@Part("CategoryID")String CategoryID,@Part("Quantity")String Quantity,@Part("xmldata")String xmldata,@Part("UserID")String UserID,@Part("BranchID")String BranchID,@Part("ModelID")String ModelID,@Part("CustomerName")String CustomerName,@Part("CustomerPhNo")String CustomerPhNo,@Part("CustomerPinCode")String CustomerPinCode,@Part("CustomerEmail")String CustomerEmail,@Part("InvoiceNo")String InvoiceNo,@Part("FinanceScheme")String FinanceScheme,@Part("DeliveryAddress")String DeliveryAddress,@Part("FirstName")String FirstName,@Part("LastName")String LastName,@Part("CustomerAlternateNumber")String CustomerAlternateNumber,@Part("HouseNo")String HouseNo,@Part("StreetName")String StreetName,@Part("Landmark")String Landmark,@Part("Title")String Title,@Part("StateID")String StateID,@Part("City")String City,@Part("InvoiceValue")String InvoiceValue,@Part("Remarks")String Remarks,@Part("UnderExchange")String UnderExchange,@Part("Area")String Area,@Part("SalesEntryFlag")String SalesEntryFlag,@Part("SecurityCode")String SecurityCode);




    @Multipart
    @POST("post_CustomerVisit")
    Call<UploadObject> customerVisit(@Part MultipartBody.Part file, @Part("AEMEmployeeID")String AEMEmployeeID,@Part("VisitDate")String VisitDate,@Part("ModelID")String ModelID,@Part("CustomerTitle")String CustomerTitle,@Part("CustomerFirstName")String CustomerFirstName,@Part("CustomerLastName")String CustomerLasttName,@Part("CustomerPhNo")String CustomerPhNo,@Part("CustomerAltNumber")String CustomerAltNumber,@Part("CustomerEmail")String CustomerEmail,@Part("CustomerPinCode")String CustomerPinCode,@Part("StateID")String StateID,@Part("CustomerCity")String CustomerCity,@Part("CustomerAddress")String CustomerAddress,@Part("CustomerArea")String CustomerArea,@Part("CustomerHouseNo")String CustomerHouseNo,@Part("CustomerStreetName")String CustomerStreetName,@Part("CustomerLandmark")String CustomerLandmark,@Part("CustomerRemarks")String CustomerRemarks,@Part("WithServiceEngineer")String WithServiceEngineer,@Part("ServiceEngFirstName")String ServiceEngFirstName,@Part("ServiceEngLastName")String ServiceEngLastName,@Part("ServiceEngPhNo")String ServiceEngPhNo,@Part("ServiceEngAltNumber")String ServiceEngAltNumber,@Part("GeoLocation")String GeoLocation,@Part("SecurityCode")String SecurityCode);



    @Multipart
    @POST("post_empdigitaldocument")
    Call<UploadObject> uploadDocument(@Part MultipartBody.Part file, @Part("AEMEmployeeID") String AEMEmployeeID, @Part("DocumentID") String DocumentID, @Part("ReferenceNo") String ReferenceNo, @Part("SecurityCode") String SecurityCode);


    @Multipart
    @POST("post_EmployeeDailyActivity")
    Call<UploadObject> postDailyLog(@Part MultipartBody.Part file, @Part("AEMEmployeeID") String AEMEmployeeID,@Part("ApprovalStatus") String ApprovalStatus, @Part("Remarks") String Remarks,@Part("Longitude") String Longitude,@Part("Latitude") String Latitude,@Part("Address") String Address,@Part("Year") String Year,@Part("Month") String Month,@Part("SecurityCode") String SecurityCode,@Part("FName") String FName);


    @Multipart
    @POST("post_EmployeeDailyActivityWithoutImage")
    Call<UploadObject> postWithOutImageDailyLog(@Part("AEMEmployeeID") String AEMEmployeeID,@Part("ApprovalStatus") String ApprovalStatus, @Part("Remarks") String Remarks,@Part("Longitude") String Longitude,@Part("Latitude") String Latitude,@Part("Address") String Address,@Part("Year") String Year,@Part("Month") String Month,@Part("SecurityCode") String SecurityCode,@Part("FName") String FName);


}
