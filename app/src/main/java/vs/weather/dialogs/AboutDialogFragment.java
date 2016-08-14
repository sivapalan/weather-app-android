package vs.weather.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.webkit.WebView;
import android.widget.LinearLayout;

import vs.weather.R;

public class AboutDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

            WebView wv = new WebView(context);

            String htmlData = "<html><body><p>Weather forecast from <a href=\"http://www.yr.no\">yr.no</a>," +
                    " delivered by the Norwegian Meteorological Institute and the NRK.</p></body></html>";
            wv.loadData(htmlData, "text/html; charset=utf-8", "utf-8");

            float sizeInDp = 15;
            float scale = context.getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (sizeInDp * scale + 0.5f);

            LinearLayout ll = new LinearLayout(context);
            ll.addView(wv);
            ll.setPadding(dpAsPixels, 0, dpAsPixels, 0);

            builder.setNegativeButton(context.getString(R.string.close), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setTitle(R.string.about)
                    .setView(ll);
        return builder.create();
    }

}
