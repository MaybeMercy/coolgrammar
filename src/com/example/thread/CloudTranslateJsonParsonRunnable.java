package com.example.thread;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.coolgrammer.R;
import com.example.jsonparse.CloudDictionaryFromEntoZhJsonHandle;
import com.example.jsonparse.CloudDictionaryFromZhtoEnJsonHandle;
import com.example.jsonparse.CloudDictionaryJsonHandle;

public class CloudTranslateJsonParsonRunnable implements Runnable{

	public static final String TRANSLATE_APPID = "20160130000009826";
	public static final String TRANSALTE_PRIVATE_KEY = "tjd6UXdszzL0iuscUMxH";
	private Handler handler;
	private ListView translate_result;
	private String keys;
	private ArrayList<HashMap<String, String>> result;
	private CloudDictionaryJsonHandle translate;
	private Context context;
	private ProgressBar loading_translate;
	private String type;
	public CloudTranslateJsonParsonRunnable(ListView translate_result, Context context, String keys, Handler handler, 
			ProgressBar loading_translate, String type){
		this.translate_result = translate_result;
		this.keys = keys;
		this.handler = handler;
		this.context = context;
		this.loading_translate = loading_translate;
		this.type = type;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(type.equals("dictionary_entozh")){
			translate = new CloudDictionaryFromEntoZhJsonHandle(keys);
		}
		else if (type.equals("dictionary_zhtoen")) {
			translate = new CloudDictionaryFromZhtoEnJsonHandle(keys);
		}
		if (translate.isgetTranslateResult()) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					result = new ArrayList<HashMap<String,String>>();
					result = translate.getTranslateResult();
					loading_translate.setVisibility(View.GONE);
//					Log.v("CloudTranslateJsonParsonRunnable", result.get(0).get("src")+"+"+result.get(0).get("dst"));
//					SimpleAdapter adapter = new SimpleAdapter(context, result, R.layout.cloud_translate_list_item,
//							new String[]{"src", "dst"}, new int[]{R.id.before_translate, R.id.after_translate});
					SimpleAdapter adapter = new SimpleAdapter(context, result, R.layout.cloud_dictionary_list_item, 
							new String[]{"word_name", "ph_am", "ph_en", "explain"}, new int[]{R.id.before_dictionary, R.id.ph_am, R.id.ph_en, R.id.after_dictionary});
					translate_result.setAdapter(adapter);
					translate_result.setVisibility(View.VISIBLE);
				}
			});
		}
		else {
			handler.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(context, "抱歉，出错了", Toast.LENGTH_SHORT).show();
					loading_translate.setVisibility(View.GONE);
				}
			});
		}
	}
	
}
