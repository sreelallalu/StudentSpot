package venture.student.com.studentspot;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import venture.student.com.Button.LayoutRipple;

import static venture.student.com.studentspot.NetworkChecker.isConnected;
import static venture.student.com.studentspot.NetworkChecker.isConnectedMobile;
import static venture.student.com.studentspot.NetworkChecker.isConnectedWifi;

public class Shows extends Fragment{
	private Button result,fees,timetable;
	private String testString="";
	 private static final String TAG_USER = "user";
	 private static final String IDS ="Title";
	 private static final String ID_R ="Result";
	 private static final String ID_D="Date";
	 
	 private static final String ID_F = "Fees";
	 private static final String ID_T ="Timetable";
	 
	String s;
	 String id=null,idresult=null,idfees=null,idtime=null,datei=null;
	String qrjson1,qrjson2;
	private JSONObject jsonObject;
	 JSONArray matchFixture = null;
	ListView listView;
	private Button change;
	private TextView rt1,rt2,rt3,rt4,ort1,ort2,ort3;
	String[] demo=new String[25];
	String[] demo1=new String[25];
	String[] demo2=new String[25];
	private ImageView Settings;
	private Fragment fragment=null;
	ImageView IOS;
	 ArrayList<HashMap<String, String>> matchFixtureList1 = new ArrayList<HashMap<String, String>>();
	 ArrayList<HashMap<String, String>> matchFixtureList2 = new ArrayList<HashMap<String, String>>();
	 ArrayList<HashMap<String, String>> matchFixtureList3 = new ArrayList<HashMap<String, String>>();


	/*public void onBackPressed() {


		if (exit)
			System.exit(0);
		else {
			Toast.makeText(getActivity(), "Press Back again to Exit.",
					Toast.LENGTH_SHORT).show();
			exit = true;
			new Handler().postDelayed(new Runnable() {
				public void run() {
					exit = false;
				}
			}, 3 * 1000);

		}

	}*/







	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
	 
	 {    
		 
	    	View view = inflater.inflate(R.layout.registerd, container, false);
	    	//result=(Button)view.findViewById(R.id.bresult);
	    	//fees=(Button)view.findViewById(R.id.bfees);
	    	//timetable=(Button)view.findViewById(R.id.btimetable);
	    	rt1=(TextView)view.findViewById(R.id.ccourse);
	    	rt2=(TextView)view.findViewById(R.id.cctcourse);
	    	rt3=(TextView)view.findViewById(R.id.ccsem);
	    	rt4=(TextView)view.findViewById(R.id.cctsem);
	    	Settings=(ImageView)view.findViewById(R.id.setting);
	    	listView=(ListView)view.findViewById(R.id.list);
             change=(Button)view.findViewById(R.id.chngeid);

      change.setOnClickListener(new OnClickListener() {
		  @Override
		  public void onClick(View v) {

			  if(isConnectedWifi(getActivity())||isConnectedMobile(getActivity()))
			  {
				  if(isConnected(getActivity())) {


					  fragment = new ListViewFragment();
					  getActivity().getSupportFragmentManager().beginTransaction()
							  .replace(R.id.container, fragment).commit();
				  }else{
					  Toast.makeText(getActivity(),"no connection",Toast.LENGTH_LONG).show();

				  }
			  }else{Toast.makeText(getActivity(),"check internet",Toast.LENGTH_LONG).show();}
		  }
	  });
	    	SharedPreferences editor = getActivity().getPreferences(0);
			if (editor != null) 
			{
				 qrjson1 = editor.getString("selectc", null);
				 qrjson2 = editor.getString("selects", null);
				rt2.setText(qrjson1);
				rt4.setText(qrjson2);
			}
			 final SharedPreferences.Editor  editor1 = getActivity()
						.getPreferences(1).edit();
			 
			 
			 
			 DataB datab=new DataB(getActivity());
		 datab.open();

		boolean b=datab.check();
		 datab.close();
		// Toast.makeText(getActivity(),""+b,Toast.LENGTH_SHORT).show();
			Settings.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				

					if (editor1 != null) {
						editor1.putString("selectcn", qrjson1);
						editor1.putString("selectsn", qrjson2);
						editor1.commit();
						
					}
					
					
					fragment = new Notification();
					getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment).commit();
					
					
				}
			});
				Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				        "fonts/ArnoPro-SmbdItalicSmText.otf");
				
				rt1.setTypeface(tf);
				rt3.setTypeface(tf);
				
				  
		        LayoutRipple layoutRipple1 = (LayoutRipple)view. findViewById(R.id.bresult);

		        setOriginRiple(layoutRipple1);

		        layoutRipple1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(isConnectedWifi(getActivity())||isConnectedMobile(getActivity()))
						{
							if(isConnected(getActivity())) {
								testString = "result";
								matchFixtureList1.clear();
								matchFixtureList2.clear();
								matchFixtureList3.clear();
								new CheckAvialability().execute();
							}	else{
									Toast.makeText(getActivity(),"no connection",Toast.LENGTH_LONG).show();
								}

							}else{
								Toast.makeText(getActivity(),"turn on wifi",Toast.LENGTH_LONG).show();
							}
						
					}
				});


		        LayoutRipple layoutRipple2 = (LayoutRipple)view. findViewById(R.id.bfees);

		        setOriginRiple(layoutRipple2);

              layoutRipple2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(isConnectedWifi(getActivity())||isConnectedMobile(getActivity()))
						{
							if(isConnected(getActivity())){
						testString="time";
						matchFixtureList1.clear();
						 matchFixtureList2.clear();
						 matchFixtureList3.clear();
						new CheckAvialability().execute();
						
					} else{
								Toast.makeText(getActivity(),"no connection",Toast.LENGTH_LONG).show();
							}

						}

						else{
							Toast.makeText(getActivity(),"turn on wifi",Toast.LENGTH_LONG).show();
						}}
				});


		        LayoutRipple layoutRipple3 = (LayoutRipple)view. findViewById(R.id.btimetable);

		        setOriginRiple(layoutRipple3);

				 layoutRipple3.setOnClickListener(new OnClickListener() {

					 @Override
					 public void onClick(View arg0) {
						 // TODO Auto-generated method stub

						 if(isConnectedWifi(getActivity())||isConnectedMobile(getActivity()))
						 {
							 if(isConnected(getActivity())) {
						 testString = "fees";
						 matchFixtureList1.clear();
						 matchFixtureList2.clear();
						 matchFixtureList3.clear();
						 new CheckAvialability().execute();

					 }
							 else{
								 Toast.makeText(getActivity(),"no connection",Toast.LENGTH_LONG).show();

							 }
						 }else{
							 Toast.makeText(getActivity(),"turn on wifi",Toast.LENGTH_LONG).show();
						 }}
				 });

				
                      return view;

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
	 


    }
	 
	 class CheckAvialability extends AsyncTask<String, Void, JSONObject> {

		
			ProgressDialog p = new ProgressDialog(getActivity());

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub

				super.onPreExecute();
				p.setCancelable(false);
				p.show();
			}

			@Override
			protected JSONObject doInBackground(String... params) {
				// TODO Auto-generated method stub

				jsonObject = getJSONFromUrl("http://studentsportal.venturesoftwares.org/page.php");

				if (jsonObject != null) {
					try {
						Log.d("json values",
								"json" + jsonObject.getString("success"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return jsonObject;
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				super.onPostExecute(result);

				   p.dismiss();
				   
				   if (result != null)
		 {		   
	               try{
	            	   matchFixture =result.getJSONArray(TAG_USER);
	            	   Log.d("json aray", "user point array");
	 				  int len = matchFixture.length(); 
	 				  Log.d("len", "get array length");
	 				 for (int i =0; i < matchFixture.length(); i++)
  				        { 
	 					  JSONObject c = matchFixture.getJSONObject(i); 
	 						if(testString=="result")
							{
						    id = c.getString(IDS);
							idresult = c.getString(ID_R);
							datei=c.getString(ID_D);
							
							demo[i]=idresult;
							
							
							HashMap<String, String> matchFixture = new HashMap<String, String>();
							  // adding each child node to
							 // HashMap key => value
							
							
							  matchFixture.put(IDS,id);
							  matchFixture.put(ID_D,datei);
							
							  matchFixtureList1.add(matchFixture);
							

							}
	 						else
	 			
	 							if(testString=="fees") {
	 							 id = c.getString(IDS);
							idfees = c.getString(ID_F);
							datei=c.getString(ID_D);
							demo1[i]=idfees;
							
							HashMap<String, String> matchFixture1 = new HashMap<String, String>();
							  // adding each child node to
							 // HashMap key => value
							  matchFixture1.put(IDS,id);
							 
							  matchFixture1.put(ID_D, datei);
							
							  matchFixtureList2.add(matchFixture1);
							
							
						        }
	 						else if(testString=="time"){
	 							 id = c.getString(IDS);
							idtime = c.getString(ID_T);
							datei=c.getString(ID_D);
							demo2[i]=idtime;
	 						HashMap<String, String> matchFixture2 = new HashMap<String, String>();
							  // adding each child node to
							 // HashMap key => value
							  matchFixture2.put(IDS,id);
							  
							  matchFixture2.put(ID_D,datei);
							  matchFixtureList3.add(matchFixture2);
	 						}
							
  				    
  				    }
	 				if(testString=="result")
					{
					  ListAdapter adapter = new SimpleAdapter(getActivity(), matchFixtureList1, R.layout.itemlist, new String[]
									  { IDS,ID_D} , new int[] { R.id.id, R.id.teamB});
					  listView.setAdapter(adapter);
					 listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							// TODO Auto-generated method stub
							 

					            Uri uri = Uri.parse(demo[position]);
					            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					            startActivity(intent);
						}
					}) ;
					
					
					}
	 				else if(testString=="fees") {
					  
					 ListAdapter adapter1 = new SimpleAdapter(getActivity(), matchFixtureList2, R.layout.itemlist, new String[]
							  { IDS,ID_D} , new int[] { R.id.id, R.id.teamB});
					  listView.setAdapter(adapter1);
					  listView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
								// TODO Auto-generated method stub
								 
								AlertDialog.Builder alert = new AlertDialog.Builder(
										getActivity());

								alert.setTitle("Fees Date");
								alert.setMessage(demo1[position]);
								alert.show();
							}
						}) ;
	 			
	 				
	 				
	 				}
					  
					 else if(testString=="time"){ 
					  
					  ListAdapter adapter2 = new SimpleAdapter(getActivity(), matchFixtureList3, R.layout.itemlist, new String[]
							  { IDS,ID_D} , new int[] { R.id.id, R.id.teamB});
					  listView.setAdapter(adapter2);
					  
					  listView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long arg3) {
								// TODO Auto-generated method stub
								 

						            Uri uri = Uri.parse(demo2[position]);
						            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						            startActivity(intent);
							}
						}) ;
					  
					  
					  
								}
								
								                 }//try close	
	               
	               catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		         }//if close
				   else {
						Toast.makeText(getActivity(), "Error in connection",
								Toast.LENGTH_LONG).show();
					}
			}

			public JSONObject getJSONFromUrl(String url) {

				InputStream is = null;
				JSONObject jObj = null;
				String json = "";
				URL obj;
				HttpURLConnection con = null;

				// Making HTTP request
				try {
					System.out.println("url" + url);

					obj = new URL(url);
					con = (HttpURLConnection) obj.openConnection();

					// optional default is GET
					con.setRequestMethod("POST");
					con.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					// add request header
					con.setDoOutput(true);
					// con.setRequestProperty("tag", "register");
					// con.setRequestProperty("username", st_username);
					// con.setRequestProperty("user_type", st_usertype);
					// con.setRequestProperty("vehicle_type", st_vehicle_type);
					// con.setRequestProperty("vehicle_no", st_vehnumber);
					String charset = "UTF-8";
					if(testString=="result"){
					 s = "tag=" + URLEncoder.encode("register", charset);
					s += "&Titlel=" + URLEncoder.encode(qrjson1, charset);
					s += "&Feesl=" + URLEncoder.encode(qrjson2, charset);
					}
					if(testString=="fees"){
						 s = "tag=" + URLEncoder.encode("feesq", charset);
						s += "&Titlel2=" + URLEncoder.encode(qrjson1, charset);
						s += "&Feesl2=" + URLEncoder.encode(qrjson2, charset);
						}
					if(testString=="time"){
						 s = "tag=" + URLEncoder.encode("timetable", charset);
						s += "&Titlel3=" + URLEncoder.encode(qrjson1, charset);
						s += "&Feesl3=" + URLEncoder.encode(qrjson2, charset);
						
						}

					con.setFixedLengthStreamingMode(s.getBytes().length);
					PrintWriter out = new PrintWriter(con.getOutputStream());
					out.print(s);
					out.close();
					int responseCode = con.getResponseCode();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(
							con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					// print result

					json = response.toString();
					Log.e("JSON", json);
				} catch (Exception e) {
					Log.e("Buffer Error", "Error converting result " + e.toString());
				}

				// try parse the string to a JSON object
				try {
					jObj = new JSONObject(json);
				} catch (JSONException e) {
					Log.e("JSON Parser", "Error parsing data " + e.toString());
				}

				// return JSON String
				return jObj;

			}
		
	


		public void setListAdapter(ListAdapter adapter) {
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
	 
	 
	 }}
