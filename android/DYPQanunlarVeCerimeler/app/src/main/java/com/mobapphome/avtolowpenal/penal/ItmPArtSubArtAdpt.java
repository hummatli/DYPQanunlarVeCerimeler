package com.mobapphome.avtolowpenal.penal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.mobapphome.avtolowpenal.R;
import com.mobapphome.avtolowpenal.other.Constants;
import com.mobapphome.avtolowpenal.other.PParArtSubArt;
import com.mobapphome.avtolowpenal.other.PParentArticle;
import com.mobapphome.avtolowpenal.other.PSubArticle;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItmPArtSubArtAdpt extends BaseAdapter {

	private final String TAG = ItmPArtSubArtAdpt.class.getName();
	private Activity activity;
	private List<PParArtSubArt> items;
	private static LayoutInflater inflater=null;
	SimpleDateFormat formatterFull = new SimpleDateFormat ("yyyy.MM.dd hh:mm");
	SimpleDateFormat formatterMonthDayHour = new SimpleDateFormat ("MMM d hh:mm");
	SimpleDateFormat formatterHour = new SimpleDateFormat ("hh:mm");



	public ItmPArtSubArtAdpt(Activity a, List<PParArtSubArt> items) {
		activity = a;
		this.items=items;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		Log.i("Test", "PParArtSubArt count = " + items.size() );
	}

	public int getCount() {
		return items.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		PParArtSubArt item = items.get(position);

		if(item.getParArt() != null){
			PParentArticle type = item.getParArt();
			View vi=convertView;		
			//if(convertView==null)
				vi = inflater.inflate(R.layout.al_chapter_item, null);
			TextView typeTV = (TextView)vi.findViewById(R.id.tvALChapterItem); // Message

			// Setting all values in listview
			if(type.getName() != null){
				String name = type.getName();
				String name1 = "";
				String name2 = "";
				int index = name.indexOf('|');
				if(index >0){
					name1 = name.substring(0, index);
					name2 = name.substring(index+1);			
				}else{
					name2 = name;
				}
				typeTV.setText(name1 + name2);	
				
			}
			return vi;
			
			
		}else if(item.getSubArt() != null){ //For typeinformation
			PSubArticle sign = item.getSubArt();
			View vi=convertView;		
			//if(convertView==null)
				vi = inflater.inflate(R.layout.p_sub_article_item, null);
				TextView nameTV = (TextView)vi.findViewById(R.id.tvPSubArticleItem); // Message
				TextView balTV = (TextView)vi.findViewById(R.id.tvPSubArticlePenalBal); // Message
				TextView etrafliTV = (TextView)vi.findViewById(R.id.tvPSubArticleEtrafli); // Message
			//ImageView signImageV = (ImageView)vi.findViewById(R.id.ivSign);
			//TextView dateTV = (TextView)vi.findViewById(R.id.tvMsgIReceiveDate); // Date

			// Setting all values in listview
			if(sign.getName() != null){
				StringBuffer descStr = new StringBuffer();		
				String[] descParts = sign.getDesc().split("\n");
				for(int i = 0;i< descParts.length; ++i){
					if(i >0){
						descStr.append("<br>");				
					}
					descStr.append(descParts[i]);
				}
				
				nameTV.setText(Html.fromHtml(
						"<span><font color=\""+Constants.COLOR_FOR_TEXT_CODE+"\">"+sign.getName()+"</font></span>"
								+ "<span>"+descStr.toString()+"</span>"));		
				balTV.setText(Html.fromHtml("<span><font color=\"red\"> Tənbeh: "+
										sign.toString()+"</font></span>"));
				etrafliTV.setText(Html.fromHtml("<span><font color=\""+Constants.COLOR_FOR_TEXT_CODE+"\">"+" ətraflı..."+"</font></span>"));		
				//countTV.setText(sign.getPenalCount());
			}

//			if(sign.getImage() != null){
//				//signTV.setText(signTV.getText() + sign.getImage());
//				try{
//				Drawable d = Drawable.createFromStream(vi.getContext().getAssets().open("images/"+ sign.getImage()),null);
//				signImageV.setImageDrawable(d);
//				}catch(IOException e){
//					e.printStackTrace();
//				}
//			}
			return vi;


		}else{
			View vi = convertView;
			return vi;
		}
	}
	
	public String formatDate(String dateStr){
		Date date = new Date(Long.valueOf(dateStr));
		Date currentDate = new Date();
		if(date.getYear() != currentDate.getYear()){
			return formatterFull.format(date);
		}else if(date.getMonth() != currentDate.getMonth()){
			return formatterMonthDayHour.format(date);
		}else if(date.getDate() != currentDate.getDate()){
			return formatterMonthDayHour.format(date);
		}else{
			return formatterHour.format(date);				
		}		
	}
}
