package com.example.android_studio_memo_pad;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MemoPadPresenter implements PropertyChangeListener, interfaceContract.interfacePresenter{
    private MemoPadModel model;
    private MemoActivityView view;
    public static final String ELEMENT_ADD = "add";
    public static final String ELEMENT_DELETE = "delete";

    public void addModel(MemoPadModel model) {
        model.addPropertyChangeListener(this);
        this.model = model;
        model.setPresenter(this);
    }

    public void addView(MemoActivityView view) {
        this.view = view;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ELEMENT_ADD)) {
            view.onAddMemo();

        } else if (evt.getPropertyName().equals(ELEMENT_DELETE)) {
            view.onDeleteMemo();
        }
    }


    // View to Presenter
    @Override
    public void addMemo(Memo memo) {
        // Presenter to Model
        model.addNewMemo(memo);
    }

    // View to Presenter
    @Override
    public void deleteMemo(int id) {
        // Presenter to Model
        model.deleteMemo(id);
    }

    public ArrayList<Memo> getAllMemosAsList() {
        return model.getAllMemosAsList();
    }
}
