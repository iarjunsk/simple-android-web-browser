package com.tricks4speed.skbrowser;

import android.app.Activity;
import android.location.Address;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MySimpleBrowserActivity extends Activity implements
		OnClickListener {

	WebView wv;
	ImageButton bAddress, bKeyword;
	Button bBack, bForward, bOption, bRefresh;
	EditText addr, keyword;

	// for setting WebView Client to wv rather than opera browser
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// progresss Bar initialisation
		getWindow().requestFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.main);
		initialise();


		// to set go back and go forward
		wv.canGoBack();
		wv.canGoForward();

		// enable javascript for webview and flash
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginsEnabled(true);

		// setting new WebViewClient so at to prevent opera from opening the
		// site
		wv.setWebViewClient(new MyWebViewClient());

		bAddress.setOnClickListener(this);
		bKeyword.setOnClickListener(this);

		bBack.setOnClickListener(this);
		bForward.setOnClickListener(this);
		bOption.setOnClickListener(this);
		bRefresh.setOnClickListener(this);

		// setTitle(wv.getTitle());

	}

	public void initialise() {
		wv = (WebView) findViewById(R.id.wvContent);

		bAddress = (ImageButton) findViewById(R.id.bAddr);
		bKeyword = (ImageButton) findViewById(R.id.bKeyword);
		bBack = (Button) findViewById(R.id.Back);
		bForward = (Button) findViewById(R.id.Forward);
		bOption = (Button) findViewById(R.id.Options);
		bRefresh = (Button) findViewById(R.id.Refresh);

		addr = (EditText) findViewById(R.id.etAddr);
		keyword = (EditText) findViewById(R.id.etKeyword);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		try {

			// <<<<<<<<<<<<<<<<<PROGRESS BAR>>>>>>>>>>>>
			// set a webChromeClient to track progress
			wv.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
					// update the progressBar
					MySimpleBrowserActivity.this.setTitle("Loading...");
					MySimpleBrowserActivity.this.setProgress(progress * 100);
					
					//set the title of the layout
					if(progress == 100)
						MySimpleBrowserActivity.this.setTitle(R.string.app_name);
				}
			});
			
			

			switch (v.getId()) {
			case R.id.bAddr:
				String url = addr.getText().toString();
				if (url != "") {
					url = URLUtil.isValidUrl(url) ? addr.getText().toString()   : "http://" + addr.getText().toString();
					wv.loadUrl(url);
					addr.setText(url);
					wv.requestFocus();
				}
				break;
			case R.id.bKeyword:
				String key = keyword.getText().toString();
				if (key != "") {
					key = "http://www.google.com/search?q="+ keyword.getText().toString();
					wv.loadUrl(key);
					addr.setText(key);
					wv.requestFocus();
				}
				break;

			case R.id.Back:
				wv.goBack();
				break;

			case R.id.Forward:
				wv.goForward();
				break;

			case R.id.Options:
				break;

			case R.id.Refresh:
				wv.reload();
				break;

			}

		} catch (Exception e) {
			// TODO: handle exception]
			e.printStackTrace();
		}

	}

}