package in.objectsol.my_roomie.Others;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.security.MessageDigest;

public class Utils {

	public static boolean isMyProduct = false;
	
	/*public static void OptionPopupWindow(final Activity context, int ycorodinate) {
		  
		 Display mDisplay = context.getWindowManager().getDefaultDisplay();
		int width = mDisplay.getWidth();
		   // Inflate the popup_layout.xml
		   LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.lloptionmenupopup);
		   LayoutInflater layoutInflater = (LayoutInflater) context
		     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   View layout = layoutInflater.inflate(R.layout.layout_optionmenu_popup, viewGroup);
		 
		   layout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		   // Creating the PopupWindow
		   final PopupWindow popup = new PopupWindow(context);
		   popup.setContentView(layout);
		   popup.setWidth((width/2)-10);
		   popup.setHeight(layout.getMeasuredHeight());
		   popup.setFocusable(true);
		   popup.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				
				
				
			}
		});
		 
		  
		 
		   // Clear the default translucent background
		   popup.setBackgroundDrawable(new BitmapDrawable());
		   final TextView userprofile=(TextView)layout.findViewById(R.id.tvoptionmenupopupuserprofile);
		   userprofile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.startActivity(new Intent(context,ProfileActivity.class));
				
			}
		});
		   final TextView  tvoptionmenupopupwishlist=(TextView)layout.findViewById(R.id.tvoptionmenupopupwishlist);
		   tvoptionmenupopupwishlist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.startActivity(new Intent(context,WishListActivity.class));
			}
		});
		   final TextView tvoptionmenupopupmyorder=(TextView)layout.findViewById(R.id.tvoptionmenupopupmyorder);
		   tvoptionmenupopupmyorder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.startActivity(new Intent(context,OrderActivity.class));
			}
		});
		   final TextView tvoptionmenupopuprateapp=(TextView)layout.findViewById(R.id.tvoptionmenupopuprateapp);
		   tvoptionmenupopuprateapp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				context.startActivity(new Intent(context,RateAppActivity.class));
				
			}
		});
		  
		   // Displaying the popup at the specified location, + offsets.
		   popup.showAtLocation(layout, Gravity.NO_GRAVITY, (width/2)-10, ycorodinate);
		  
		   
		   
	}*/


	public static boolean isNetworkAvailable(Context mcontext) {


		ConnectivityManager mConnectivity = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager mTelephony = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
		// Skip if no connection, or background data disabled
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		if (info == null || !mConnectivity.getBackgroundDataSetting()) {
			return false;
		}

		// Only update if WiFi or 3G is connected and not roaming
		int netType = info.getType();
		int netSubtype = info.getSubtype();

		if (netType == ConnectivityManager.TYPE_WIFI || netType == ConnectivityManager.TYPE_MOBILE) {
			return info.isConnected();
		} else if (netType == ConnectivityManager.TYPE_MOBILE && netSubtype == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming()) {
			return info.isConnected();
		} else {
			return false;
		}

	}

	public static String GenerateChecksum(String content) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(content.getBytes());

			byte byteData[] = md.digest();

			//convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			System.out.println("Hex format : " + sb.toString());

			//convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			System.out.println("Hex format : " + hexString.toString());
			return hexString.toString();
		} catch (Exception e) {
			return "";
		}
	}
}

