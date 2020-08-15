package ie.corktrainingcentre.canvasmenutest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;



import static ie.corktrainingcentre.canvasmenutest.R.id.saveFrag;

public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private FrameLayout fl;
    private DrawerLayout mDrawer;

    private static MyCanvasView myCanvasView;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;

    protected static final int REQUEST_PICK_IMAGE = 1;

    private int color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //remove native statusbar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        color = ResourcesCompat.getColor(getResources(),
                R.color.WHITE, null);

        //navagation
       mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        // set item as selected to persist highlight
                        item.setChecked(true);
                        // close drawer when item is tapped
                        int sel = item.getItemId();
                        mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        //canvas
        fl = findViewById(R.id.flContent);
        myCanvasView = new MyCanvasView(this);

        //myCanvasView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //myCanvasView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        fl.addView(myCanvasView);



        //menu
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);


        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        contextOfApplication = getApplicationContext();

    }
    public void setColor(int color){
        this.color = color;
    }

    public int getColor(){
        return this.color;
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(
                this,
                mDrawer,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass  = null;
        switch(menuItem.getItemId()) {
            case R.id.pick:

                Intent pickImageIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                );
                startActivityForResult(pickImageIntent, REQUEST_PICK_IMAGE);
                break;
            case R.id.save:
                fragmentClass = SaveFrag.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

                break;
            case R.id.Color:
                fragmentClass = ColorFrag.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager2 = getFragmentManager();
                fragmentManager2.beginTransaction().replace(R.id.flContent, fragment).commit();

                break;
            case R.id.stuff2:

                break;
            default:

        }



        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }//eb

    public static Bitmap getmap(){
    return  myCanvasView.getDrawingCache();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (Activity.RESULT_OK == resultCode) {
            Uri imageUri = intent.getData();
            Bitmap bitmap;
            Context applicationContext = MainActivity.getContextOfApplication();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        applicationContext.getContentResolver(), imageUri);

                myCanvasView.draw(bitmap);
               // im.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }//eb



    public void broadcast(String filename, String type) {

        //check for permission
        if(Build.VERSION.SDK_INT>22){
            requestPermissions(
                    new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
           // onRequestPermissionsResult();
        }

       //get bitmap
        Bitmap bmMyView = MyCanvasView.mExtraBitmap;

        //save bitmap
        MediaStore.Images.Media.insertImage(getContentResolver(), bmMyView, filename+type , "new");
        Toast.makeText(this, "Saved: "+filename+type, Toast.LENGTH_LONG).show();


        //https://stackoverflow.com/questions/33822508/error-incompatible-types-android-app-fragmentmanager-cannot-be-converted-to-and
        FragmentManager fm = getFragmentManager();
        SaveFrag fragment = (SaveFrag) fm.findFragmentById(saveFrag);
        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .hide(fragment)
                .commit();

    }
    public void broadcast(int color) {
        setColor(color);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied to access your location.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }




    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }



}//eb





