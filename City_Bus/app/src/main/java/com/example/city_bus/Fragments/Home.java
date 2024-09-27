package com.example.city_bus.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.city_bus.Models.Path;
import com.example.city_bus.Models.PathRequest;
import com.example.city_bus.Models.PathResponce;
import com.example.city_bus.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.example.city_bus.API.Instancee;
import com.example.city_bus.Adapter.HomeAdapter;
import com.example.city_bus.Models.AllRoutes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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

    AutoCompleteTextView autoViewFromLocation, autoViewToLocation;
    RecyclerView recyclerView;
    Button btnFindRoute;

    // Badha Station Aama Store Thay chhe
    ArrayList<String> allRoutes = new ArrayList<>();

    LinearLayout txtVisibility;

    TextView frgtHomeTxtStop, homeTxtShowAfter, homeTxtShowFirst;
    String src;
    String dest;

    // Rsponce Path Ma Store Thay chhe
    Path path = new Path();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        autoViewFromLocation = view.findViewById(R.id.autoViewFromLocation);
        autoViewToLocation = view.findViewById(R.id.autoViewToLocation);
        btnFindRoute = view.findViewById(R.id.btnFindRoute);
        recyclerView = view.findViewById(R.id.recycler_view);
        frgtHomeTxtStop = view.findViewById(R.id.frgtHomeTxtStop);
        txtVisibility = view.findViewById(R.id.txtVisibility);
        homeTxtShowAfter = view.findViewById(R.id.homeTxtShowAfter);
        homeTxtShowFirst = view.findViewById(R.id.homeTxtShowFirst);

        // Badha Station No Responce Leva Mate Nu Function
        retrofitCallForAllRoute();


        // find button pr click kre tyare
        btnFindRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // source and destination n string ma lidha
                src = autoViewFromLocation.getText().toString();
                dest = autoViewToLocation.getText().toString();


                // check kru k input null chhe k ny
                if (src == null || dest == null||(src.equalsIgnoreCase(dest))) {
                    Toast.makeText(getActivity(), "Srkhu Enter Kr n Mara Bhai", Toast.LENGTH_SHORT).show();
                } else {
                    // Body na input mate object banavo API Request mate
                    PathRequest pathReq = new PathRequest(src, dest);
                    Call<Path> call = Instancee.getService().getPath(pathReq);
                    call.enqueue(new Callback<Path>() {
                        @Override
                        public void onResponse(Call<Path> call, Response<Path> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                path = response.body();
                                if (response.body().getPath().size() > 0) {

                                    // Mota Ma Moti Lap Responce aava p6i aama chhe --> responce thi path vayavo chhe aapdi pase have bus n related arrange krvanu chhe ene
                                    mappinigOfInMap(path);

                                    // Badhu Logic Thay Jay P6i Xml ma set kru
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    HomeAdapter homeAdapter = new HomeAdapter(busAndStations, getActivity());
                                    recyclerView.setAdapter(homeAdapter);
                                    frgtHomeTxtStop.setText(path.getPath().size() + "");
                                    homeTxtShowAfter.setText("Journey From " + src + " To " + dest);
                                    homeTxtShowFirst.setVisibility(View.GONE);
                                    txtVisibility.setVisibility(View.VISIBLE);

                                }
                            }
                            else{
                                Toast.makeText(getActivity(), "Tu Chinta No Kr Internal Problem chhe", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Path> call, Throwable t) {
                            Toast.makeText(getActivity(), "Tu Chinta No Kr Internal Problem chhe", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return view;
    }

    private void retrofitCallForAllRoute() {
        Call<AllRoutes> call = Instancee.getService().getAllRoutes();

        call.enqueue(new Callback<AllRoutes>() {
            @Override
            public void onResponse(Call<AllRoutes> call, Response<AllRoutes> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Dar Vakhte App Khole Atle Mutiple Station no aave atle clear kri n store kru
                    allRoutes.clear();
                    allRoutes.addAll(response.body().getAllStations());

                    // Badha station no responce lay ne adpter ma set kri nakhu autocomplete textview mat
                    if (getContext() != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, allRoutes);
                        autoViewFromLocation.setAdapter(adapter);
                        autoViewToLocation.setAdapter(adapter);
                    }
                }else{
                    Toast.makeText(getActivity(), "Tu Chinta No Kr Internal Problem chhe", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AllRoutes> call, Throwable t) {
                Toast.makeText(getActivity(), "Tu Chinta No Kr Internal Problem chhe", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Bus nu mapping krva banave means k perticular bus starting and ending station janva
    // Bus < Number, < String Station Name, Index From Responce >, < Ending Station Name, Index From Responce > >
    HashMap<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> map = new HashMap<>();

    // Mapping p6i map n sort kri n aama store kravvanu chhe
    // Bus < Number, < String Station Name, Index From Responce >, < Ending Station Name, Index From Responce > >
    ArrayList<Pair<String, Pair<Pair<String, Integer>, Pair<String, Integer>>>> v = new ArrayList<>();

    private void mappinigOfInMap(Path pathh) {

        // Map n clear kro multiple responce ne eleminate krva mate
        map.clear();

        // Path Mathi Main Path Get Kri aama store kru chhe
        List<PathResponce> pathResponce = pathh.getPath();

        // Initialize Hashmap
        for (int i = 0; i < pathResponce.size(); i++) {
            for (int j = 0; j < pathResponce.get(i).getBusNumbers().size(); j++) {

                if (!map.containsKey(pathResponce.get(i).getBusNumbers().get(j))) {
                    map.put(pathResponce.get(i).getBusNumbers().get(j), new Pair<>(new Pair<>(pathResponce.get(i).getDestStation(), i), new Pair<>(pathResponce.get(i).getDestStation(), i)));
                } else {
                    String firstKey = map.get(pathResponce.get(i).getBusNumbers().get(j)).first.first;
                    Integer secondKey = map.get(pathResponce.get(i).getBusNumbers().get(j)).first.second;
                    map.put(pathResponce.get(i).getBusNumbers().get(j), new Pair<>(new Pair<>(firstKey, secondKey), new Pair<>(pathResponce.get(i).getDestStation(), i)));
                }
            }
        }


        mappingOfBus(pathResponce);
    }

    // After Sorting buses Store busnumber starting station and ending station
    ArrayList<Pair<String, Pair<String, String>>> arrayList = new ArrayList<>();

    ArrayList<Pair<String, String>> busAndStations = new ArrayList<>();

    // Helper Bus n sorting krva maximum kadhva
    int mx = -1; // max station kadhva perticular bus na
    int z = 0; // first station nu patavi ne ene p6i ni index p6i first station na stations mate use thay
    int isCheck = -1; // ek type nu remebere variable jvu kam kre chhe sorting ma
    String sortingBusName, startingStation, endingStation;

    // Find bus Which is travel Max Station
    private void mappingOfBus(List<PathResponce> pathResponce) {
        arrayList.clear();
        v.clear();
        mx = -1;
        z = 0;
        isCheck = -1;
        sortingBusName = null;
        startingStation = null;
        endingStation = null;

        // Sort the map based on the value of o1.getValue().second.second
        List<Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>>> sortedEntries = new ArrayList<>(map.entrySet());
        Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>>>() {
            @Override
            public int compare(Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> o1, Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> o2) {
                // Compare based on the value of o1.getValue().second.second
                Integer value1 = o1.getValue().second.second;
                Integer value2 = o2.getValue().second.second;

                // Higher values have higher priority
                return value2.compareTo(value1);
            }
        });

        // Display the sorted map
        for (Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> entry : sortedEntries) {
            System.out.println(entry.getKey() + " : " + entry.getValue().first + " " + entry.getValue().second);
        }


        // After Sorting Map Store its element in arraylist for easy traversing
        for (Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> i : sortedEntries) {

            String key = i.getKey();
            Pair<Pair<String, Integer>, Pair<String, Integer>> value = i.getValue();

            Pair<String, Integer> firstPair = value.first;
            String firstKey = firstPair.first;
            Integer firstValue = firstPair.second;

            Pair<String, Integer> secondPair = value.second;
            String secondKey = secondPair.first;
            Integer secondValue = secondPair.second;

            v.add(new Pair<>(key, new Pair<>(new Pair<>(firstKey, firstValue), new Pair<>(secondKey, secondValue))));
        }

        // aapde ending station thi sru kru chhe atle ending station thi jtli buses upadti hoy e busma ky bus max to mx station cover kre chhe te first bus n store krvani with starting and ending station
        for (int j = 0; j <= v.size() - 1; j++) {
            Pair<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> e = v.get(j);
            String cKey = e.first;
            String cFirstKey = e.second.first.first;
            Integer cFirstValue = e.second.first.second;
            String cSecondKey = e.second.second.first;
            Integer cSecondValue = e.second.second.second;


            if (cSecondValue == (pathResponce.size() - 1)) {
                if ((cSecondValue - cFirstValue) >= mx) {
                    sortingBusName = cKey;
                    startingStation = cFirstKey;
                    endingStation = cSecondKey;
                    isCheck = cFirstValue; // bus mali gy p6i eni first key thi bakina station ni bus malse atle --> right to left aapde check krvi 6vi index atle left side no store kru
                    mx = cSecondValue - cFirstValue;
                }

            } else {
                z = j; // first station nu pti gyu atle eni p6i na station n itrete krvanu chhe atle ene store kri lidhu
                break;
            }
        }

        // first station ni bus no data aama store kro chhe
        arrayList.add(new Pair<>(sortingBusName, new Pair<>(startingStation, endingStation)));

        // aa condition no meaning k eky bus src to dest direct nthi jati
        if (isCheck != 0) {

            for (int i = z; i < v.size(); i++) {
                // j station pr chhavi eni upar ni badhi bus lay lidhu have e bus na last station means k max to max ktla station cover kre chhe e check kri n e bus na max station no index goti n isCheck ma store karavi n frithi e station prni badhi bus gotvani
                List<String> buses = pathResponce.get(isCheck).getBusNumbers();

                // khali index parna station nu nam lidhu
                String singleStation = pathResponce.get(isCheck).getDestStation();
                mx = -1;
                sortingBusName = "";
                startingStation = "";
                endingStation = "";

                // current station pr ni bus itreate thay chhe
                for (int j = 0; j < buses.size(); j++) {
                    String singleBus = buses.get(j).toString();

                    // current bus n v ma store kreli bus sathe sarkhavu ane jo current station pr ni bus "v" ma hoy to e badhi bus nu max to max station kadhu ane store kru
                    for (int k = 0; k < v.size(); k++) {
                        String vectorSingleBus = v.get(k).first;
                        String firstKey = v.get(k).second.first.first;
                        Integer firstValue = v.get(k).second.first.second;
                        if (singleBus.equalsIgnoreCase(vectorSingleBus)) {
                            if(isCheck - firstValue >= mx)
                            {
                                sortingBusName = vectorSingleBus;
                                startingStation = singleStation;
                                endingStation = firstKey;
                                mx = isCheck - firstValue;
                                isCheck = firstValue;
                            }
                            break;
                        }
                    }
                }

                // Store the data
                arrayList.add(new Pair<>(sortingBusName, new Pair<>(endingStation, startingStation)));

                // Means k pochhi gya majiz pr km k aapde last thi sru kru tu atle isCheck = 0 dest this src pochhi gya
                if(isCheck == 0)
                {
                    break;
                }
            }
        }

        // busu mali gy sortest vali ane ena starting and ending destion have ene ek string ma append kri n textview ma store krvanu chhe
        appendStations(pathResponce);
    }

    // Append all Stations
    private void appendStations(List<PathResponce> pathResponce) {
        busAndStations.clear();
        String appendd = "";

        // evdi e bus na station ni sruvat thay atle aa true thay jay means k busu na connection pr true that atle append ma new bus add thava mande
        String help = src;
        for (int i = arrayList.size() -1; i >=0; i--) {
            String busSrc = arrayList.get(i).second.first;
            String busDest = arrayList.get(i).second.second;

            Boolean f = false;
            appendd = "";
            for (int j = 0; j < pathResponce.size(); j++) {
                if ((pathResponce.get(j).getDestStation()).equals(help)) {
                    f = true;
                }
                if (f) {
                    if (!(pathResponce.get(j).getDestStation()).equals(busDest)) {
                        appendd += (pathResponce.get(j).getDestStation() + " -> ");
                    } else {
                        appendd += pathResponce.get(j).getDestStation();
                    }
                }
                if ((pathResponce.get(j).getDestStation()).equals(busDest)) {
                    help = pathResponce.get(j).getDestStation();
                    busAndStations.add(new Pair<>(arrayList.get(i).first, appendd));
                    break;
                }

            }
        }
    }
}
