package com.example.foodpantry;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class ShoppingListFragment extends Fragment {

  View view;
  private WebView mWebView;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
    return view;
    }
    private void WebViewPrint() {
    WebView webView = new WebView(getActivity());
    webView.setWebViewClient(new WebViewClient() {
      public boolean shouldOverrideURLLoading(WebView view, String url) {
        return false;
      }
      @Override
      public void onPageFinished(WebView view, String url) {
        Log.i(TAG, "Finished Loading "+ url);
        mWebView = null;
      }
    });

    String htmlDocument = "\"<html><body><h1>Test Content</h1><p>Testing, \" +\n" +
            "\"testing, testing...</p></body></html>\";";
    webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
    mWebView = webView;
    }
} // class
