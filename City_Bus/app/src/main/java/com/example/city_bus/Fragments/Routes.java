package com.example.city_bus.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.city_bus.API.Instancee;
import com.example.city_bus.Adapter.StationAdapter;
import com.example.city_bus.Models.AllBuses;
import com.example.city_bus.Models.BusDetail;
import com.example.city_bus.Models.Station;
import com.example.city_bus.R;

import java.util.ArrayList;

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

    AutoCompleteTextView routeEdt;
    RecyclerView recyclerView;
    TextView routeTotalStops;
    Button routeBtnSeek;
    LinearLayout routeHide;
    String busName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);
        recyclerView = view.findViewById(R.id.routeRecycler);
        routeBtnSeek = view.findViewById(R.id.routeBtnSeek);
        routeEdt = view.findViewById(R.id.routeEdt);
        routeHide = view.findViewById(R.id.routeHide);
        routeTotalStops = view.findViewById(R.id.routeTotalStops);

        // already aama data chhe to shu kam call krvutu multiple batave
        if (allBus.size() == 0) {
            retrofitCallForAllBus();
        }

        // find button pr click krvathi
        routeBtnSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busName = routeEdt.getText().toString();

                // check kru k busname null chhe k ny
                if (busName != null) {
                    callRetrofit();
                }
            }

        });

        return view;
    }

    // badhi bus no data aama store thahe means k badhi bus na nam
    ArrayList<String> allBus = new ArrayList<>();

    private void retrofitCallForAllBus() {
        allBus.clear();
        Call<AllBuses> call = Instancee.getService().getAllBuses();

        call.enqueue(new Callback<AllBuses>() {
            @Override
            public void onResponse(Call<AllBuses> call, Response<AllBuses> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allBus = (ArrayList<String>) response.body().getBuses();
                    if (getActivity() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, allBus);
                        routeEdt.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getActivity(), "Tu Chinta No Kr Internal Problem chhe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllBuses> call, Throwable t) {
                Toast.makeText(getActivity(), "Tu Chinta No Kr Internal Problem chhe", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // all station correspoding to name of enter bus
    ArrayList<Station> stations = new ArrayList<>();

    private void callRetrofit() {
        stations.clear();
        Call<BusDetail> call = Instancee.getService().getAllStations(busName);

        call.enqueue(new Callback<BusDetail>() {
            @Override
            public void onResponse(Call<BusDetail> call, Response<BusDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    stations = (ArrayList<Station>) response.body().getBusStations().getStations();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    // badha station n recycler view m set kara chhe
                    StationAdapter adapter = new StationAdapter(stations, getActivity());
                    recyclerView.setAdapter(adapter);
                    routeTotalStops.setText("" + stations.size());
                    routeHide.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(getActivity(), "Tu Chinta No Kr Internal Problem chhe", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BusDetail> call, Throwable t) {
                Toast.makeText(getActivity(), "Tu Chinta No Kr Internal Problem chhe", Toast.LENGTH_SHORT).show();
            }
        });
    }
}