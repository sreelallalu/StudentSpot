package venture.student.com.studentspot;


import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


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
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.DataFormatException;


public class Brackgrounds extends Service{

    private static final String TAG_USER ="user";
    private static final String IDq="id";
    private static final String IDS ="Title";
    private static final String ID_R ="Result";
    private static final String ID_D="Date";
    private  String idchk ;
    private static final String ID_F = "Fees";
    private static final String ID_T ="Timetable";
   // private final Handler handler = new Handler();
    Intent intent;
    private int flags;
    private int startId;
    int counter = 0;
    String s11;
    String s12;
    DataB db;
    boolean bb,bb1;
    String idck;
    String gio1;
    String name="BA";
    String idName="s1s2s3";
    private JSONObject jsonObject;
    JSONArray matchFixture = null;
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mTimer = new Timer();
        mTimer.schedule(timerTask, 2000, 10 * 1000);



    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {  db=new DataB(getApplicationContext());
        try{
          String cback=null;
            String sback=null;
            cback =intent.getStringExtra("Cback");
          sback=intent.getStringExtra("Sback");



        }catch (Exception e){e.printStackTrace();}

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable


    private Timer mTimer;
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            Log.e("Log", "running");
          new NotificationQ().execute();
            // StringNotification();

        }
    };



    class NotificationQ extends AsyncTask<String, Void, JSONObject> {


        String id,idresult,idfees,idtimetable,idf,idh;
        int idfd;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            super.onPreExecute();
            try{  db.open();
                s11= db.getData1();
                s12 = db.getData2();
                 bb=db.check();

                db.close();

               //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            // TODO Auto-generated method stub

            jsonObject = getJSONFromUrl("http://studentsportal.venturesoftwares.org/Notification.php");

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
            String data = null;

            if (result != null) {
                try {
                    int status=0;
                    int i;

                    int cz,xz;

                        //Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();

                    matchFixture =result.getJSONArray(TAG_USER);
                    Log.d("json aray", "user point array");
                    int len = matchFixture.length();
                    Log.d("len", "get array length");
                    for ( i =0; i<matchFixture.length(); i++)
                    {
                        JSONObject c = matchFixture.getJSONObject(i);
                        idf=c.getString(IDq);

                       //cz=Integer.parseInt(idf);

                           id = c.getString(IDS);
                        idresult = c.getString(ID_R);
                        idfees=c.getString(ID_F);
                        idtimetable=c.getString(ID_T);


                        if(bb!=false)
                           {

                            DataB gh=new DataB(getApplicationContext());
                             gh.open();

                             gh.createEntry1(idf);


                               //  Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();

                             gh.close();
                             notifiloop(i);

                         }
                        else{
                            //if condition check the value idf in databse
                            // if not
                            // create new value in to database
                            // and notify the value



                            DataB gh=new DataB(getApplicationContext());
                            gh.open();
                          bb1= gh.CheckIsDataAlreadyInDBorNot(idf);
                          //  Toast.makeText(getApplicationContext(),""+bb1, Toast.LENGTH_SHORT).show();
                            if(!bb1)

                            {


                                gh.createEntry1(idf);
                                notifiloop(i);
                                gh.close();
                            }



                       //    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();








                        }



                       /* else
                        {

                           /* try {
                                DataB ghn = new DataB(getApplicationContext());
                                ghn.open();


                                ghn.close();
                                //idh=ghn.getData3();
                                //Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();
                               //
                            }catch(Exception e){Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();}
                            // =Integer.parseInt(idh);


                            //ss1.trim().equals("succ"))


                                 if (cz==2) {

                                    // Toast.makeText(getApplicationContext(),"equal ok",Toast.LENGTH_SHORT).show();
                                    // Log.e(idh,idf);

                                 notifiloop(i);
                                 //delete second table
                                 DataB ghnm=new DataB(getApplicationContext());
                                 ghnm.open();
                                ghnm.deleteEntry1();



                                 ghnm.close();

                                  }


                             }
                                       */



                    }







                    //









				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//Toast.makeText(getApplicationContext(),"error fetchig",Toast.LENGTH_SHORT).show();
				}
			} else {
                //Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
			}

			// TODO Auto-generated method stub

		}

        private void notifiloop(int i) {
            if (idfees.length() <= 1 && idtimetable.length() <= 1) {

                setNotification(i, id, idresult);

            } else if (idresult.length() <= 1 && idfees.length() <= 1) {
//result
                setNotification(i+1,id, idtimetable);
            } else if (idresult.length() <= 1 && idtimetable.length() <= 1) {

                setNotifications(i+1,id, idfees);
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

				String charset = "UTF-8";
				String s = "tag=" + URLEncoder.encode("backgrd", charset);
				s += "&bcourse=" + URLEncoder.encode(s11, charset);
				s += "&bsem=" + URLEncoder.encode(s12, charset);


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
	}

    private void setNotifications(int i,String id, String idfees) {

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("RssPullLservice");
       // Uri uri = Uri.parse(sxC);
        Intent myIntent=new Intent(Intent.ACTION_VIEW,Uri.EMPTY);
        PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,myIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
        Context context=getApplicationContext();
        android.app.Notification.Builder builder;
        builder=new android.app.Notification.Builder(context)
                .setAutoCancel(true)
                .setContentText(idfees)
                .setContentTitle(id)
                .setContentIntent(pendingIntent)
                .setDefaults(android.app.Notification.DEFAULT_SOUND)

                .setSmallIcon(R.mipmap.ic_launcher);
        android.app.Notification notification=builder.build();
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification);


    }



    private void setNotification(int i,String setNoti,String sxC) {



        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("RssPullLservice");
        Uri uri = Uri.parse(sxC);
        Intent myIntent=new Intent(Intent.ACTION_VIEW, uri);
        PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,myIntent,Intent.FLAG_ACTIVITY_NEW_TASK);
        Context context=getApplicationContext();

        android.app.Notification.Builder builder;
        builder=new android.app.Notification.Builder(context)
                .setAutoCancel(true)
                .setContentText("StudentSpot")
                .setContentTitle(setNoti)
                .setContentIntent(pendingIntent)
                .setDefaults(android.app.Notification.DEFAULT_SOUND)

                .setSmallIcon(R.mipmap.ic_launcher);
        android.app.Notification notification=builder.build();
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(i,notification);


    }
}
