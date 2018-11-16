package com.github.zhang;

import android.Manifest;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.ListView;

public class ContactsActivity extends BaseActivity {

    private CustomContactAdapter adapter;
    private ListView lstContact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        lstContact =  findViewById(R.id.lstContacts);
       requestPermiss(0);
    }

    @Override
    protected void initData() {

        LoaderManager.getInstance(this).initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {

                Uri CONTACT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                return new CursorLoader(ContactsActivity.this, CONTACT_URI, null,
                        null, null, null);

            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                adapter = new CustomContactAdapter(ContactsActivity.this, cursor);
                lstContact.setAdapter(adapter);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });
    }
}
