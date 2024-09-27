package com.example.city_bus.API;

import com.example.city_bus.Models.AllBuses;
import com.example.city_bus.Models.ForgetPassResp;
import com.example.city_bus.Models.Path;
import com.example.city_bus.Models.PathRequest;
import com.example.city_bus.Models.Token;
import com.example.city_bus.Models.AllRoutes;
import com.example.city_bus.Models.BusDetail;
import com.example.city_bus.Models.ForgatePass;
import com.example.city_bus.Models.LoginRequest;
import com.example.city_bus.Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface BusApiService {

    // Login User
    @POST("/user/login")
    Call<Token> userLogin(@Body LoginRequest loginRequest);

    // SignUp User
    @POST("/user/signup")
    Call<Token> userSingUp(@Body User user);

    @PUT("/user/profile/password")
    Call<String> userPut(@Body ForgatePass pass);

    // Get
    @GET("/allstations")
    Call<AllRoutes> getAllRoutes();

    @POST("/route/path")
    Call<Path> getPath(@Body PathRequest pathRequest);

    @GET("/allstations/{bus}")
    Call<BusDetail> getAllStations(@retrofit2.http.Path("bus") String bus);

    @GET("/allstations/1D")
    Call<BusDetail> getStations();

    @GET("/bus")
    Call<AllBuses> getAllBuses();

    @PUT("/user/profile/password")
    Call<ForgetPassResp> frgtPass(@Body ForgatePass forgatePass);

}
