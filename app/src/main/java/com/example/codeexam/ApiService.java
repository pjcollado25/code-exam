package com.example.codeexam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("8e75cfa5-a84d-47b1-b2f2-f7a756bfc27d")
    Call<ResponseData> createUser(@Body DataModel dataModel);
    class ResponseData {
        private String message;

        public String getMessage() {
            return message;
        }
    }
}
