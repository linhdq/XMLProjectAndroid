package com.ldz.fpt.xmlprojectandroid.network;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by linhdq on 2/22/17.
 */

public interface GetService {
    @POST(API.LOGIN)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlLogin(@Body RequestBody data);

    @POST(API.GET_ALL_CLIENTS)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllClients(@Body RequestBody data);

    @POST(API.GET_ALL_ADMINS)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllAdmins(@Body RequestBody data);

    @POST(API.UPDATE_USER)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlUpdateUser(@Body RequestBody data);

    @POST(API.GET_ALL_BA_CANG_DATA)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllBaCangData(@Body RequestBody data);

    @POST(API.GET_ALL_LO_DATA)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllLoData(@Body RequestBody data);

    @POST(API.GET_ALL_LO_XIEN_2_DATA)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllLoXien2Data(@Body RequestBody data);

    @POST(API.GET_ALL_LO_XIEN_3_DATA)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllLoXien3Data(@Body RequestBody data);

    @POST(API.GET_ALL_LO_XIEN_4_DATA)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllLoXien4Data(@Body RequestBody data);

    @POST(API.GET_ALL_DE_DATA)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllDeData(@Body RequestBody data);

    //
    @POST(API.GET_ALL_BA_CANG_BY_DATE)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllBaCangByDate(@Body RequestBody data);

    @POST(API.GET_ALL_LO_BY_DATE)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllLoByDate(@Body RequestBody data);

    @POST(API.GET_ALL_LO_XIEN_2_BY_DATE)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllLoXien2ByDate(@Body RequestBody data);

    @POST(API.GET_ALL_LO_XIEN_3_BY_DATE)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllLoXien3ByDate(@Body RequestBody data);

    @POST(API.GET_ALL_LO_XIEN_4_BY_DATE)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllLoXien4ByDate(@Body RequestBody data);

    @POST(API.GET_ALL_DE_BY_DATE)
    @Headers({API.HEADERS})
    Call<ResponseBody> callXmlGetAllDeByDate(@Body RequestBody data);

    @POST(API.DELETE_ACCOUNT_ADMIN)
    @Headers({API.HEADERS})
    Call<ResponseBody> callDeleteAccountAdmin(@Body RequestBody data);
}
