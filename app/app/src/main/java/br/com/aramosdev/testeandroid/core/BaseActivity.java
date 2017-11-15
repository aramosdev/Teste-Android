package br.com.aramosdev.testeandroid.core;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.aramosdev.testeandroid.R;
import br.com.aramosdev.testeandroid.model.api.RestClient;
import br.com.aramosdev.testeandroid.search.SearchActivity;
import br.com.aramosdev.testeandroid.util.KeyboardUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Created by Alberto.Ramos on 11/11/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static final int RESULT_VOICE_SEARCH = 996;

    protected RestClient mApi;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    protected TextView mTitleView;
    @BindView(R.id.search_view)
    protected RelativeLayout mSearchView;
    @BindView(R.id.search_text)
    protected EditText mSearchText;
    @BindView(R.id.toolbar_search_clear)
    protected ImageView mSearchClear;
    @BindView(R.id.toolbar_search_mic)
    protected ImageView mSearchMic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = new RestClient(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        LayoutInflater.from(this).inflate(layoutResID, (FrameLayout)findViewById(R.id.main_layout));
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                InputMethodManager imm
                        = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View view = getCurrentFocus();
                if (view != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setActionBarTitle(String title) {
        if(getSupportActionBar() == null) {
            mToolbar = findViewById(R.id.toolbar);
            mToolbar.setVisibility(View.VISIBLE);
            setSupportActionBar(mToolbar);
        }
        mSearchView.setVisibility(View.GONE);
        mTitleView.setVisibility(View.VISIBLE);
        mTitleView.setText(title);
        setTitle(null);
    }

    public void showSearch() {
        mSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mSearchMic.setVisibility(View.GONE);
                    mSearchClear.setVisibility(View.VISIBLE);
                } else {
                    mSearchMic.setVisibility(View.VISIBLE);
                    mSearchClear.setVisibility(View.GONE);
                }
            }
        });

        mTitleView.setVisibility(View.GONE);
        mSearchView.setVisibility(View.VISIBLE);
        KeyboardUtil.hideKeyboard(getWindow());
    }

    protected void setReturnButton(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case  RESULT_VOICE_SEARCH :
                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                performSearch(result.get(0));
                break;
        }
    }

    @OnClick(R.id.toolbar_search_clear)
    protected void clearSearch() {
        mSearchText.setText("");
    }

    @OnEditorAction(R.id.search_text)
    protected boolean sendsSearch(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            startSearch(mSearchText.getText().toString());
            return true;
        }

        return false;
    }

    @OnClick(R.id.toolbar_search_mic)
    protected void promptSpeechInput() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getResources().getString(R.string.search_hint));
            intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivityForResult(intent, BaseActivity.RESULT_VOICE_SEARCH);

        } catch(ActivityNotFoundException e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.google_voice_search)));
            startActivity(browserIntent);
        }
    }

    private void performSearch(String query) {
        startSearch(query);
    }

    private void startSearch(String query) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(SearchActivity.SEARCH_QUERY_EXTRA, query);
        startActivity(intent);
        overridePendingTransition(R.anim.transition_in_up, R.anim.transition_none);
    }

}
