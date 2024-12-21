package com.example.miniprojectsem5;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.w3c.dom.Attr;

public class CardSelectionViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isCard1Selected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isCard2Selected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isCard3Selected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isCard4Selected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isCard5Selected = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> isCard6Selected = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsCard1Selected() {
        return isCard1Selected;
    }

    public LiveData<Boolean> getIsCard2Selected() {
        return isCard2Selected;
    }

    public LiveData<Boolean> getIsCard3Selected() {
        return isCard3Selected;
    }

    public LiveData<Boolean> getIsCard4Selected() {
        return isCard4Selected;
    }

    public LiveData<Boolean> getIsCard5Selected() {
        return isCard5Selected;
    }

    public LiveData<Boolean> getIsCard6Selected() {
        return isCard6Selected;
    }

    public void updateCard1Selection(boolean isSelected) {

        isCard1Selected.setValue(isSelected);
        isCard2Selected.setValue(!isSelected);
        isCard3Selected.setValue(!isSelected);
        isCard4Selected.setValue(!isSelected);
        isCard5Selected.setValue(!isSelected);
        isCard6Selected.setValue(!isSelected);

    }

    public void updateCard2Selection(boolean isSelected) {
        isCard1Selected.setValue(!isSelected);
        isCard2Selected.setValue(isSelected);
        isCard3Selected.setValue(!isSelected);
        isCard4Selected.setValue(!isSelected);
        isCard5Selected.setValue(!isSelected);
        isCard6Selected.setValue(!isSelected);
    }

    public void updateCard3Selection(boolean isSelected) {
        isCard1Selected.setValue(!isSelected);
        isCard2Selected.setValue(!isSelected);
        isCard3Selected.setValue(isSelected);
        isCard4Selected.setValue(!isSelected);
        isCard5Selected.setValue(!isSelected);
        isCard6Selected.setValue(!isSelected);
    }

    public void updateCard4Selection(boolean isSelected) {
        isCard1Selected.setValue(!isSelected);
        isCard2Selected.setValue(!isSelected);
        isCard3Selected.setValue(!isSelected);
        isCard4Selected.setValue(isSelected);
        isCard5Selected.setValue(!isSelected);
        isCard6Selected.setValue(!isSelected);
    }

    public void updateCard5Selection(boolean isSelected) {

        isCard1Selected.setValue(!isSelected);
        isCard2Selected.setValue(!isSelected);
        isCard3Selected.setValue(!isSelected);
        isCard4Selected.setValue(!isSelected);
        isCard5Selected.setValue(isSelected);
        isCard6Selected.setValue(!isSelected);

    }

    public void updateCard6Selection(boolean isSelected) {

        isCard1Selected.setValue(!isSelected);
        isCard2Selected.setValue(!isSelected);
        isCard3Selected.setValue(!isSelected);
        isCard4Selected.setValue(!isSelected);
        isCard5Selected.setValue(!isSelected);
        isCard6Selected.setValue(isSelected);
    }
}



