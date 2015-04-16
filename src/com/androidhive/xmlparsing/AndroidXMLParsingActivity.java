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

	static final String URL = "http://terryyamg.3eeweb.com/ea_103052/drink.xml"; // ��ƺ��}
	// XML node keys
	static final String KEY_ITEM = "item"; // ���}�W��<item> node
	static final String KEY_ID = "id"; // ���}�W��<id> node
	static final String KEY_NAME = "name"; // ���}�W��<name> node
	static final String KEY_COST = "cost"; // ���}�W��<cost> node
	static final String KEY_DESC = "description"; // ���}�W��<description> node

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // �q�����Wdrink.xml���o��T

		Document doc = parser.getDomElement(xml); // �qxml���oDOM����

		NodeList nl = doc.getElementsByTagName(KEY_ITEM); // ���o<item>�̪���T

		/* �j����o�X��(nl.getLength())��ƨù������map */
		for (int i = 0; i < nl.getLength(); i++) {

			HashMap<String, String> map = new HashMap<String, String>(); // �إ�HashMap

			Element e = (Element) nl.item(i);

			/* ������ key => value */
			map.put(KEY_ID, parser.getValue(e, KEY_ID));
			map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
			map.put(KEY_COST, "NT." + parser.getValue(e, KEY_COST));
			map.put(KEY_DESC, parser.getValue(e, KEY_DESC));

			Log.i("map", map + "");

			menuItems.add(map); // map�[�J��menuItems
		}

		/* menuItems�[�J��ListView */
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item, // �����ۭq��layout
				new String[] { KEY_NAME, KEY_DESC, KEY_COST }, new int[] {
						R.id.name, R.id.desciption, R.id.cost });

		setListAdapter(adapter);

		/* �襤ListView���� */
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/* ���o�襤�����ح� */
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String cost = ((TextView) view.findViewById(R.id.cost))
						.getText().toString();
				String description = ((TextView) view
						.findViewById(R.id.desciption)).getText().toString();

				Toast.makeText(view.getContext(),
						"�z�I�諸�O" + name + "\n�`�@" + cost + "\n�`�N:" + description,
						Toast.LENGTH_LONG).show();

			}
		});
	}
}