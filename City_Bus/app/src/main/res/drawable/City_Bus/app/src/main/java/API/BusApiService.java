package API;

import Models.AllRoutes;
import Models.BusDetail;
import Models.ForgatePass;
import Models.LoginRequest;
import Models.Path;
import Models.PathRequest;
import Models.Token;
import Models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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

}
