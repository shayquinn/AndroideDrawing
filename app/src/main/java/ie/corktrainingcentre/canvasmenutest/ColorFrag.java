package ie.corktrainingcentre.canvasmenutest;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ColorFrag extends Fragment implements View.OnClickListener {
//https://www.android-examples.com/android-create-color-picker-dialog-example-tutorial-using-library/

    private View v;
    private Fragment f;
    private RelativeLayout relativeLayout;
    private int DefaultColor ;
   // String htmlColor = cv.getSavedColorHtml(Color.WHITE);
   // int[] colorRGB = cv.getSavedColorRGB(Color.WHITE);

    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        v = inflater.inflate(R.layout.activity_color, container, false);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.colorFrag);
        OpenColorPickerDialog(false);
        DefaultColor = ContextCompat.getColor(v.getContext(), R.color.colorPrimary);
        return  v;
    }

    private void OpenColorPickerDialog(boolean AlphaSupport) {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(v.getContext(), DefaultColor, AlphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int color) {


                DefaultColor = color;
                relativeLayout.setBackgroundColor(color);



            }
            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {
                Toast.makeText(v.getContext(), "Color Picker Closed", Toast.LENGTH_SHORT).show();
            }
        });
        ambilWarnaDialog.show();
    }


    @Override
    public void onClick(View v) {
    }

    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

}
