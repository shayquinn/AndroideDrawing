package ie.corktrainingcentre.canvasmenutest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;




import static ie.corktrainingcentre.canvasmenutest.MyCanvasView.*;

public class SaveFrag extends Fragment implements View.OnClickListener{
    private EditText ed;
    private String filename;
    private String filetype = ".jpg";
    private RadioGroup rg;
    private Button b;
    private ImageView sim;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.activity_save, container, false);
        sim = view.findViewById(R.id.sim);
        ed = view.findViewById(R.id.edt);
        rg = view.findViewById(R.id.rg);
        b = view.findViewById(R.id.save);
        b.setOnClickListener(this);
       rg = (RadioGroup) view.findViewById(R.id.rg);
       rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.r1:
                        filetype = ".jpg";
                        break;
                    case R.id.r2:
                        filetype = ".png";
                        break;
                }
            }
        });
        return view;
    }
    //public void killActivity() {
        //getActivity().finish();
        //getActivity().getFragmentManager().popBackStack();
   // }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:

                if(ed.getText().toString().matches("")){
                    filename = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                }else{
                    filename = ed.getText().toString().trim();
                }
                Toast.makeText(getActivity(),
                        filename+" "+filetype, Toast.LENGTH_SHORT).show();
                //https://stackoverflow.com/questions/12659747/call-an-activity-method-from-a-fragment
                ((MainActivity)getActivity()).broadcast(filename, filetype);
                break;
                default:
        }
        }
    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}
