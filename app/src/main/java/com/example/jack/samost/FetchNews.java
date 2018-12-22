package com.example.jack.samost;


import android.os.AsyncTask;

import java.util.ArrayList;

public class FetchNews extends AsyncTask<Void, Void, ArrayList<Product> > {
    public AsyncResponse delegate = null;

    @Override
    protected void onPostExecute(ArrayList<Product> products) {
        delegate.processFinish(products);
    }

    @Override
    protected ArrayList<Product> doInBackground(Void... voids) {

        return null;
    }
}
