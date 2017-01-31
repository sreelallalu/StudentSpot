
package venture.student.com.studentspot;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity{
	private Fragment fragment=null;

    private Context context;
    Intent intent;
    String counter=null;
    String time=null;

    Boolean exit = false;

    public void onBackPressed() {
        if (exit)
            System.exit(0);
        else {
            Toast.makeText(MainActivity.this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, Brackgrounds.class));
       android.support.v7.app.ActionBar a = getSupportActionBar();
     //  a.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_ab_shadow));
       a.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
       a.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff"))); 
      // getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        a.hide();
        intent = new Intent(this, Brackgrounds.class);

        if (savedInstanceState == null) {

            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            Boolean status = prefs.getBoolean("register", false);
            if (status) {

                fragment=new Shows();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).commit();


            }else{

                SharedPreferences.Editor editor = getPreferences(
                        MODE_PRIVATE).edit();
                editor.putBoolean("register", true);

                editor.commit();

                fragment=new ListViewFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment).commit();



            }




        	
        }
    }






    }



