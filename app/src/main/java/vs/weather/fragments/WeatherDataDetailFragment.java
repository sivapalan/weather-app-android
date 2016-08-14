package vs.weather.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vs.weather.data.MyWeatherDataPlaces;
import vs.weather.R;
import vs.weather.adapters.WeatherDataDetailAdapter;
import vs.weather.models.WeatherData;
import vs.weather.activities.WeatherDataDetailActivity;
import vs.weather.activities.WeatherDataListActivity;

/**
 * A fragment representing a single Location detail screen.
 * This fragment is either contained in a {@link WeatherDataListActivity}
 * in two-pane mode (on tablets) or a {@link WeatherDataDetailActivity}
 * on handsets.
 */
public class WeatherDataDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The content this fragment is presenting.
     */
    private WeatherData mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WeatherDataDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = MyWeatherDataPlaces.getMap().get(getArguments().getLong(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getLocation().getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weatherdata_detail, container, false);

        if (mItem != null) {
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_weatherdata_detail);
            recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(new WeatherDataDetailAdapter(getContext(), mItem.getForecast().getTabularForecastList()));
        }

        return rootView;
    }
}
