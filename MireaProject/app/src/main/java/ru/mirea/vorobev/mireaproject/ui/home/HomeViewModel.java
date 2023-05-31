package ru.mirea.vorobev.mireaproject.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это начальная страница \n Меня зовут Воробьев Иван \n Студент группы БСБО-17-20 \n ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}