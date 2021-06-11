package com.example.vocabapp.Data;

import android.content.Context;
import android.widget.Filter;

import com.example.vocabapp.DatabaseHelper.DatabaseAccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataHelper {

    private static List<WordSuggestion> sColorSuggestions = new ArrayList<>();

    public static void setsColorSuggestions(Context context) {
        sColorSuggestions = DatabaseAccess.getInstance(context).getSuggestions();
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<WordSuggestion> results);
    }

//    public static List<String> getHistory() {
//        List<String> l = new ArrayList<>();
//        new UserDataHelper().readWordSearched(new UserDataHelper.WordSearchedStatus() {
//            @Override
//            public void ListIsLoaded(List<String> list) {
//                if (list != null)
//                    l.addAll(list);
//
//            }
//
//            @Override
//            public void ListIsInserted() {
//
//            }
//
//            @Override
//            public void ListIsUpdated() {
//
//            }
//
//            @Override
//            public void ListIsDeleted() {
//
//            }
//        });
//        Log.d("list",String.valueOf(l.size()));
//
//        return l;
//    }
//
//
//    public static List<WordSuggestion> getHistory(Context context, int count) {
//        List<WordSuggestion> suggestionList = new ArrayList<>();
//        new UserDataHelper().readWordSearched(new UserDataHelper.WordSearchedStatus() {
//            @Override
//            public void ListIsLoaded(List<String> list) {
//                if (list != null)
//                    for(String str : list)
//                        suggestionList.add(new WordSuggestion(str));
//            }
//
//            @Override
//            public void ListIsInserted() {
//
//            }
//
//            @Override
//            public void ListIsUpdated() {
//
//            }
//
//            @Override
//            public void ListIsDeleted() {
//
//            }
//        });
////        WordSuggestion colorSuggestion;
////        for (int i = 0; i < sColorSuggestions.size(); i++) {
////            colorSuggestion = sColorSuggestions.get(i);
////            colorSuggestion.setIsHistory(true);
////            suggestionList.add(colorSuggestion);
////            if (suggestionList.size() == count) {
////                break;
////            }
////        }
//        List<WordSuggestion> wordSuggestions = new ArrayList<>();
//        for (int i = suggestionList.size() - 1; i >= 0; i--) {
//            wordSuggestions.add(suggestionList.get(i));
//            if (wordSuggestions.size() == count)
//                break;
//        }
//        return wordSuggestions;
//    }

    public static void resetSuggestionsHistory() {
        for (WordSuggestion colorSuggestion : sColorSuggestions) {
            colorSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DataHelper.resetSuggestionsHistory();
                List<WordSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (WordSuggestion suggestion : sColorSuggestions) {
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, (lhs, rhs) -> lhs.getIsHistory() ? -1 : 0);
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<WordSuggestion>) results.values);
                }
            }
        }.filter(query);

    }
}


