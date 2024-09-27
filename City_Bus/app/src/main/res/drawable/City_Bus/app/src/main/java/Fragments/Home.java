package Fragments;

import android.app.Dialog;
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

import com.example.city_bus.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import API.Instancee;
import Adapter.HomeAdapter;
import Models.Path;
import Models.PathRequest;
import Models.PathResponce;
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
    String src = "LD Engg College";
    String dest = "Ghuma Gam";
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

        allRoutes = bundle.getStringArrayList("allRoutes");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, allRoutes);
        autoViewFromLocation.setAdapter(adapter);
        autoViewToLocation.setAdapter(adapter);
        autoViewFromLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tagy", "A " + allRoutes.size());
            }
        });

        btnFindRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                Log.d("Done", "A");
                                sepration(path);

                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                HomeAdapter homeAdapter = new HomeAdapter(busAndStations, getActivity());
                                recyclerView.setAdapter(homeAdapter);
                                frgtHomeTxtStop.setText(path.getPath().size() + "");
                                txtVisibility.setVisibility(View.VISIBLE);
                                // Print All Bus And Stations
//                                Log.d("Tagy")
//                                 for (int i = 0; i < busAndStations.size(); i++) {
//                                     Log.d("Tagy", busAndStations.get(i).first+" --> A "+busAndStations.get(i).second);
//                                 }
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

    String tmpKey = "";
    String s = "";
    String d = "";
    HashMap<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> map = new HashMap<>();

    private void sepration(Path pathh) {
        map.clear();
        String station = "";
        List<PathResponce> pathResponce = pathh.getPath();
        Log.d("Tagy", "A " + map.size());
        // Initialize Hashmap
        for (int i = 0; i < pathResponce.size(); i++) {
            for (int j = 0; j < pathResponce.get(i).getBusNumbers().size(); j++) {

                if (!map.containsKey(pathResponce.get(i).getBusNumbers().get(j))) {
                    Log.d("Tagy", "A Null " + pathResponce.size());
                    map.put(pathResponce.get(i).getBusNumbers().get(j), new Pair<>(new Pair<>(pathResponce.get(i).getDestStation(), i), new Pair<>(pathResponce.get(i).getDestStation(), i)));
                } else {
                    Log.d("Tagy", "A Null Not");
                    String firstKey = map.get(pathResponce.get(i).getBusNumbers().get(j)).first.first;
                    Integer secondKey = map.get(pathResponce.get(i).getBusNumbers().get(j)).first.second;
                    map.put(pathResponce.get(i).getBusNumbers().get(j), new Pair<>(new Pair<>(firstKey, secondKey), new Pair<>(pathResponce.get(i).getDestStation(), i)));
                }
            }
        }

        mappingOfBus(pathResponce);
    }

    ArrayList<Pair<String, Pair<String, String>>> arrayList = new ArrayList<>();
    ArrayList<Pair<String, String>> busAndStations = new ArrayList<>();

    // Find bus Which is travel Max Station
    private void mappingOfBus(List<PathResponce> pathResponce) {
        arrayList.clear();
        int ii = 0, jj = 0;
        int n = map.size();
        while (n != 0) {
            int mx = 0;
            for (Map.Entry<String, Pair<Pair<String, Integer>, Pair<String, Integer>>> entry : map.entrySet()) {
                String key = entry.getKey();
                Pair<Pair<String, Integer>, Pair<String, Integer>> value = entry.getValue();

                // Access nested elements within the value Pair
                Pair<String, Integer> firstPair = value.first;
                String firstKey = firstPair.first;
                Integer firstValue = firstPair.second;

                Pair<String, Integer> secondPair = value.second;
                String secondKey = secondPair.first;
                Integer secondValue = secondPair.second;
                if (firstValue <= ii) {
                    if (secondValue - firstValue >= mx) {
                        tmpKey = key;
                        s = firstKey;
                        d = secondKey;
                        mx = secondValue - firstValue;
                        jj = secondValue;

                        Log.d("Tagy", " A ");
                    }

                }
            }

            ii = jj + 1;
            arrayList.add(new Pair<>(tmpKey, new Pair<>(s, d)));
            Log.d("Tagy", dest + " A " + d + " -> " + tmpKey);
            n = n - 1;
//            if(d.equalsIgnoreCase("Shivranjani"))
//            {
//                break;
//            }
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