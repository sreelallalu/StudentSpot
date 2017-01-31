package venture.student.com.studentspot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class Notification extends Fragment implements OnClickListener{
	private static final int MODE_PRIVATE =1 ;
	private TextView rt1,rt2,rt3;
	EditText  Email;
	private CheckBox ss1,ss2,ss5,ss4,ss3;
	String qrjson1,qrjson2,emailstring;
	String rs1=null,rs2=null,rs3=null,rs4=null,rs5=null,rs6=null;
	Button gok;
	private JSONObject jsonObject;
	private Fragment fragment = null;

	DataB dbs = new DataB(getActivity());
	int id;
	String sendString=null;

	private Boolean exit = false;

	public void onBackPressed() {
		fragment=new Shows();
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();




	}



	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
	    	View view = inflater.inflate(R.layout.notification, container, false);

	    	rt1=(TextView)view.findViewById(R.id.nncourse);
	    	rt2=(TextView)view.findViewById(R.id.nnsem);
	    	rt3=(TextView)view.findViewById(R.id.textView1);
	    	ss1=(CheckBox)view.findViewById(R.id.cb1);
	    	ss2=(CheckBox)view.findViewById(R.id.cb2);
	    	ss3=(CheckBox)view.findViewById(R.id.cb3);
	    	ss4=(CheckBox)view.findViewById(R.id.cb4);
	    	ss5=(CheckBox)view.findViewById(R.id.cb5);

	    	gok=(Button)view.findViewById(R.id.upok);
	    	ss1.setOnClickListener(this);
	    	ss2.setOnClickListener(this);
	    	ss3.setOnClickListener(this);
	    	ss4.setOnClickListener(this);
	    	ss5.setOnClickListener(this);



		  SharedPreferences editor1 = getActivity().getPreferences(1);


		  if (editor1 != null)
		  {

			  qrjson1 = editor1.getString("selectcn", null);
			  qrjson2 = editor1.getString("selectsn", null);
			  rt1.setText(qrjson1);
			  rt2.setText(qrjson2);
			  rt3.setText(qrjson2);

		  }
		  Days enumval = Days.valueOf(qrjson2);
		  arrange(enumval);







		  //ss1.setText("s9");


			gok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {

					// TODO Auto-generated method stub
					/*Intent intent = new Intent(getActivity(), Brackgrounds.class);
					intent.putExtra("Cback", qrjson1);
					intent.putExtra("Sback", sendString);
					getActivity().startService(intent);
*/                  DataB db=new DataB(getActivity());
					      try{
                              db.open();
							  db.deleteEntry();
							  if(sendString==null){
								  db.createEntry(qrjson1,qrjson2);
							  }else{

							  db.createEntry(qrjson1,sendString);
							  }
							  db.close();
						  }catch (Exception e)
						  {e.printStackTrace();}
					finally {
							  if(sendString==null){Toast.makeText(getActivity(),"successful",Toast.LENGTH_LONG).show();}
							  else{Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();}
                           fragment=new Shows();
							  getActivity().getSupportFragmentManager().beginTransaction()
									  .replace(R.id.container, fragment).commit();
						  }




										//Toast.makeText(getActivity(),rs1+""+rs2+""+rs3+""+rs4+""+rs5+""+rs6,Toast.LENGTH_SHORT).show();
				}
			});
			
			
			
			
	    	
	    	return view;
	  }
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String kbk=null;
		Days enumval = Days.valueOf(qrjson2);
		arrange(enumval);
		if(rs1!=null&&rs2!=null&&rs3!=null&&rs4!=null&&rs5!=null&& rs6!=null){
			kbk=rs1+rs2+rs3+rs4+rs5+rs6;
		}
		//Toast.makeText(getActivity(),kbk,Toast.LENGTH_SHORT).show();

		if(kbk!=""){
			//rt3.setText(kbk);
			sendString=qrjson2+kbk;
			rt3.setText(sendString);
		}else{
			//rt3.setText("66");
			sendString=qrjson2;
			rt3.setText(sendString);

		}

	}
	  

	  
	  
	  private void arrange(Days enumval) {
		// TODO Auto-generated method stub
		  switch(enumval){
			
			case s1:
				rs1="";
				ss1.setText("s2");
				if(ss1.isChecked()){
					
					rs2="s2";
				}
				else{rs2="";}
				ss2.setText("s3");
              if(ss2.isChecked()){
              	rs3="s3";
				}else{rs3="";}
				ss3.setText("s4");
              if(ss3.isChecked()){
              	rs4="s4";
				}else{rs4="";}
				ss4.setText("s5");
             if(ss4.isChecked()){
          	   rs5="s5";
				}else{rs5="";}
				ss5.setText("s6");
            if(ss5.isChecked()){
          	  rs6="s6";
				}else{rs6="";}
				
				
				
				break; 
           case s2:
        	   rs2="";
          	 ss1.setText("s1");
          	 if(ss1.isChecked()){
          		 rs1="s1";
				}else{rs1="";}
				ss2.setText("s3");
            if(ss2.isChecked()){
          	  rs3="s3";
				}else{rs3="";}
				ss3.setText("s4");
          if(ss3.isChecked()){
          	rs4="s4";
				}else{rs4="";}
				ss4.setText("s5");
if(ss4.isChecked()){
	rs5="s5";	
				}else{rs5="";}
				ss5.setText("s6");
if(ss5.isChecked()){
	rs6="s6";		
				}else{rs6="";}
				
				break;		
           case s3:
        	   rs3="";
          	 ss1.setText("s1");
          	 if(ss1.isChecked()){
          		 rs1="s1";	
				}else{rs1="";}
				ss2.setText("s2");
if(ss2.isChecked()){
	rs2="s2";
				}else{rs2="";}
				ss3.setText("s4");
if(ss3.isChecked()){
	rs4="s4";	
				}else{rs4="";}
				ss4.setText("s5");
if(ss4.isChecked()){
	rs5="s5";
				}else{rs5="";}
				ss5.setText("s6");
if(ss5.isChecked()){
	rs6="s6";		
				} else{rs6="";}				
				break;
           case s4:
        	   rs4="";
          	 ss1.setText("s1");
          	 if(ss1.isChecked()){
          		 rs1="s1";
				}else{rs1="";}
 				ss2.setText("s2");
if(ss2.isChecked()){
	rs2="s2";
				}else{rs2="";}
 				ss3.setText("s3");
if(ss3.isChecked()){
	rs3="s3";			
				}else{rs3="";}
 				ss4.setText("s5");
if(ss4.isChecked()){
	rs5="s5";			
				}else{rs5="";}
 				ss5.setText("s6");
if(ss5.isChecked()){
	rs6="s6";			
				}else{rs6="";}
				
				break;
           case s5:
        	   rs5="";
          	 ss1.setText("s1");
          	 if(ss1.isChecked()){
          		 rs1="s1";	
				}else{rs1="";}
 				ss2.setText("s2");
if(ss2.isChecked()){
	rs2="s2";
				}else{rs2="";}
 				ss3.setText("s3");
if(ss3.isChecked()){
	rs3="s3";			
				}else{rs3="";}
 				ss4.setText("s4");
if(ss4.isChecked()){
	rs4="s4";		
				}else{rs4="";}
 				ss5.setText("s6");
if(ss5.isChecked()){
	rs6="s6";			
				}else{rs6="";}
				
				break;

           case s6:

               rs6="";
          	 ss1.setText("s1");

          	 if(ss1.isChecked()){
          		 rs1="s1";
				}else{rs1="";}
  				ss2.setText("s2");
  				if(ss2.isChecked()){
  					rs2="s2";
  				}else{rs2="";}
  				ss3.setText("s3");
  				if(ss3.isChecked()){
  					rs3="s3";
  				}else{rs3="";}
  				ss4.setText("s4");
  				if(ss4.isChecked()){
  					rs4="s4";
  				}else{rs4="";}
  				ss5.setText("s5");
  				if(ss5.isChecked()){
  					rs5="s5";
  				}else{rs5="";}

				break;

		   default:break;
			}
		
	}
	public enum Days {

		   s1,s2,s3,s4,s5,s6
		  }
	

}
