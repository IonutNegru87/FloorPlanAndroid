package API;

import ApiResponse.LoginResponse;
import ApiResponse.Registration;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FloorPlanService {
    @GET("user_registeration.php")
    Call<Registration> registerUser(
            @Query("email") String email,
            @Query("password") String password,
            @Query("device_type") String deviceType,
            @Query("device_token") String deviceToken,
            @Query("app_id") String appID,
            @Query("lat") String latitude,
            @Query("longt") String longitude
    );

    @GET("user_login.php")
    Call<LoginResponse> loginUser(
            @Query("email") String email,
            @Query("password") String password,
            @Query("device_token") String deviceToken,
            @Query("device_type") String deviceType,
            @Query("app_id") String appID
    );
}
