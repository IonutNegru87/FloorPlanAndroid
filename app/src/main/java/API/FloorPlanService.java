package API;

import ApiResponse.FavouriteResponse;
import ApiResponse.LoginResponse;
import ApiResponse.Registration;
import ApiResponse.PlanResponse;
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

    @GET("search.php")
    Call<PlanResponse> searchPlan(
            @Query("bathrooms") String bathrooms,
            @Query("bedrooms") String bedrooms,
            @Query("toilets") String toilets,
            @Query("garages") String garages,
            @Query("user_id") String user_id,
            @Query("is_purchase") String is_purchase
    );

    @GET("get_fav.php")
    Call<PlanResponse> getFavPlan(
            @Query("user_id") String user_id
    );

    @GET("favoraties.php")
    Call<FavouriteResponse> markFavourite(
            @Query("user_id") String user_id,
            @Query("plan_id") String plan_id
    );

}
