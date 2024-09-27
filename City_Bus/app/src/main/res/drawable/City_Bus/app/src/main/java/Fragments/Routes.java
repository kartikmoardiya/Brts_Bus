package Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.city_bus.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import API.Instancee;
import Adapter.StationAdapter;
import Models.BusDetail;
import Models.Station;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Routes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Routes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Routes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Routes.
     */
    // TODO: Rename and change types and number of parameters
    public static Routes newInstance(String param1, String param2) {
        Routes fragment = new Routes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recyclerView;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);
        recyclerView = view.findViewById(R.id.routeRecycler);
        textView = view.findViewById(R.id.tmp);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRetrofit();
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                StationAdapter adapter = new StationAdapter(stations,getActivity());
                recyclerView.setAdapter(adapter);
            }

        });
        return view;
    }


    ArrayList<Station> stations = new ArrayList<>();
    String ala = "1D";

    private void callRetrofit() {
        Call<BusDetail> call = Instancee.getService().getAllStations(ala);

        call.enqueue(new Callback<BusDetail>() {
            @Override
            public void onResponse(Call<BusDetail> call, Response<BusDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stations = (ArrayList<Station>) response.body().getBusStations().getStations();
                }
            }

            @Override
            public void onFailure(Call<BusDetail> call, Throwable t) {

            }
        });
    }
}