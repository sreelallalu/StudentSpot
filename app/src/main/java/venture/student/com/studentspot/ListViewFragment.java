
package venture.student.com.studentspot;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import venture.student.com.Button.LayoutRipple;

import static venture.student.com.studentspot.NetworkChecker.isConnected;
import static venture.student.com.studentspot.NetworkChecker.isConnectedMobile;
import static venture.student.com.studentspot.NetworkChecker.isConnectedWifi;


/**
 * Created by Artem on 06.12.13.
 */
public class ListViewFragment extends Fragment {
	 private Spinner course,sem;
	    String[] Scourse={"BA","Bsc","Bcom","BBA"};
	    String[] Sem={"s1","s2","s3","s4","s5","s6"};
	    String selectScourse;
	    String selectSem;
	    String en="vvn";
	    String bg="bh";
	    TextView t1,t2,t3,t4;
	     Button sendJson;
	JSONArray matchFixture = null;
	    ArrayAdapter<String> acourse;
	    ArrayAdapter<String> asem;
	    //private JSONObject jsonObject;
	    private JSONObject jsonObject;
	private String jsonString;
	private Fragment fragment=null;
	JSONArray ds=null;
           Button fr;
   


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.fragment_listview, container, false);
    	
        //if (mLoaded) setListViewTitles(mListViewTitles);
    	course=(Spinner)view.findViewById(R.id.spinner1);
    	sem=(Spinner)view.findViewById(R.id.spinner2);

       doit();

		/*List<String>  lcourse= new ArrayList(Arrays.asList(Scourse));
		acourse=new  ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item,lcourse);

		acourse.setDropDownViewResource(R.layout.spinner_compo);
		course.setAdapter(acourse);
*/


        List<String> lsem=new ArrayList(Arrays.asList(Sem));
	//	"http://studentsportal.venturesoftwares.org/Totalcourse.php"




        
        asem=new  ArrayAdapter<String>(getActivity(),
                 R.layout.spinner_item,lsem);
        
        
        asem.setDropDownViewResource(R.layout.spinner_compo);
        final SharedPreferences.Editor  editor = getActivity()
				.getPreferences(0).edit();
        
        sem.setAdapter(asem);
        
      Button layoutRipple = (Button)view. findViewById(R.id.button1);


        layoutRipple.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					if (ds != null) {


						selectScourse = course.getSelectedItem().toString();
						selectSem = sem.getSelectedItem().toString();

						if (editor != null) {
							editor.putString("selectc", selectScourse);
							editor.putString("selects", selectSem);
							editor.commit();

						}
						if(selectScourse!=null)
						{
						fragment = new Shows();
						}else{Toast.makeText(getActivity(),"null value",Toast.LENGTH_SHORT).show();}
						//new CheckAvialability().execute();
						//Toast.makeText(getActivity(),selectScourse+"  "+selectSem, Toast.LENGTH_SHORT).show();
					}else{
						doit();
					}
				}catch(Exception e){e.printStackTrace();}
				
				getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
			}
			
		});
    	/*fr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
		});*/
        return view;
    }

	private void doit() {
		try{

			if(isConnectedWifi(getActivity())||isConnectedMobile(getActivity()))
			{
				if(isConnected(getActivity())) {
					HashMap<String,String> hashmap =new HashMap<>();
					hashmap.put("tag","totalcourse");
					PostResponseAsyncTask task=new PostResponseAsyncTask(getActivity(),hashmap, new AsyncResponse() {
						@Override
						public void processFinish(String s) {

							try {
								ArrayList<String> listdata = new ArrayList<String>();
								 ds=new JSONArray(s);
								int io=ds.length();
								if (ds!= null) {
									for (int i=0;i<ds.length();i++){
										listdata.add(ds.get(i).toString());
									}


									acourse=new  ArrayAdapter<String>(getActivity(),
											R.layout.spinner_item,listdata);

									acourse.setDropDownViewResource(R.layout.spinner_compo);
									course.setAdapter(acourse);
								}



								//Toast.makeText(getActivity(),""+io,Toast.LENGTH_LONG).show();

							} catch (JSONException e) {
								e.printStackTrace();
							}


							}

							/*List<String>  lcourse= new ArrayList(Arrays.asList(s));
							acourse=new  ArrayAdapter<String>(getActivity(),
									R.layout.spinner_item,lcourse);

							acourse.setDropDownViewResource(R.layout.spinner_compo);
							course.setAdapter(acourse);*/





					});
					task.execute("http://studentsportal.venturesoftwares.org/Totalcourse.php");

					task.setEachExceptionsHandler(new EachExceptionsHandler() {
						@Override
						public void handleIOException(IOException e) {
							Toast.makeText(getActivity(), "time out", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void handleMalformedURLException(MalformedURLException e) {
							Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
						}

						@Override
						public void handleProtocolException(ProtocolException e) {
							Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
						}

						@Override
						public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
							Toast.makeText(getActivity(), "handle pro", Toast.LENGTH_SHORT).show();
						}
					});





				}else


				{//not connected
					 }


			                      }
			else {//turn on wifi
			}

		}catch(Exception e){}

	}

	private void setOriginRiple(final LayoutRipple layoutRipple) {
   layoutRipple.post(new Runnable() {
			
			@Override
			public void run() {
			//View v = layoutRipple.getChildAt(0);
		    
		    	
		    	layoutRipple.setRippleColor(Color.parseColor("#afe0e7"));
		    	
		    	layoutRipple.setRippleSpeed(30);
			}
		});
	// TODO Auto-generated method stub
	
}



	public boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			// edited, to include @Arthur's comment
			// e.g. in case JSONArray is valid as well...
			try {
				new JSONArray(test);
			} catch (JSONException jex) {
				return false;
			}
		}
		return true;
	}




}



