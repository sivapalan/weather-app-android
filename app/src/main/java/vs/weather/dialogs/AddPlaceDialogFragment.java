package vs.weather.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import vs.weather.R;
import vs.weather.data.MyWeatherDataPlaces;
import vs.weather.models.WeatherData;
import vs.weather.services.WeatherDataHandler;

public class AddPlaceDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View v = inflater.inflate(R.layout.dialog_add_place, null);
        builder.setView(v)
                .setTitle(R.string.dialog_add_place_title)
                .setMessage(R.string.dialog_add_place_message)
                // Add action buttons
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText et = (EditText) v.findViewById(R.id.edittext_placepath);
                        String placePath = et.getText().toString();
                        MyWeatherDataPlaces.addPlace(placePath, new WeatherDataHandler.ICallback() {
                            @Override
                            public void call(WeatherData weatherData) {
                                Snackbar.make(v, "Added " + weatherData.getLocation().getName(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddPlaceDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}