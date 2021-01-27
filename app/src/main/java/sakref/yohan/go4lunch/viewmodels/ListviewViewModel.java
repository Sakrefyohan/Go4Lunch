package sakref.yohan.go4lunch.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListviewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ListviewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is List View fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}