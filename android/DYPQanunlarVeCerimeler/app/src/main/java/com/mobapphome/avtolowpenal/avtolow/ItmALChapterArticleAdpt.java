package com.mobapphome.avtolowpenal.avtolow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.mobapphome.avtolowpenal.R;
import com.mobapphome.avtolowpenal.other.ALArticle;
import com.mobapphome.avtolowpenal.other.ALChapter;
import com.mobapphome.avtolowpenal.other.ALChapterArticle;
import com.mobapphome.avtolowpenal.other.Constants;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItmALChapterArticleAdpt extends BaseAdapter {

	private final String TAG = ItmALChapterArticleAdpt.class.getName();
	private Activity activity;
	private List<ALChapterArticle> items;
	private static LayoutInflater inflater=null;
	SimpleDateFormat formatterFull = new SimpleDateFormat ("yyyy.MM.dd hh:mm");
	SimpleDateFormat formatterMonthDayHour = new SimpleDateFormat ("MMM d hh:mm");
	SimpleDateFormat formatterHour = new SimpleDateFormat ("hh:mm");



	public ItmALChapterArticleAdpt(Activity a, List<ALChapterArticle> items) {
		activity = a;
		this.items=items;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		ALChapterArticle item = items.get(position);

		if(item.getArticle() != null){
			ALArticle sign = item.getArticle();
			View vi=convertView;		
			//if(convertView==null)
				vi = inflater.inflate(R.layout.al_article_item, null);
			TextView signTV = (TextView)vi.findViewById(R.id.tvALArticleItem); // Message
			//ImageView signImageV = (ImageView)vi.findViewById(R.id.ivSign);
			//TextView dateTV = (TextView)vi.findViewById(R.id.tvMsgIReceiveDate); // Date

			// Setting all values in listview
			if(sign.getName() != null){
				String name = sign.getName();
				int index = name.indexOf('.');
				String name1 = "";
				String name2 = "";
				if(index > 0){
					name1 = name.substring(0, index);
					name2 = name.substring(index);					
				}else{
					name1 = name;
				}
				signTV.setText(Html.fromHtml(
						"<span><font color=\""+Constants.COLOR_FOR_TEXT_CODE+"\">"+name1+"</font></span><span>"+name2+"</span>"));			
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

		}else if(item.getChapter() != null){ //For typeinformation
			ALChapter type = item.getChapter();
			View vi=convertView;		
			//if(convertView==null)
				vi = inflater.inflate(R.layout.al_chapter_item, null);
			TextView typeTV = (TextView)vi.findViewById(R.id.tvALChapterItem); // Message

			// Setting all values in listview
			if(type.getName() != null){
				typeTV.setText(type.getName());			
			}
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
