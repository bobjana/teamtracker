package za.co.zynafin.teamtracker.account;

import retrofit.http.POST;
import retrofit.http.Query;
import za.co.zynafin.teamtracker.account.AuthToken;

public interface LoginService {

    @POST("/api/authenticate")
    AuthToken authenticate(@Query("username") String username, @Query("password")String password);

}
