package vs.weather.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import vs.weather.data.MyWeatherDataPlaces;
import vs.weather.R;
import vs.weather.adapters.WeatherDataListAdapter;
import vs.weather.dialogs.AddPlaceDialogFragment;
import vs.weather.models.WeatherData;
import vs.weather.services.WeatherDataHandler;

public class WeatherDataListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weatherdata_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPlaceDialogFragment dialog = new AddPlaceDialogFragment();
                dialog.show(getSupportFragmentManager(), "AddPlaceDialogFragment");
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.places_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new WeatherDataListAdapter(this, MyWeatherDataPlaces.getList());
        mRecyclerView.setAdapter(mAdapter);

        if (findViewById(R.id.weatherdata_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the activity should be in two-pane mode.
            ((WeatherDataListAdapter) mAdapter).setTwoPane(true);
        }

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item
                final WeatherData wd = MyWeatherDataPlaces.getList().get(viewHolder.getAdapterPosition());
                MyWeatherDataPlaces.remove(wd);
                Snackbar undoSnackbar = Snackbar
                    .make(mRecyclerView, wd.getLocation().getName() + " was removed", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyWeatherDataPlaces.undoLastRemove();
                            Snackbar redoConfirmationSnackbar = Snackbar.make(mRecyclerView, "Restored", Snackbar.LENGTH_SHORT);
                            redoConfirmationSnackbar.show();
                        }
                    });

                undoSnackbar.show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_btn_refresh:
                MyWeatherDataPlaces.refreshAll(new WeatherDataHandler.ICallback() {
                    @Override
                    public void call(WeatherData weatherData) {
                        if (weatherData != null) {
                            Snackbar.make(mRecyclerView, R.string.refresh_success, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                return true;
            case R.id.menu_btn_about:
                // TODO: Display 'About' info
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
