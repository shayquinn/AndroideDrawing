package ie.corktrainingcentre.canvasmenutest;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;


public class FirstFragment extends Fragment implements View.OnClickListener{
    private ImageView im;
    private TextView t1;
    private Button b1;

    protected static final int REQUEST_PICK_IMAGE = 1;

    private Drawable drawable;
    private Bitmap bitmap;
    private String ImagePath;
    private Uri URI;

    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View v = inflater.inflate(R.layout.option, container, false);
        im = v.findViewById(R.id.im);
        t1 = v.findViewById(R.id.t1);
         b1 = (Button) v.findViewById(R.id.b1);

        b1.setOnClickListener(this);






        // Inflate the layout for this fragment
        return v;
    }//eb

    public void selectDrawerItem(MenuItem menuItem) {

    }//eb


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.b1:
                //im.setImageResource(R.drawable.snowb);
               // t1.setText("test 1");
                Intent pickImageIntent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                startActivityForResult(pickImageIntent, REQUEST_PICK_IMAGE);
                break;
                default:
                    break;
        }
    }//eb

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (Activity.RESULT_OK == resultCode) {
            Uri imageUri = intent.getData();
            Bitmap bitmap;


            Context applicationContext = MainActivity.getContextOfApplication();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        applicationContext.getContentResolver(), imageUri);

                im.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }//eb

}//eb
