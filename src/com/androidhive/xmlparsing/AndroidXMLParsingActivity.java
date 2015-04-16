package com.androidhive.xmlparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidXMLParsingActivity extends ListActivity {

	static final String URL = "http://terryyamg.3eeweb.com/ea_103052/drink.xml"; // 資料網址
	// XML node keys
	static final String KEY_ITEM = "item"; // 網址上的<item> node
	static final String KEY_ID = "id"; // 網址上的<id> node
	static final String KEY_NAME = "name"; // 網址上的<name> node
	static final String KEY_COST = "cost"; // 網址上的<cost> node
	static final String KEY_DESC = "description"; // 網址上的<description> node

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // 從網路上drink.xml取得資訊

		Document doc = parser.getDomElement(xml); // 從xml取得DOM元素

		NodeList nl = doc.getElementsByTagName(KEY_ITEM); // 取得<item>裡的資訊

		/* 迴圈取得幾筆(nl.getLength())資料並對應放至map */
		for (int i = 0; i < nl.getLength(); i++) {

			HashMap<String, String> map = new HashMap<String, String>(); // 建立HashMap

			Element e = (Element) nl.item(i);

			/* 對應值 key => value */
			map.put(KEY_ID, parser.getValue(e, KEY_ID));
			map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
			map.put(KEY_COST, "NT." + parser.getValue(e, KEY_COST));
			map.put(KEY_DESC, parser.getValue(e, KEY_DESC));

			Log.i("map", map + "");

			menuItems.add(map); // map加入到menuItems
		}

		/* menuItems加入到ListView */
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item, // 對應自訂的layout
				new String[] { KEY_NAME, KEY_DESC, KEY_COST }, new int[] {
						R.id.name, R.id.desciption, R.id.cost });

		setListAdapter(adapter);

		/* 選中ListView項目 */
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/* 取得選中的項目值 */
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String cost = ((TextView) view.findViewById(R.id.cost))
						.getText().toString();
				String description = ((TextView) view
						.findViewById(R.id.desciption)).getText().toString();

				Toast.makeText(view.getContext(),
						"您點選的是" + name + "\n總共" + cost + "\n注意:" + description,
						Toast.LENGTH_LONG).show();

			}
		});
	}
}