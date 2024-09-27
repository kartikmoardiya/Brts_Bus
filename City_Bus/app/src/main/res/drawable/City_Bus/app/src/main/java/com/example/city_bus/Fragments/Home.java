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
    ArrayList<String> allRoutes = new ArrayList<>();
    Bundle bundle = new Bundle();
    LinearLayout txtVisibility;

    TextView frgtHomeTxtStop;
    String src;
    String dest;
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

        retrofitCallForAllRoute();


        String t1, t2;
        btnFindRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                src = autoViewFromLocation.getText().toString();
                dest = autoViewToLocation.getText().toString();

                if (src == null || dest == null) {
                    Toast.makeText(getActivity(), "Enter Src And Dest", Toast.LENGTH_SHORT).show();
                } else {
                    PathRequest pathReq = new PathRequest(src, dest);
                    Call<Path> call = Instancee.getService().getPath(pathReq);
                    call.enqueue(new Callback<Path>() {
                        @Override
                        public void onResponse(Call<Path> call, Response<Path> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                path = response.body();
                                if (response.body().getPath().size() > 0) {
                                    Log.d("Done", " A " + response.body().getPath().size());
                                    sepration(path);

                                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                    HomeAdapter homeAdapter = new HomeAdapter(busAndStations, getActivity());
                                    recyclerView.setAdapter(homeAdapter);
                                    frgtHomeTxtStop.setText(path.getPath().size() + "");
                                    txtVisibility.setVisibility(View.VISIBLE);
                                    // Print All Bus And Stations
                                    for (int i = 0; i < busAndStations.size(); i++) {
                                        Log.d("Tagy", busAndStations.get(i).first + " --> A " + busAndStations.get(i).second);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Path> call, Throwable t) {

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
                    allRoutes.addAll(response.body().getAllStations());
                    Log.d("Tagy", "A" + arrayList.size());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, allRoutes);
                    autoViewFromLocation.setAdapter(adapter);
                    autoViewToLocation.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<AllRoutes> call, Throwable t) {

            }
        });
    }

    String tmpKey = "";
    String s = "";
    String d = "";
    HashMap<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> map = new HashMap<>();

    private void sepration(Path pathh) {
        map.clear();
        String station = "";
        List<PathResponce> pathResponce = pathh.getPath();
        Log.d("Tagy", "A " + pathResponce.size() + " sorry");
        // Initialize Hashmap
        for (int i = 0; i < pathResponce.size(); i++) {
            for (int j = 0; j < pathResponce.get(i).getBusNumbers().size(); j++) {

                if (!map.containsKey(pathResponce.get(i).getBusNumbers().get(j))) {
//                    Log.d("Tagy", "A Null " + pathResponce.size());
                    map.put(pathResponce.get(i).getBusNumbers().get(j), new Pair<>(new Pair<>(pathResponce.get(i).getDestStation(), i), new Pair<>(pathResponce.get(i).getDestStation(), i)));
                } else {
//                    Log.d("Tagy", "A Null Not");
                    String firstKey = map.get(pathResponce.get(i).getBusNumbers().get(j)).first.first;
                    Integer secondKey = map.get(pathResponce.get(i).getBusNumbers().get(j)).first.second;
                    map.put(pathResponce.get(i).getBusNumbers().get(j), new Pair<>(new Pair<>(firstKey, secondKey), new Pair<>(pathResponce.get(i).getDestStation(), i)));
                }
                Log.d("Tagy", "A" + map.size() + "hello");

            }
        }

//        Log.d("Tagy","A"+map.size()+"hello");
        mappingOfBus(pathResponce);
    }

    ArrayList<Pair<String, Pair<String, String>>> arrayList = new ArrayList<>();
    ArrayList<Pair<String, String>> busAndStations = new ArrayList<>();

    // Find bus Which is travel Max Station
    private void mappingOfBus(List<PathResponce> pathResponce) {
        arrayList.clear();

        // Convert HashMap to a List of entries
        List<Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>>> list = new LinkedList<>(map.entrySet());

        // Sort the list based on the Integer value of the first pair
        Collections.sort(list, new Comparator<Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>>>() {
            public int compare(Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> o1,
                               Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> o2) {
//                return o1.getValue().getKey().getValue().compareTo(o2.getValue().getKey().getValue());
                return o1.getValue().first.second.compareTo(o2.getValue().first.second);
            }
        });

        // Convert sorted list back to a LinkedHashMap to maintain the insertion order
        LinkedHashMap<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        // Display the sorted map
        System.out.println("Sorted Map:");
        for (Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> entry : sortedMap.entrySet()) {
            Log.d("Tagy", "A" + entry.getKey() + ": " + entry.getValue());
        }

        Boolean f = false;
        for (Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            Pair<Pair<String, Integer>, Pair<String, Integer>> value = entry.getValue();

            // Access nested elements within the value Pair
            Pair<String, Integer> firstPair = value.first;
            String firstKey = firstPair.first;
            Integer firstValue = firstPair.second;

            Pair<String, Integer> secondPair = value.second;
            String secondKey = secondPair.first;
            Integer secondValue = secondPair.second;
            Log.d("Tagy", "A " + key + " " + firstKey + " " + firstValue + " " + secondKey + " " + secondValue);
            int mx = 0;
            String tmpKey = null, tmpSrc = "", tmpDest = "";

            for (Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> i : sortedMap.entrySet()) {
                String key1 = entry.getKey();
                Pair<Pair<String, Integer>, Pair<String, Integer>> value1 = entry.getValue();

                // Access nested elements within the value Pair
                Pair<String, Integer> firstPair1 = value.first;
                String firstKey1 = firstPair.first;
                Integer firstValue1 = firstPair.second;

                Pair<String, Integer> secondPair1 = value.second;
                String secondKey1 = secondPair.first;
                Integer secondValue1 = secondPair.second;
//                    Log.d("Tagy","A "+key+" "+firstKey+" "+firstValue+" "+secondKey+" "+secondValue);
                if (firstKey1 == firstKey) {
                    if (secondValue1 - firstValue1 > mx) {
                        tmpKey = key1;
                        tmpSrc = firstKey1;
                        tmpDest = secondKey1;
                        mx = secondValue1 - firstValue1;
                    }
                }
                if (secondKey1.equalsIgnoreCase(dest) && secondKey1 != null) {
                    f = true;
                    break;
                }
            }
            if (tmpKey != null && tmpSrc != null && tmpDest != null) {
                arrayList.add(new Pair<>(tmpKey, new Pair<>(tmpSrc, tmpDest)));
            }
            if (f) {
                break;
            }
        }

        for (Pair<String, Pair<String, String>> pair : arrayList) {
            String key = pair.first;
            String value1 = pair.second.first;
            String value2 = pair.second.second;
            Log.d("Tagy", "A" + "Key: " + key + ", Value1: " + value1 + ", Value2: " + value2);
        }

        appendStations(pathResponce);
    }

    // Append all Stations
    private void appendStations(List<PathResponce> pathResponce) {
        busAndStations.clear();
        String appendd = "";

        int k = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            String busSrc = arrayList.get(i).second.first;
            String busDest = arrayList.get(i).second.second;

            Boolean f = false;
            for (int j = k; j < pathResponce.size(); j++) {
                if ((pathResponce.get(j).getDestStation()).equalsIgnoreCase(busSrc)) {
                    f = true;
                }
                if (f) {
                    if (!(pathResponce.get(j).getDestStation()).equalsIgnoreCase(busDest)) {
                        appendd += (pathResponce.get(j).getDestStation() + " -> ");
                    } else {
                        appendd += pathResponce.get(j).getDestStation();
                    }
                }
                if ((pathResponce.get(j).getDestStation()).equalsIgnoreCase(busDest)) {
//                    Log.d("Tagy",appendd +" A");
                    busAndStations.add(new Pair<>(arrayList.get(i).first, appendd));
                    appendd = busDest + " -> ";
                    k = k + 1;
                    break;
                }

            }
        }
    }


}