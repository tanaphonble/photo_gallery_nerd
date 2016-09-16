package ayp.aug.photogallerynerd;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Tanaphon on 9/16/2016.
 * QueryPreferences for storing preference values in this app
 */
public class QueryPreferences {
    private static String PREF_SEARCH_QUERY = "searchQuery";

    public static String getStoredQuery(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_SEARCH_QUERY, query)
                .apply();
    }
}
