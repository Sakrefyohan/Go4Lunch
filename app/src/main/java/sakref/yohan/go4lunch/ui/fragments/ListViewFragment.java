package sakref.yohan.go4lunch.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import sakref.yohan.go4lunch.R;
import sakref.yohan.go4lunch.utils.NetworkAsyncTask;
import sakref.yohan.go4lunch.viewmodels.ListviewViewModel;


public class ListViewFragment extends Fragment implements NetworkAsyncTask.Listeners {

    private ListviewViewModel mListviewViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mListviewViewModel =
                ViewModelProviders.of(this).get(ListviewViewModel.class);
        View root = inflater.inflate(R.layout.fragment_view, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        mListviewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        this.executeHttpRequest();
        return root;
    }

    private void executeHttpRequest(){
        new NetworkAsyncTask(this).execute("https://api.github.com/users/JakeWharton/following");
    }

    @Override
    public void onPreExecute() {
        this.updateUIWhenStartingHTTPRequest();
    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(String json) {
        this.updateUIWhenStopingHTTPRequest(json);
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    private void updateUIWhenStartingHTTPRequest(){
        this.textView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response){
        this.textView.setText(response);
    }
}