package ayp.aug.photogallerynerd;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Tanaphon on 9/16/2016.
 * QueryPreferences for storing preference values in this app
 */
public class QueryPreferences {
    private static String PREF_SEARCH_QUERY = "searchQuery";
    private static String PREF_LAST_RESULT_ID = "lastResultId";

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

    public static String getLastResultId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LAST_RESULT_ID, null);
    }

    public static void setLastResultId(Context context, String lastResultId){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_RESULT_ID, lastResultId)
                .apply();
    }
}
