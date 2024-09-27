package API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Instancee {
    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://city-bus.vercel.app/";

    public static BusApiService getService()
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(BusApiService.class);
    }
}
